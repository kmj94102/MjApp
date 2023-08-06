package com.example.mjapp.ui.screen.accountbook.fixed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CenteredDoubleCard
import com.example.mjapp.ui.custom.CommonButton
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.UnderLineText
import com.example.mjapp.ui.dialog.DaySelectDialog
import com.example.mjapp.ui.screen.accountbook.add.IncomeExpenditureType
import com.example.mjapp.ui.dialog.YearMonthSelectDialog
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.formatAmount
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.rememberLifecycleEvent
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle24B
import com.example.network.model.FixedAccountBook

@Composable
fun RegistrationFixedAccountBookScreen(
    onBackClick: () -> Unit,
    goToAddFixed: () -> Unit,
    viewModel: RegistrationFixedAccountBookViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val lifecycleEvent = rememberLifecycleEvent()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            RegistrationFixedAccountBookHeader(
                onBackClick = onBackClick,
                goToAddFixed = goToAddFixed
            )
        },
        bodyContent = {
            RegistrationFixedAccountBookBody(viewModel)
        }
    )

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchFixedAccountBook()
        }
    }
}

@Composable
fun RegistrationFixedAccountBookHeader(
    onBackClick: () -> Unit,
    goToAddFixed: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconBox(
            boxColor = MyColorTurquoise,
            onClick = onBackClick
        )

        Text(
            text = "고정 내역 등록",
            style = textStyle16B(),
            modifier = Modifier.align(Alignment.Center)
        )

        IconBox(
            boxColor = MyColorRed,
            boxShape = RoundedCornerShape(5.dp),
            iconRes = R.drawable.ic_plus,
            onClick = goToAddFixed,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun RegistrationFixedAccountBookBody(
    viewModel: RegistrationFixedAccountBookViewModel
) {
    var isDaySelectShow by remember { mutableStateOf(false) }
    var isYearMonthSelectShow by remember { mutableStateOf(false) }
    var selectDay by remember { mutableStateOf("01") }
    var selectIndex by remember { mutableIntStateOf(0) }

    UnderLineText(
        textValue = viewModel.yearMonth.value,
        textStyle = textStyle24B(),
        underLineColor = MyColorTurquoise,
        modifier = Modifier
            .padding(top = 10.dp)
            .nonRippleClickable { isYearMonthSelectShow = true }
    )
    Spacer(modifier = Modifier.height(15.dp))

    if (viewModel.list.isEmpty()) {
        FixedAccountBookEmpty()
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(bottom = 20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.list.forEachIndexed { index, item ->
                item {
                    FixedAccountBookCard(
                        item = item,
                        index = index,
                        viewModel = viewModel,
                        onDelete = { viewModel.deleteItem(item) },
                        onRegistration = { viewModel.registrationItem(item) },
                        onDateClick = {
                            selectIndex = index
                            selectDay = item.date
                            isDaySelectShow = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    DaySelectDialog(
        isShow = isDaySelectShow,
        yearMonth = viewModel.yearMonth.value,
        selectDay = selectDay,
        onConfirm = { viewModel.updateDay(it, selectIndex) },
        onDismiss = { isDaySelectShow = false }
    )

    YearMonthSelectDialog(
        isShow = isYearMonthSelectShow,
        year = viewModel.year,
        month = viewModel.month,
        color = MyColorTurquoise,
        onDismiss = { isYearMonthSelectShow = false },
        onSelect = viewModel::updateYearMonth
    )
}

@Composable
fun FixedAccountBookEmpty() {
    CenteredDoubleCard(
        bottomCardColor = MyColorTurquoise,
        modifier = Modifier
            .fillMaxWidth()
            .height(77.dp)
    ) {
        Text(
            text = "등록된 고정 목록이 없습니다.\n고정 내역을 추가해 주세요.",
            style = textStyle12().copy(
                color = MyColorGray,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun FixedAccountBookCard(
    modifier: Modifier = Modifier,
    index: Int,
    item: FixedAccountBook,
    viewModel: RegistrationFixedAccountBookViewModel,
    onDelete: () -> Unit,
    onRegistration: () -> Unit,
    onDateClick: () -> Unit
) {
    DoubleCard(
        topCardColor = if (item.isIncome) MyColorTurquoise else MyColorRed,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            IconBox(
                iconRes = IncomeExpenditureType.getImageByType(item.usageType),
                iconSize = 25.dp,
                boxColor = MyColorWhite.copy(alpha = 0.5f),
                onClick = {}
            )

            Text(
                text = item.whereToUse,
                style = textStyle16B(),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            )

            IconBox(
                iconRes = R.drawable.ic_close,
                iconSize = 21.dp,
                boxColor = MyColorBeige,
                boxShape = CircleShape,
                onClick = onDelete
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(60.dp)
                    .nonRippleClickable(onDateClick)
            ) {
                Text(text = item.date.plus("일"), style = textStyle16())
                Box(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .width(60.dp)
                        .height(1.dp)
                        .background(MyColorBlack)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))

            CommonTextField(
                value = item.amount.formatAmount().plus(" 원"),
                onTextChange = { viewModel.updateAmount(it, index) },
                textStyle = textStyle16().copy(
                    textAlign = TextAlign.End
                ),
                hint = "금액",
                keyboardType = KeyboardType.Number,
                unfocusedIndicatorColor = MyColorGray,
                focusedIndicatorColor = MyColorBlack,
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        CommonButton(
            text = "등록하기",
            backgroundColor = MyColorBeige,
            onClick = onRegistration,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}