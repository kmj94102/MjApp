package com.example.mjapp.ui.screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mjapp.ui.theme.MyColorSkyBlue

@Composable
fun CalendarScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColorSkyBlue)
    ) {
        Text(text = "Calendar", modifier = Modifier.align(Alignment.Center))
    }
}