package com.example.mjapp.ui.screen.game.persona

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle20B

@Composable
fun Persona3Screen(
    navHostController: NavHostController? = null,
    viewModel: Persona3ViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonGnb(
                title = "커뮤 스케줄",
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                },
                endButton = {
                    Image(
                        painter = painterResource(R.drawable.ic_history),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                            .nonRippleClickable {
                                navHostController?.navigate(NavScreen2.Persona3Community)
                            }
                    )
                }
            )
        },
        bodyContent = {
            Persona3Body()
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(Color(0xFF005AA4))
    )
}

@Composable
fun Persona3Body() {
    LazyColumn(
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stickyHeader {
            Text(
                "4월",
                style = textStyle20B(color = Color(0xFF0EF2E5)),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF005AA4))
                    .padding(16.dp)
            )
        }

        items((0..10).toList()) {
            Persona3Item()
        }
    }
}

@Composable
fun Persona3Item() {
    Column(
        modifier = Modifier
            .background(Color(0xCC131234), shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text(
            "1일 수요일",
            style = textStyle12B(color = MyColorWhite),
            modifier = Modifier
                .background(Color(0xFF0036FB), RoundedCornerShape(12.dp))
                .padding(8.dp)
        )
        Spacer(Modifier.height(12.dp))

        Text("아침/낮", style = textStyle14(MyColorGray))
        Spacer(Modifier.height(6.dp))
        Text(
            "아침 행동관련 내용이 들어갑니다.\n두번째줄까지 있는 내용입니다",
            style = textStyle16B(MyColorWhite),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Text("방과후", style = textStyle14(MyColorGray))
        Spacer(Modifier.height(6.dp))
        Text(
            "방과후 행동관련 내용이 들어갑니다.",
            style = textStyle16B(MyColorWhite),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Text("밤", style = textStyle14(MyColorGray))
        Spacer(Modifier.height(6.dp))
        Text(
            "밤 행동관련 내용이 들어갑니다.\n두번째줄까지 있는 내용입니다",
            style = textStyle16B(MyColorWhite),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Text("비고", style = textStyle14(MyColorGray))
        Spacer(Modifier.height(6.dp))
        Text(
            "비고 행동관련 내용이 들어갑니다.",
            style = textStyle16B(MyColorWhite),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(30.dp))

        TextButton(
            text = "완료",
            borderColor = Color(0xFF0EF2E5),
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth()
        ) { }
    }
}