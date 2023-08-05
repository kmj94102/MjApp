package com.example.mjapp.ui.screen.accountbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CenteredDoubleCard
import com.example.mjapp.ui.custom.DashLine
import com.example.mjapp.ui.custom.ImageDoubleCard
import com.example.mjapp.ui.custom.TitleText
import com.example.mjapp.ui.screen.accountbook.add.IncomeExpenditureType
import com.example.mjapp.ui.structure.BaseContainer
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.*
import com.example.network.model.LastMonthAnalysis
import com.example.network.model.LastMonthAnalysisItem
import com.example.network.model.ThisMonthSummary
import com.example.network.model.ThisYearSummaryItem

@Composable
fun AccountBookScreen(
    goToNewAccountBookItem: (String) -> Unit,
    goToFixedAccountBookItem: (String) -> Unit,
    goToDetail: (String) -> Unit,
    viewModel: AccountBookViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    BaseContainer(
        status = status,
        reload = { viewModel.fetchSummaryThisMonth() },
    ) {
        TitleText(
            title = "가계부",
            color = MyColorTurquoise,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        BaseDateAndAddButtonsRow(
            goToNewAccountBookItem = goToNewAccountBookItem,
            goToFixedAccountBookItem = goToFixedAccountBookItem
        )

        AccountBookBody(
            viewModel = viewModel,
            goToDetail = goToDetail
        )
    }
}

@Composable
fun BaseDateAndAddButtonsRow(
    goToNewAccountBookItem: (String) -> Unit,
    goToFixedAccountBookItem: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        DoubleCard(
            topCardColor = MyColorTurquoise,
            modifier = Modifier.width(73.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(70.dp)
            ) {
                Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                    Text(
                        text = "기준일",
                        style = textStyle12().copy(
                            color = MyColorWhite,
                            textAlign = TextAlign.End
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = "25일", style = textStyle24B())
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))

        DoubleCard(
            bottomCardColor = MyColorTurquoise,
            modifier = Modifier
                .weight(1f)
                .nonRippleClickable {
                    goToFixedAccountBookItem(getToday("yyyy.MM"))
                }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pin),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "고정 내역 추가", style = textStyle16().copy(fontSize = 14.sp))
            }
        }
        Spacer(modifier = Modifier.width(10.dp))

        DoubleCard(
            bottomCardColor = MyColorTurquoise,
            modifier = Modifier.weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .nonRippleClickable {
                        goToNewAccountBookItem(getToday())
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "신규 내역 추가", style = textStyle16().copy(fontSize = 14.sp))
            }
        }
    }
}

@Composable
fun AccountBookBody(
    viewModel: AccountBookViewModel,
    goToDetail: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        viewModel.info.value?.let {
            item {
                SummaryThisMonthContainer(
                    info = it.thisMonthSummary,
                    goToDetail = goToDetail
                )
            }
            item {
                AnalyzeLastMonthContainer(
                    info = it.lastMonthAnalysis
                )
            }
            item {
                SummaryThisYearContainer(
                    list = it.thisYearSummary
                )
            }
        }
    }
}

@Composable
fun SummaryThisMonthContainer(
    modifier: Modifier = Modifier,
    info: ThisMonthSummary,
    goToDetail: (String) -> Unit
) {
    DoubleCard(
        bottomCardColor = MyColorTurquoise,
        modifier = modifier
            .fillMaxWidth()
            .nonRippleClickable { goToDetail(getToday()) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorTurquoise)
        ) {
            Text(
                text = "이번 달 요약",
                style = textStyle16B(),
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp, top = 4.dp, bottom = 4.dp)
                    .size(24.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MyColorBlack)
        )

        Text(
            text = "${info.startDate} ~ ${info.endDate}",
            style = textStyle12().copy(color = MyColorGray, textAlign = TextAlign.End),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, end = 15.dp)
        )

        TitleAmountRow(
            title = "수입",
            amount = info.income,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        TitleAmountRow(
            title = "지출",
            amount = info.expenditure,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        DashLine(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        TitleAmountRow(
            title = "차액",
            amount = info.difference,
            isAmountBold = true,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun AnalyzeLastMonthContainer(
    modifier: Modifier = Modifier,
    info: LastMonthAnalysis
) {
    DoubleCard(
        bottomCardColor = MyColorTurquoise,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorTurquoise)
        ) {
            Text(
                text = "지난 달 분석",
                style = textStyle16B(),
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp, top = 4.dp, bottom = 4.dp)
                    .size(24.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MyColorBlack)
        )

        Text(
            text = "${info.start} ~ ${info.end}",
            style = textStyle12().copy(color = MyColorGray, textAlign = TextAlign.End),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, end = 15.dp)
        )

        info.result.forEach {
            AnalyzeLastMonthItem(
                item = it,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun AnalyzeLastMonthItem(
    modifier: Modifier = Modifier,
    item: LastMonthAnalysisItem
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        ImageDoubleCard(
            resId = IncomeExpenditureType.getImageByType(item.usageType),
            imageSize = DpSize(24.dp, 24.dp),
            innerPadding = PaddingValues(3.dp),
            topCardColor = if (item.amount < 0) MyColorRed else MyColorTurquoise,
            connerSize = 5.dp,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            Text(
                text = IncomeExpenditureType.getNameByType(item.usageType),
                style = textStyle16B(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "60%",
                style = textStyle12().copy(color = MyColorGray, fontSize = 10.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(text = item.amount.formatAmountWithSign(), style = textStyle16B())
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SummaryThisYearContainer(
    modifier: Modifier = Modifier,
    list: List<ThisYearSummaryItem>
) {
    DoubleCard(
        bottomCardColor = MyColorTurquoise,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorTurquoise)
        ) {
            Text(
                text = "1년 요약",
                style = textStyle16B(),
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp, top = 4.dp, bottom = 4.dp)
                    .size(24.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MyColorBlack)
        )

        Text(
            text = "2023년",
            style = textStyle12().copy(color = MyColorGray, textAlign = TextAlign.End),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, end = 15.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            maxItemsInEachRow = 4,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            list.forEach {
                SummaryThisYearItem(
                    item = it,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SummaryThisYearItem(
    item: ThisYearSummaryItem,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CenteredDoubleCard(
            topCardColor = when {
                item.info == 0 -> MyColorLightGray
                item.info > 0 -> MyColorTurquoise
                else -> MyColorRed
            },
            modifier = Modifier.size(46.dp)
        ) {
            Text(text = item.month.toString(), style = textStyle16B().copy(fontSize = 18.sp))
        }

        Text(
            text = item.info.formatAmountInTenThousand(),
            style = textStyle12().copy(color = MyColorGray),
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}