package com.example.mjapp.ui.screen.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mjapp.ui.theme.MyColorTurquoise

@Composable
fun OtherScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColorTurquoise)
    ) {
        Text(text = "Other", modifier = Modifier.align(Alignment.Center))
    }
}