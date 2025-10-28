package com.example.mjapp.ui.screen.game

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.screen.navigation.BottomNavItems
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle24B
import com.example.mjapp.R
import com.example.mjapp.ui.custom.OutlineText
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseStatus
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.*

@Composable
fun GameScreen(
    navHostController: NavHostController? = null
) {
    HeaderBodyContainer(
        status = BaseStatus(),
        headerContent = {
            Text(
                text = BottomNavItems.Game.item.title,
                style = textStyle24B().copy(color = MyColorRed),
                modifier = Modifier.padding(bottom = 10.dp)
            )
        },
        bodyContent = {
            GameBody(navHostController = navHostController)
        }
    )
}

@Composable
fun GameBody(
    navHostController: NavHostController?
) {
    val list = createGameCardItemList()

    LazyColumn(
        contentPadding = PaddingValues(bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        list.forEach {
            item {
                GameCard(
                    gameCardItem = it,
                    navHostController = navHostController
                )
            }
        }
    }
}

data class GameCardItem(
    val text: String,
    @DrawableRes
    val imageRes: Int,
    val color: Color,
    val pageAddress: String = "",
    val page: NavScreen2? = null
)

fun createGameCardItemList() = listOf(
    GameCardItem(
        text = "포켓몬\n도감",
        imageRes = R.drawable.img_pokemon_dex,
        color = MyColorBeige,
        pageAddress = "",
        page = NavScreen2.PokemonDex
    ),
    GameCardItem(
        text = "디지몬 유니온",
        imageRes = R.drawable.img_pokemon_dex,
        color = MyColorBeige,
        page = NavScreen2.DmoUnion
    ),
    GameCardItem(
        text = "타이틀\n도감",
        imageRes = R.drawable.img_pokemon_dex,
        color = MyColorBeige,
        pageAddress = NavScreen.GenerationDex.item.routeWithPostFix
    ),
    GameCardItem(
        text = "포켓몬\n카운터",
        imageRes = R.drawable.img_pokemon_counter,
        color = MyColorTurquoise,
        page = NavScreen2.PokemonCounter
    ),
    GameCardItem(
        text = "페르소나3",
        imageRes = R.drawable.img_pokemon_counter,
        color = MyColorTurquoise,
        page = NavScreen2.Persona3
    ),
    GameCardItem(
        text = "엘소드\n캐릭터 소개",
        imageRes = R.drawable.img_elsword_introduce,
        color = MyColorRed,
        pageAddress = NavScreen.ElswordIntroduce.item.routeWithPostFix
    ),
    GameCardItem(
        text = "엘소드\n카운터",
        imageRes = R.drawable.img_elsword_counter,
        color = MyColorPurple,
        pageAddress = NavScreen.ElswordCounter.item.routeWithPostFix
    ),
    GameCardItem(
        text = "포켓몬 이미지\n수정",
        imageRes = R.drawable.img_pokemon_dex,
        color = MyColorBeige,
        pageAddress = NavScreen.PokemonImageChange.item.routeWithPostFix
    ),
)

@Composable
fun GameCard(
    gameCardItem: GameCardItem,
    navHostController: NavHostController?
) {
    DoubleCard(
        bottomCardColor = gameCardItem.color,
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .nonRippleClickable {
                if (gameCardItem.page != null) {
                    navHostController?.navigate(gameCardItem.page)
                }
            }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = gameCardItem.imageRes),
                contentDescription = gameCardItem.text,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(125.dp)
            )
            OutlineText(
                text = gameCardItem.text,
                style = textStyle24B().copy(
                    fontSize = 32.sp,
                    textAlign = TextAlign.End,
                    color = gameCardItem.color,
                    lineHeight = 40.sp
                ),
                outlineColor = MyColorBlack,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
            )
        }
    }
}