package com.example.mjapp.ui.screen.accountbook.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.ui.custom.*
import com.example.mjapp.ui.screen.accountbook.TitleAmountRow
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.ui.screen.accountbook.add.IncomeExpenditureType
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.formatAmountWithSign
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.network.model.AccountBookItem

@Composable
fun AccountBookDetailScreen(
    onBackClick: () -> Unit,
    goToNewAccountBookItem: (String) -> Unit,
    viewModel: AccountBookDetailViewModel = hiltViewModel()
) {
    val info = viewModel.info.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp)
                .padding(top = 22.dp)
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
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        TitleAmountRow(
            title = "지출",
            amount = info.expenditure,
            isAmountBold = true,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        DoubleCard(
            bottomCardColor = MyColorTurquoise,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp, top = 15.dp)
        ) {
            AccountBookMonthCalendar(
                today = viewModel.date,
                calendarInfo = createAccountBookCalendarList(
                    startDate = info.startDate,
                    endDate = info.endDate,
                    list = info.list
                ),
                selectDate = viewModel.selectDate.value,
                onSelectChange = {
                    viewModel.updateSelectDate(it)
                },
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
                .padding(start = 20.dp, end = 17.dp, top = 15.dp, bottom = 15.dp)
        ) {
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
                Text(
                    text = "내역이 없습니다.",
                    style = textStyle16().copy(MyColorGray),
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.CenterHorizontally)
                )
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp, bottom = 10.dp)
        ) {
            DoubleCard(
                bottomCardColor = MyColorTurquoise,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            ) {
                Text(
                    text = "고정 내역 추가",
                    style = textStyle16B(),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

            DoubleCard(
                topCardColor = MyColorTurquoise,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
                    .nonRippleClickable {
                        goToNewAccountBookItem(viewModel.selectDate.value)
                    }
            ) {
                Text(
                    text = "신규 내역 추가",
                    style = textStyle16B(),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)

                )
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
        DoubleCard(
            topCardColor = if (item.amount < 0) MyColorRed else MyColorTurquoise,
            modifier = Modifier.width(33.dp)
        ) {
            Image(
                painter = painterResource(id = IncomeExpenditureType.getImageByType(item.usageType)),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(3.dp)
            )
        }

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