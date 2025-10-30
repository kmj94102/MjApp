package com.example.mjapp.ui.screen.game.dmo.union.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle20
import com.example.mjapp.util.textStyle20B
import com.example.network.model.ConditionInfo
import com.example.network.model.DmoDigimonInfo
import com.example.network.model.DmoUnionDetail

@Composable
fun DmoUnionDetailScreen(
    navHostController: NavHostController? = null,
    viewModel: DmoUnionDetailViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val info by viewModel.info

    HeaderBodyContainer(
        status = status,
        modifier = Modifier.background(MyColorBlack),
        paddingValues = PaddingValues(),
        headerContent = {
            CommonGnb(
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                },
                title = info.name
            )
        },
        bodyContent = {
            DmoUnionDetailBody(info)
        }
    )
}

@Composable
fun DmoUnionDetailBody(info: DmoUnionDetail) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {
        item { DmoUnionConditions(info) }
        item { DmoUnionDigimonList(info.digimonInfo) }
    }
}

@Composable
fun DmoUnionConditions(info: DmoUnionDetail) {
    Row(
        modifier = Modifier.padding(top = 20.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
    ) {
        Text("달성조건", style = textStyle20B(color = MyColorWhite))
        Spacer(Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_reload),
            contentDescription = null,
            tint = MyColorGray
        )
        Spacer(Modifier.width(3.dp))

        Text("내용 갱신", style = textStyle20(color = MyColorGray))
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        info.conditionInfo.forEach {
            ConditionItem(it, info.getProgress(it.conditionId))
        }
    }
}

@Composable
fun ConditionItem(
    item: ConditionInfo,
    progress: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (item.isComplete == true) MyColorDarkBlue.copy(alpha = 0.1f) else MyColorLightBlack,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (item.isComplete == true) MyColorDarkBlue else MyColorLightBlack,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            item.getConditionInfo(),
            style = textStyle16(color = if (item.isComplete == true) MyColorWhite else MyColorGray)
        )
        Text(progress, style = textStyle16(color = MyColorWhite))
    }
}

@Composable
fun DmoUnionDigimonList(list: List<DmoDigimonInfo>) {
    Text(
        "디지몬 목록",
        style = textStyle20B(color = MyColorWhite),
        modifier = Modifier.padding(top = 20.dp, bottom = 16.dp, start = 24.dp)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(MyColorLightBlack, RoundedCornerShape(8.dp))
            .padding(20.dp)
    ) {
        list.forEach {
            DmoUnionDigimonItem(it)
        }
    }
}

@Composable
fun DmoUnionDigimonItem(item: DmoDigimonInfo) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .border(1.dp, MyColorLightGray, RoundedCornerShape(5.dp))
                .background(
                    color = if(item.isOpen) MyColorLightGray else Color.Transparent,
                    shape = RoundedCornerShape(5.dp)
                )
        )
        Spacer(Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                item.name,
                style = textStyle14(MyColorWhite),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))

            Text(
                item.getStatus(),
                style = textStyle14(MyColorLightGray),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}