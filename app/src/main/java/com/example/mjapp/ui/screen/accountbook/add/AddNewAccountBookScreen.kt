package com.example.mjapp.ui.screen.accountbook.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.ui.custom.CommonCheckBox
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.dialog.DateSelectDialog
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.network.model.AccountBookInsertItem
import com.example.network.util.priceFormat

@Composable
fun AddNewAccountBookItemScreen(
    navController: NavHostController?,
    viewModel: AddNewAccountBookItemViewModel = hiltViewModel()
) {
    val isShow = remember { mutableStateOf(false) }
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyBottomContainer(
        status = status,
        onBackClick = { navController?.popBackStack() },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack),
        heightContent = {
            CommonGnb(
                title = "가계부 등록",
                startButton = {
                    CommonGnbBackButton { navController?.popBackStack() }
                }
            )
        },
        bodyContent = {
            AddNewAccountBookBody(
                item = viewModel.item.value,
                updateDate = { isShow.value = true },
                updateIsIncome = { viewModel.updateIsIncome(it) },
                updateAmount = { viewModel.updateAmount(it) },
                updateWhereToUse = { viewModel.updateWhereToUse(it) },
                updateUsageType = { viewModel.updateUsageType(it) },
                updateIsAddFrequently = viewModel::updateIsAddFrequently
            )
        },
        bottomContent = {
            TextButton(
                text = "등록하기",
                backgroundColor = MyColorDarkBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                onClick = viewModel::insertNewAccountBook
            )
        }
    )

    DateSelectDialog(
        date = viewModel.item.value.date,
        isShow = isShow.value,
        onDismiss = { isShow.value = false },
        onSelect = { viewModel.updateDateInfo(it) }
    )
}

@Composable
fun AddNewAccountBookBody(
    item: AccountBookInsertItem,
    updateDate: () -> Unit = {},
    updateIsIncome: (Boolean) -> Unit = {},
    updateAmount: (String) -> Unit = {},
    updateWhereToUse: (String) -> Unit = {},
    updateUsageType: (String) -> Unit = {},
    updateIsAddFrequently: () -> Unit = {}
) {
    LazyColumn(
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            DateSelectBox(
                date = item.date,
                onClick = updateDate
            )
        }
        item {
           Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
               SelectBox(
                   isSelect = item.isIncome,
                   onSelectChange = { updateIsIncome(true) },
                   title = "수입",
                   modifier = Modifier.weight(1f)
               )
               SelectBox(
                   isSelect = item.isIncome.not(),
                   onSelectChange = { updateIsIncome(false) },
                   title = "지출",
                   modifier = Modifier.weight(1f)
               )
           }
        }
        item {
            NewAccountBookTextField(
                title = "사용 금액",
                value = item.amount.priceFormat(),
                onTextChange = updateAmount
            )
        }
        item {
            NewAccountBookTextField(
                title = "사용 내용",
                value = item.whereToUse,
                onTextChange = updateWhereToUse
            )
        }

        item {
            ChooseWhereToUseCard(
                selectType = item.usageType,
                onSelectChange = updateUsageType
            )
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CommonCheckBox(
                        isChecked = item.isAddFrequently,
                        onCheckedChange = { updateIsAddFrequently() },
                        unCheckedColor = MyColorGray,
                        checkedColor = MyColorDarkBlue,
                        text = "고정내역에 추가"
                    )
                }
                Spacer(Modifier.weight(1f))

                Text(
                    "고정내역에서 찾기",
                    style = textStyle14(MyColorWhite),
                )
            }
        }
    }
}


@Composable
fun DateSelectBox(
    date: String,
    onClick: () -> Unit
) {
    Column {
        Text(
            "날짜",
            style = textStyle14(MyColorLightGray),
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorLightBlack, RoundedCornerShape(16.dp))
                .padding(vertical = 19.dp, horizontal = 16.dp)
                .nonRippleClickable(onClick)
        ) {
            Text(date, style = textStyle14(MyColorWhite))
        }
    }
}

@Composable
fun NewAccountBookTextField(
    title: String,
    value: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(
            title,
            style = textStyle14(MyColorLightGray),
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(Modifier.height(8.dp))

        CommonTextField(
            value = value,
            onTextChange = onTextChange,
            keyboardType = keyboardType,
            textStyle = textStyle14(MyColorWhite),
            contentPadding = PaddingValues(20.dp),
            focusedIndicatorColor = MyColorDarkBlue,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun SelectBox(
    isSelect: Boolean,
    onSelectChange: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                if (isSelect) MyColorDarkBlue.copy(alpha = 0.1f) else MyColorLightBlack,
                RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                if (isSelect) MyColorDarkBlue else MyColorLightBlack,
                RoundedCornerShape(16.dp)
            )
            .padding(vertical = 19.dp)
            .nonRippleClickable { onSelectChange() }
    ) {
        Text(title, style = textStyle14(if (isSelect) MyColorWhite else MyColorLightGray))
    }
}

@Composable
fun ChooseWhereToUseCard(
    selectType: String,
    onSelectChange: (String) -> Unit = {}
) {
    Column {
        Text(
            text = "사용처 선택",
            style = textStyle14(MyColorLightGray),
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(Modifier.height(8.dp))

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            maxItemsInEachRow = 5,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IncomeExpenditureType.entries.forEach {
                Column {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                MyColorLightBlack,
                                RoundedCornerShape(16.dp)
                            )
                            .border(
                                1.dp,
                                if(selectType == it.type) MyColorDarkBlue else MyColorLightBlack,
                                RoundedCornerShape(16.dp)
                            )
                            .nonRippleClickable { onSelectChange(it.type) }
                    ) {
                        Icon(
                            painter = painterResource(it.imageRes),
                            contentDescription = null,
                            tint = MyColorWhite,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = it.typeName,
                        style = textStyle14().copy(
                            color = MyColorWhite,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .width(50.dp)
                            .padding(top = 5.dp)
                    )
                }
            }
        }
    }
}