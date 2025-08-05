package com.example.mjapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.getToday
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle24B

@Composable
fun HomeScreen(
    navHostController: NavHostController? = null,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            Text(
                text = getToday("yyyy년 MM월"),
                style = textStyle24B().copy(color = MyColorPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
        },
        bodyContent = {
            HomeBody(navHostController)
        },
        modifier = Modifier.background(MyColorBlack)
    )
}

@Composable
fun HomeBody(navHostController: NavHostController? = null) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        TempHomeItem("게임") { navHostController?.navigate(NavScreen2.Game) }
        TempHomeItem("일정") { navHostController?.navigate(NavScreen2.Schedule) }
        TempHomeItem("가계부") { navHostController?.navigate(NavScreen2.AccountBook) }
        TempHomeItem("기타") { navHostController?.navigate(NavScreen2.Other) }
    }
}

@Composable
fun TempHomeItem(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MyColorDarkBlue, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .nonRippleClickable(onClick)
    ) {
        Text(text, style = textStyle16B(MyColorWhite))
    }
}