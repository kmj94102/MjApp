package com.example.mjapp.ui.screen.accountbook.fixed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.ui.custom.CommonButton
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.custom.DoubleCardText
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.dialog.DaySelectDialog
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.formatAmount
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B

@Composable
fun AddFixedAccountBookScreen(
    onBackClick: () -> Unit,
    viewModel: AddFixedAccountBookViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isShow by remember { mutableStateOf(false) }
    val color = if (viewModel.item.value.isIncome) MyColorTurquoise else MyColorRed

    HeaderBodyBottomContainer(
        status = status,
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack),
        heightContent = {
            AddFixedAccountBookHigh(
                onBackClick = onBackClick,
                onItemClick = viewModel::updateIsIncome,
                isIncome = viewModel.item.value.isIncome,
                color = color
            )
        },
        bodyContent = {
            AddFixedAccountBookMedium(
                viewModel = viewModel,
                color = color,
                onDateSelect = { isShow = true }
            )
        },
        bottomContent = {
            AddFixedAccountBookLow(
                color = color,
                onAddClick = viewModel::insertFixedAccountBookItem
            )
        },
        onBackClick = onBackClick
    )

    DaySelectDialog(
        isShow = isShow,
        yearMonth = "2023.01",
        selectDay = viewModel.item.value.date,
        onConfirm = viewModel::updateDay,
        onDismiss = { isShow = false }
    )
}

@Composable
fun AddFixedAccountBookHigh(
    onBackClick: () -> Unit,
    onItemClick: (Boolean) -> Unit,
    isIncome: Boolean,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        IconBox(
            boxColor = color,
            onClick = onBackClick
        )
        Spacer(modifier = Modifier.weight(1f))

        CommonButton(
            text = "수입",
            backgroundColor = if (isIncome) color else MyColorWhite,
            innerPadding = PaddingValues(5.dp),
            onClick = { onItemClick(true) },
            modifier = Modifier.padding(end = 5.dp)
        )
        CommonButton(
            text = "지출",
            backgroundColor = if (isIncome) MyColorWhite else color,
            innerPadding = PaddingValues(5.dp),
            onClick = { onItemClick(false) }
        )
    }
}

@Composable
fun AddFixedAccountBookMedium(
    viewModel: AddFixedAccountBookViewModel,
    color: Color,
    onDateSelect: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(bottom = 20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            DoubleCardText(
                onClick = onDateSelect,
                text = "${viewModel.item.value.date}일",
                textStyle = textStyle16B(textAlign = TextAlign.Start),
                hint = "고정 날짜",
                bottomCardColor = color,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            DoubleCardTextField(
                value = viewModel.item.value.amount.formatAmount(),
                onTextChange = viewModel::updateAmount,
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
                onTextChange = viewModel::updateWhereToUse,
                hint = "사용 내용",
                bottomCardColor = color,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
        }
    }
}

@Composable
fun AddFixedAccountBookLow(
    onAddClick: () -> Unit,
    color: Color
) {
    DoubleCardButton(
        text = "등록하기",
        topCardColor = color,
        onClick = onAddClick,
        modifier = Modifier.fillMaxWidth()
    )
}