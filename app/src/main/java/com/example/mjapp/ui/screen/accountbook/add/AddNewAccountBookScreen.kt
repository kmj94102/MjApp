package com.example.mjapp.ui.screen.accountbook.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.ui.custom.CommonRadio
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.ImageDoubleCard
import com.example.mjapp.ui.dialog.DateSelectDialog
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.*

@Composable
fun AddNewAccountBookItemScreen(
    onBackClick: () -> Unit,
    viewModel: AddNewAccountBookItemViewModel = hiltViewModel()
) {
    val color = if (viewModel.isIncome.value) MyColorTurquoise else MyColorRed
    val isShow = remember { mutableStateOf(false) }

    val status by viewModel.status.collectAsStateWithLifecycle()
    HighMediumLowContainer(
        status = status,
        heightContent = {
            AddNewAccountBookHeightItem(
                onBackClick = onBackClick,
                color = color,
                viewModel = viewModel
            )
        },
        mediumContent = {
            AddNewAccountBookMediumItem(
                color = color,
                viewModel = viewModel,
                onDateSelect = { isShow.value = true }
            )
        },
        lowContent = {
            AddNewAccountBookLowItem(color = color, viewModel = viewModel)
        }
    )

    DateSelectDialog(
        date = viewModel.item.value.date,
        isShow = isShow.value,
        color = color,
        onDismiss = { isShow.value = false },
        onSelect = { viewModel.updateDateInfo(it) }
    )
}

@Composable
fun AddNewAccountBookHeightItem(
    onBackClick: () -> Unit,
    color: Color,
    viewModel: AddNewAccountBookItemViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 3.dp)
    ) {
        IconBox(boxColor = color) {
            onBackClick()
        }
        Spacer(modifier = Modifier.weight(1f))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(30.dp)
                .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
                .clip(RoundedCornerShape(5.dp))
                .background(
                    if (viewModel.isIncome.value) color else MyColorWhite,
                )
                .nonRippleClickable { viewModel.updateIsIncome(true) }
        ) {
            Text(
                text = "수입",
                style = textStyle16B(),
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
        Spacer(modifier = Modifier.width(5.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(30.dp)
                .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
                .clip(RoundedCornerShape(5.dp))
                .background(
                    if (viewModel.isIncome.value) MyColorWhite else color,
                )
                .nonRippleClickable { viewModel.updateIsIncome(false) }
        ) {
            Text(
                text = "지출",
                style = textStyle16B(),
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
    }
}


@Composable
fun AddNewAccountBookMediumItem(
    color: Color,
    viewModel: AddNewAccountBookItemViewModel,
    onDateSelect: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            DoubleCard(
                bottomCardColor = color,
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable(onDateSelect)
            ) {
                Text(
                    text = viewModel.item.value.date,
                    style = textStyle16(),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .padding(start = 15.dp)
                )
            }
        }

        item {
            DoubleCard(
                topCardColor = color,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "즐겨찾기에서 찾기",
                    style = textStyle16B(),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        item {
            DoubleCardTextField(
                value = viewModel.item.value.amount.formatAmount(),
                onTextChange = {
                    viewModel.updateAmount(it)
                },
                textStyle = textStyle16().copy(textAlign = TextAlign.End),
                keyboardType = KeyboardType.Number,
                tailIcon = {
                    Text(
                        text = "원",
                        style = textStyle16(),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                },
                hint = "수입/지출 금액",
                bottomCardColor = color,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            DoubleCardTextField(
                value = viewModel.item.value.whereToUse,
                onTextChange = {
                    viewModel.updateWhereToUse(it)
                },
                hint = "사용 내용",
                bottomCardColor = color,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            ChooseWhereToUseCard(
                color = color,
                usageType = viewModel.item.value.usageType,
                onItemClick = viewModel::updateUsageType
            )
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseWhereToUseCard(
    color: Color,
    usageType: String,
    onItemClick: (String) -> Unit
) {
    DoubleCard(
        bottomCardColor = color,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "사용처 선택",
            style = textStyle16(),
            modifier = Modifier.padding(top = 10.dp, start = 15.dp)
        )


        FlowRow(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            maxItemsInEachRow = 5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            IncomeExpenditureType.values().forEach {
                Column {
                    ImageDoubleCard(
                        resId = it.imageRes,
                        imageSize = DpSize(47.dp, 47.dp),
                        innerPadding = PaddingValues(3.dp),
                        topCardColor = if (usageType == it.type) {
                            color
                        } else {
                            MyColorWhite
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .nonRippleClickable {
                                onItemClick(it.type)
                            }
                    )
                    Text(
                        text = it.typeName,
                        style = textStyle12().copy(textAlign = TextAlign.Center),
                        modifier = Modifier
                            .width(50.dp)
                            .padding(top = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AddNewAccountBookLowItem(
    color: Color,
    viewModel: AddNewAccountBookItemViewModel
) {
    Column {
        CommonRadio(
            text = "즐겨찾기 등록",
            check = viewModel.item.value.isAddFrequently,
            onCheckedChange = {
                viewModel.updateIsAddFrequently()
            },
            color = color,
            shape = RoundedCornerShape(3.dp),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 3.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))

        DoubleCard(
            topCardColor = color,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = "등록하기",
                style = textStyle16B().copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .nonRippleClickable {
                        viewModel.insertNewAccountBook()
                    }
            )
        }
    }
}