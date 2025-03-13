package com.example.mjapp.ui.screen.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mjapp.util.textStyle24B
import com.example.mjapp.R
import com.example.mjapp.ui.custom.PageMoveCard
import com.example.mjapp.ui.custom.PageMoveCardItem
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.util.makeRouteWithArgs
import com.example.mjapp.util.textStyle16B

@Composable
fun OtherScreen(
    goToScreen: (String) -> Unit
) {
    val list = listOf(
        PageMoveCardItem(
            text = "인터넷 즐겨찾기",
            imageRes = R.drawable.ic_network,
            onClick = { goToScreen(NavScreen.InternetFavorites.item.routeWithPostFix) }
        ),
        PageMoveCardItem(
            text = "영단어 암기",
            imageRes = R.drawable.ic_english_study,
            onClick = {
                goToScreen(
                    makeRouteWithArgs(
                        NavScreen.Note.item.route,
                        false.toString()
                    )
                )
            }
        ),
        PageMoveCardItem(
            text = "단어 테스트하기",
            imageRes = R.drawable.ic_exam,
            onClick = {
                goToScreen(
                    makeRouteWithArgs(
                        NavScreen.Note.item.route,
                        true.toString()
                    )
                )
            }
        ),
    )

    LazyColumn(
        contentPadding = PaddingValues(top = 22.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Text(text = "기타", style = textStyle24B())
            Spacer(modifier = Modifier.height(5.dp))
        }

        list.forEach {
            item {
                PageMoveCard(
                    bottomCardColor = MyColorBeige,
                    icon = {
                        Icon(
                            painter = painterResource(id = it.imageRes),
                            contentDescription = null,
                            tint = MyColorBlack,
                            modifier = Modifier.size(64.dp)
                        )
                    },
                    content = {
                        Text(
                            text = it.text,
                            style = textStyle16B().copy(fontSize = 18.sp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = it.onClick
                )
            }
        }
    }
}