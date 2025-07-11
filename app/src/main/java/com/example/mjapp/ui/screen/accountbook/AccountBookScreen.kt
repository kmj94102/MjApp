package com.example.mjapp.ui.screen.accountbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.screen.accountbook.add.IncomeExpenditureType
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.ui.theme.MyRed
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle20B
import com.example.network.model.AccountBookDetailInfo
import com.example.network.model.AccountBookItem

@Composable
fun AccountBookScreen(
    navController: NavHostController?,
    viewModel: AccountBookViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val info by viewModel.info.collectAsStateWithLifecycle()

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
            AccountBookBody(info)
        }
    )
}

@Composable
fun AccountBookBody(info: AccountBookDetailInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(MyColorLightBlack, RoundedCornerShape(32.dp))
            .padding(20.dp)
    ) {
        Text(
            info.getDateInfo(),
            style = textStyle12B(color = MyColorWhite),
            modifier = Modifier
                .background(MyColorDarkBlue, RoundedCornerShape(12.dp))
                .padding(horizontal = 13.dp, vertical = 7.5.dp)
        )
        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("수입", style = textStyle14(color = MyColorGray))
            Spacer(Modifier.weight(1f))

            Text(info.getIncomeFormat(), style = textStyle16B(color = MyColorWhite))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("지출", style = textStyle14(color = MyColorGray))
            Spacer(Modifier.weight(1f))

            Text(
                info.getExpenditureFormat(),
                style = textStyle16B(
                    color = if(info.isExcessOfBudget()) MyRed else MyColorDarkBlue
                )
            )
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 50.dp),
    ) {
        var header = ""
        info.list.forEach { data ->
            if (data.date != header) {
                stickyHeader {
                    Text(
                        data.date,
                        style = textStyle20B(color = MyColorWhite),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MyColorBlack)
                            .padding(vertical = 12.dp)
                    )
                }
                header = data.date
            }
            item { AccountBookInfo(data) }
        }
    }
}

@Composable
fun AccountBookInfo(
    item: AccountBookItem
) {
    val color = if(item.isIncome()) MyColorDarkBlue else MyRed
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .background(color.copy(alpha = 0.1f), CircleShape)
        ) {
            Icon(
                painter = painterResource(IncomeExpenditureType.getImageByType(item.usageType)),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(item.whereToUse, style = textStyle14(color = MyColorWhite))
            Text(
                item.getFormattedAmount(),
                style = textStyle16B(color = color)
            )
        }
    }
}