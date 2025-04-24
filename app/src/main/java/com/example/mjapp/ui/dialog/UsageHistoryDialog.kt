package com.example.mjapp.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DashLine
import com.example.mjapp.ui.custom.DividerLine
import com.example.mjapp.ui.dialog.viewmodel.UsageHistoryViewModel
import com.example.mjapp.ui.screen.accountbook.TitleAmountRow
import com.example.mjapp.ui.screen.accountbook.detail.UsageHistoryItem
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle14
import com.example.network.model.AccountBookDetailInfo
import com.example.network.model.AccountBookHistoryDate
import com.example.network.model.AccountBookItem
import com.example.network.model.DateConfiguration

@Composable
fun UsageHistoryDialog(
    isShow: Boolean,
    dateConfiguration: DateConfiguration,
    onDismiss: () -> Unit,
    viewModel: UsageHistoryViewModel = hiltViewModel()
) {
    val info = viewModel.info.value
    val status by viewModel.status.collectAsStateWithLifecycle()

    StatusDialog(
        status = status,
        isShow = isShow,
        title = "사용내역",
        onDismiss = onDismiss,
        dialogHeight = 500.dp,
        bodyContents = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                DividerLine()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Text(text = "기간 :", style = textStyle14())
                    Text(
                        text = "${info.startDate} ~ ${info.endDate}",
                        style = textStyle14(),
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }
                DividerLine()

                UsageHistoryBody(
                    info = info,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    )

    LaunchedEffect(dateConfiguration) {
        viewModel.fetchThisMonthDetail(dateConfiguration)
    }
}

@Composable
fun UsageHistoryBody(
    info: AccountBookDetailInfo,
    modifier: Modifier = Modifier
) {

    when {
        info.list.isEmpty() -> {
            Image(
                painter = painterResource(id = R.drawable.img_pokemon_empty_1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            )
        }

        else -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 10.dp),
                modifier = modifier
            ) {
                items(info.getHistoryList()) {
                    when(it) {
                        is AccountBookHistoryDate -> {
                            Text(
                                text = it.date,
                                style = textStyle14().copy(MyColorGray),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MyColorWhite)
                                    .padding(top = 5.dp)
                            )
                        }
                        is AccountBookItem -> {
                            UsageHistoryItem(
                                item = it,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            DividerLine()
            TitleAmountRow(
                title = "수입",
                amount = info.income,
                isAmountBold = true,
                modifier = Modifier.padding(top = 10.dp)
            )
            TitleAmountRow(
                title = "지출",
                amount = info.expenditure,
                isAmountBold = true,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            DashLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            TitleAmountRow(
                title = "차액",
                amount = info.income + info.expenditure,
                isAmountBold = true,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}