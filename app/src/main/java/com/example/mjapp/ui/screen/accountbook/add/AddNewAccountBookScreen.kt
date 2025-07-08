package com.example.mjapp.ui.screen.accountbook.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.CommonRadio
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.ImageDoubleCard
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.dialog.DateSelectDialog
import com.example.mjapp.ui.dialog.FrequentlyDialog
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.*
import com.example.network.model.ElswordQuestUpdate

@Composable
fun AddNewAccountBookItemScreen(
    navController: NavHostController?,
    viewModel: AddNewAccountBookItemViewModel = hiltViewModel()
) {
    val isShow = remember { mutableStateOf(false) }

    val status by viewModel.status.collectAsStateWithLifecycle()
    //수입
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
                isIncome = viewModel.isIncome.value,
                updateIsIncome = { viewModel.updateIsIncome(it) }
            )
        },
        bottomContent = {
            TextButton(
                text = "등록하기",
                backgroundColor = MyColorDarkBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                onClick = {

                }
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
    isIncome: Boolean,
    updateIsIncome: (Boolean) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            NewAccountBookTextField(
                title = "날짜",
                value = "",
                onTextChange = {}
            )
        }
        item {
           Row {
               CommonRadio(
                   text = "수입",
                   textStyle = textStyle14(),
                   check = isIncome,
                   onCheckedChange = { updateIsIncome(true) }
               )

               CommonRadio(
                   text = "지출",
                   textStyle = textStyle14(),
                   check = !isIncome,
                   onCheckedChange = { updateIsIncome(false) }
               )
           }
        }
        item {
            NewAccountBookTextField(
                title = "사용 금액",
                value = "",
                onTextChange = {}
            )
        }
        item {
            NewAccountBookTextField(
                title = "사용 내용",
                value = "",
                onTextChange = {}
            )
        }

        item { ChooseWhereToUseCard() }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = MyColorDarkBlue,
                            uncheckedColor = MyColorLightGray
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        "고정내역에서 찾기",
                        style = textStyle14(MyColorWhite),
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
            contentPadding = PaddingValues(20.dp),
            focusedIndicatorColor = MyColorDarkBlue,
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorLightBlack, RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun ChooseWhereToUseCard() {
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
                            .border(1.dp, MyColorDarkBlue, RoundedCornerShape(16.dp))
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