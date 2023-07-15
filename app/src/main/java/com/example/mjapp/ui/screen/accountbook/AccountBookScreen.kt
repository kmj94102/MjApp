package com.example.mjapp.ui.screen.accountbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DashLine
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.*

@Composable
fun AccountBookScreen(
    goToAdInComeExpenditure: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "가계부",
            style = textStyle24B().copy(color = MyColorTurquoise),
            modifier = Modifier.padding(top = 22.dp, start = 20.dp, bottom = 10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp)
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
                modifier = Modifier.weight(1f)
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
                            goToAdInComeExpenditure(getToday())
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pin),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "신규 내역 추가", style = textStyle16().copy(fontSize = 14.sp))
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(start = 20.dp, end = 17.dp, top = 10.dp, bottom = 70.dp)
        ) {

        }
    }
}

@Composable
fun SummaryThisMonthContainer(
    modifier: Modifier = Modifier
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
            text = "23.05.26 ~ 23.06.25",
            style = textStyle12().copy(color = MyColorGray, textAlign = TextAlign.End),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, end = 15.dp)
        )

        TitleAmountRow(
            title = "수입",
            amount = 1_000_000,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        TitleAmountRow(
            title = "지출",
            amount = -1_000_000,
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
            amount = 0,
            isAmountBold = true,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun AnalyzeLastMonthContainer(
    modifier: Modifier = Modifier
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
            text = "23.05.26 ~ 23.06.25",
            style = textStyle12().copy(color = MyColorGray, textAlign = TextAlign.End),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, end = 15.dp)
        )

        AnalyzeLastMonthItem(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
        )

        AnalyzeLastMonthItem(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
        )

        AnalyzeLastMonthItem(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
        )

        AnalyzeLastMonthItem(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun AnalyzeLastMonthItem(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        DoubleCard(
            topCardColor = MyColorSkyBlue,
            connerSize = 5.dp,
            modifier = Modifier.width(33.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_meal),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(3.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            Text(
                text = "사용 내용",
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

        Text(text = "- 5,000 원", style = textStyle16B())
    }
}

@Composable
fun SummaryThisYearContainer(
    modifier: Modifier = Modifier
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

        val monthInfoList = listOf(
            Pair(1, 1_000_000),
            Pair(2, -1_000_000),
            Pair(3, 11_000_000),
            Pair(4, -51_000_000),
            Pair(5, 0),
            Pair(6, 0),
            Pair(7, 0),
            Pair(8, 0),
            Pair(9, 0),
            Pair(10, 1_000_000),
            Pair(11, -1_000_000),
            Pair(12, 1_000_000),
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            contentPadding = PaddingValues(vertical = 5.dp, horizontal = 10.dp)
        ) {
            monthInfoList.forEach { (month, balance) ->
                item {
                    SummaryThisYearItem(
                        month = month,
                        balance = balance,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryThisYearItem(
    month: Int,
    balance: Int?,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        DoubleCard(
            topCardColor = when {
                balance == null || balance == 0 -> MyColorLightGray
                balance > 0 -> MyColorTurquoise
                else -> MyColorRed
            },
            modifier = Modifier.width(46.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(43.dp)
            ) {
                Text(text = month.toString(), style = textStyle16B().copy(fontSize = 18.sp))
            }
        }

        Text(
            text = balance?.formatAmountInTenThousand() ?: "0 원",
            style = textStyle12().copy(color = MyColorGray),
            modifier = Modifier.padding(top = 3.dp)
        )
    }
}

@Preview
@Composable
fun TestScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        SummaryThisYearContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp)
        )
    }
}