package com.example.mjapp.ui.screen.game

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.screen.navigation.BottomNavItems
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle24B
import com.example.mjapp.R
import com.example.mjapp.util.Constants
import com.example.mjapp.ui.custom.OutlineText
import com.example.mjapp.ui.theme.*

@Composable
fun GameScreen(
    goToScreen: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 22.dp, bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MyColorWhite)
            .padding(start = 20.dp, end = 17.dp)
    ) {
        item {
            Text(
                text = BottomNavItems.Game.item.title,
                style = textStyle24B().copy(color = MyColorRed),
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
        item {
            GameCard(
                text = "포켓몬\n도감",
                imageRes = R.drawable.img_pokemon_dex,
                color = MyColorBeige,
            ) {
                goToScreen(Constants.PokemonDex)
            }
        }
        item {
            GameCard(
                text = "포켓몬\n카운터",
                imageRes = R.drawable.img_pokemon_counter,
                color = MyColorTurquoise,
            ) {
                goToScreen(Constants.PokemonCounter)
            }
        }
        item {
            GameCard(
                text = "엘소드\n캐릭터 소개",
                imageRes = R.drawable.img_elsword_introduce,
                color = MyColorRed,
            ) {
                goToScreen(Constants.ElswordIntroduce)
            }
        }
        item {
            GameCard(
                text = "엘소드\n카운터",
                imageRes = R.drawable.img_elsword_counter,
                color = MyColorPurple,
            ) {
                goToScreen(Constants.ElswordCounter)
            }
        }
        item {
            GameCard(
                text = "포켓몬\n등록",
                imageRes = R.drawable.img_pokemon_counter,
                color = MyColorTurquoise,
            ) {
                goToScreen(Constants.PokemonAdd)
            }
        }
    }
}

@Composable
fun GameCard(
    text: String,
    @DrawableRes
    imageRes: Int,
    color: Color,
    onClick: () -> Unit
) {
    DoubleCard(
        bottomCardColor = color,
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .nonRippleClickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = text,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(125.dp)
            )
            OutlineText(
                text = text,
                style = textStyle24B().copy(
                    fontSize = 32.sp,
                    textAlign = TextAlign.End,
                    color = color,
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