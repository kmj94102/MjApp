package com.example.mjapp.ui.screen.accountbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CenteredDoubleCard
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.DashLine
import com.example.mjapp.ui.custom.ImageDoubleCard
import com.example.mjapp.ui.custom.TitleText
import com.example.mjapp.ui.dialog.UsageHistoryDialog
import com.example.mjapp.ui.screen.accountbook.add.IncomeExpenditureType
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseContainer
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.*
import com.example.network.model.DateConfiguration
import com.example.network.model.LastMonthAnalysis
import com.example.network.model.LastMonthAnalysisItem
import com.example.network.model.ThisMonthSummary
import com.example.network.model.ThisYearSummaryItem

@Composable
fun AccountBookScreen(
    navController: NavHostController?,
    viewModel: AccountBookViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack),
        headerContent = {
            CommonGnb(
                title = "가계부",
                startButton = {
                    CommonGnbBackButton { navController?.popBackStack() }
                },
                endButton = {
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        tint = MyColorWhite,
                        modifier = Modifier
                            .size(28.dp)
                            .nonRippleClickable {
                                navController?.navigate(NavScreen2.AddAccountBook)
                            }
                    )
                }
            )
        },
        bodyContent = {
            AccountBookBody()
        }
    )
}

@Composable
fun AccountBookBody() {
    val list = listOf(
        TemporaryData(
            date = "7월 8일 화요일",
            title = "월급",
            price = 1000000
        ),
        TemporaryData(
            date = "7월 8일 화요일",
            title = "월급2",
            price = 1300000
        ),
        TemporaryData(
            date = "7월 8일 화요일",
            title = "월급3",
            price = 600000
        ),
        TemporaryData(
            date = "7월 8일 화요일",
            title = "월급4",
            price = 700000
        ),
        TemporaryData(
            date = "7월 9일 화요일",
            title = "월급5",
            price = 12000000
        ),
        TemporaryData(
            date = "7월 9일 화요일",
            title = "월급6",
            price = 1050000
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(MyColorLightBlack, RoundedCornerShape(32.dp))
            .padding(20.dp)
    ) {
        Text(
            "07.26 ~ 08.25",
            style = textStyle12B(color = MyColorWhite),
            modifier = Modifier
                .background(MyColorDarkBlue, RoundedCornerShape(12.dp))
                .padding(horizontal = 13.dp, vertical = 7.5.dp)
        )
        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("수입", style = textStyle14(color = MyColorGray))
            Spacer(Modifier.weight(1f))

            Text("1,000,000원", style = textStyle16B(color = MyColorWhite))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("지출", style = textStyle14(color = MyColorGray))
            Spacer(Modifier.weight(1f))

            Text("1,000,000원", style = textStyle16B(color = MyColorDarkBlue))
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 50.dp),
    ) {
        var header = ""
        list.forEach { data ->
            if (data.date != header) {
                stickyHeader {
                    Text(
                        data.date,
                        style = textStyle20B(color = MyColorWhite),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
                header = data.date
            }
            item { AccountBookItem(data) }
        }
    }
}

@Composable
fun AccountBookItem(
    item: TemporaryData
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .background(MyColorDarkBlue.copy(alpha = 0.1f), CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_meal),
                contentDescription = null,
                tint = MyColorDarkBlue,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(Modifier.weight(1f))

        Column {
            Text(item.title, style = textStyle14(color = MyColorWhite))
            Text(item.getPriceFormat(), style = textStyle16B(color = MyColorDarkBlue))
        }
    }
}

data class TemporaryData(
    val date: String,
    val title: String,
    val price: Int
) {
    fun getPriceFormat() = price.formatAmount() + "원"
}