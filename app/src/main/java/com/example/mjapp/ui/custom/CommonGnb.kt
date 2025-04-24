package com.example.mjapp.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mjapp.R
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle20B

@Composable
fun CommonGnb(
    startButton: @Composable () -> Unit = {},
    endButton: @Composable () -> Unit = {},
    title: String = "",
    titleStyle: TextStyle = textStyle20B(color = MyColorWhite, textAlign = TextAlign.Center),
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 11.dp)
    ) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            startButton()
        }

        Text(title, style = titleStyle, modifier = Modifier.align(Alignment.Center))

        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            endButton()
        }
    }
}

@Composable
fun CommonGnbBackButton(
    onBackClick: () -> Unit
) {
    Image(
        painter = painterResource(R.drawable.ic_back),
        contentDescription = null,
        modifier = Modifier.nonRippleClickable { onBackClick() }
    )
}