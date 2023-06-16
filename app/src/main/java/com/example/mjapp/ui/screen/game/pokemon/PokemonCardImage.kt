package com.example.mjapp.ui.screen.game.pokemon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable

@Composable
fun PokemonCardImage(
    number: String,
    image: String,
    size: Dp,
    isSelect: Boolean = false,
    onClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .width(size)
            .nonRippleClickable {
                onClick(number, image)
            }
    ) {
        DoubleCard(
            topCardColor = if (isSelect) MyColorRed else MyColorWhite,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = image,
                placeholder = painterResource(id = R.drawable.img_egg),
                error = painterResource(id = R.drawable.img_egg),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            )
        }
    }
}