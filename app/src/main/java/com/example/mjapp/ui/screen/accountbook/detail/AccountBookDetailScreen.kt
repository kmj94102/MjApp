package com.example.mjapp.ui.screen.accountbook.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.ui.custom.AccountBookMonthCalendar
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.ImageDoubleCard
import com.example.mjapp.ui.custom.UnderLineText
import com.example.mjapp.ui.screen.accountbook.TitleAmountRow
import com.example.mjapp.ui.screen.accountbook.add.IncomeExpenditureType
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.util.formatAmountWithSign
import com.example.mjapp.util.rememberLifecycleEvent
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.network.model.AccountBookDetailInfo
import com.example.network.model.AccountBookItem

@Composable
fun AccountBookDetailScreen(
    onBackClick: () -> Unit,
    goToNewAccountBookItem: (String) -> Unit,
    goToFixedAccountBookItem: () -> Unit,
    viewModel: AccountBookDetailViewModel = hiltViewModel()
) {
    val info = viewModel.info.value
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyBottomContainer(
        status = status,
        heightContent = {
            AccountBookDetailHigh(onBackClick = onBackClick, info = info)
        },
        bodyContent = {
            AccountBookDetailMedium(viewModel = viewModel)
        },
        bottomContent = {
            AccountBookDetailLow(
                goToNewAccountBookItem = goToNewAccountBookItem,
                goToFixedAccountBookItem = goToFixedAccountBookItem,
                viewModel = viewModel
            )
        }
    )

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchThisMonthDetail()
        }
    }
}

@Composable
fun AccountBookDetailHigh(
    onBackClick: () -> Unit,
    info: AccountBookDetailInfo
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconBox(
            boxColor = MyColorTurquoise,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            onBackClick()
        }

        UnderLineText(
            textValue = "${info.startDate} ~ ${info.endDate}",
            textStyle = textStyle16B().copy(fontSize = 18.sp),
            underLineColor = MyColorTurquoise,
            modifier = Modifier.align(Alignment.Center)
        )
    }
    Spacer(modifier = Modifier.height(15.dp))

    TitleAmountRow(
        title = "수입",
        amount = info.income,
        isAmountBold = true,
        modifier = Modifier.padding(end = 3.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))

    TitleAmountRow(
        title = "지출",
        amount = info.expenditure,
        isAmountBold = true,
        modifier = Modifier.padding(end = 3.dp)
    )
}

@Composable
fun AccountBookDetailMedium(
    viewModel: AccountBookDetailViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        DoubleCard(bottomCardColor = MyColorTurquoise) {
            AccountBookMonthCalendar(
                today = viewModel.date,
                calendarInfo = viewModel.calendarList,
                selectDate = viewModel.selectDate.value,
                onSelectChange = viewModel::updateSelectDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        DoubleCard(
            bottomCardColor = MyColorTurquoise,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "내역",
                    style = textStyle16B(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MyColorTurquoise)
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MyColorBlack)
                )

                if (viewModel.itemList.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "내역이 없습니다.",
                            style = textStyle16().copy(MyColorGray)
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        viewModel.itemList.forEach {
                            item {
                                UsageHistoryItem(
                                    item = it,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UsageHistoryItem(
    modifier: Modifier = Modifier,
    item: AccountBookItem
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ImageDoubleCard(
            resId = IncomeExpenditureType.getImageByType(item.usageType),
            imageSize = DpSize(30.dp, 30.dp),
            innerPadding = PaddingValues(3.dp),
            topCardColor = if (item.amount < 0) MyColorRed else MyColorTurquoise,
            modifier = Modifier.size(33.dp)
        )

        Text(
            text = item.whereToUse,
            style = textStyle16B(),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1f)
        )

        Text(
            text = item.amount.formatAmountWithSign(),
            style = textStyle16B().copy(
                fontSize = 18.sp,
                color = if (item.amount < 0) MyColorRed else MyColorTurquoise
            )
        )
    }
}

@Composable
fun AccountBookDetailLow(
    goToNewAccountBookItem: (String) -> Unit,
    goToFixedAccountBookItem: () -> Unit,
    viewModel: AccountBookDetailViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        DoubleCardButton(
            bottomCardColor = MyColorTurquoise,
            text = "고정 내역으로 등록",
            onClick = goToFixedAccountBookItem,
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))

        DoubleCardButton(
            topCardColor = MyColorTurquoise,
            text = "신규 내역 등록",
            onClick = { goToNewAccountBookItem(viewModel.selectDate.value) },
            modifier = Modifier
                .weight(1f)
        )
    }
}