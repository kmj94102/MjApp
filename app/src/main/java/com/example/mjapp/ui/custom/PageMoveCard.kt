package com.example.mjapp.ui.custom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.R

@Composable
fun PageMoveCard(
    modifier: Modifier = Modifier,
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorWhite,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit,
    onClick: () -> Unit
) {
    DoubleCard(
        topCardColor = topCardColor,
        bottomCardColor = bottomCardColor,
        modifier = modifier
            .fillMaxWidth()
            .nonRippleClickable(onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            icon()
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(24.dp)
            )
        }
    }
}

data class PageMoveCardItem(
    val text: String,
    @DrawableRes
    val imageRes: Int,
    val onClick: () -> Unit
)