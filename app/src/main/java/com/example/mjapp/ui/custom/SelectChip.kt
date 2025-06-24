package com.example.mjapp.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14

@Composable
fun SelectChip(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = textStyle14(),
    isSelected: Boolean = false,
    minWidth: Dp = 0.dp,
    selectedColor: Color = MyColorWhite,
    unselectedColor: Color = MyColorLightGray,
    selectedBackground: Color = MyColorRed,
    unselectedBackground: Color = Color.Transparent,
    selectedTextColor: Color = selectedColor,
    unselectedTextColor: Color = unselectedColor,
    paddingValues: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 18.dp),
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(
                1.dp,
                if (isSelected) selectedColor else unselectedColor,
                RoundedCornerShape(25.dp)
            )
            .background(
                if (isSelected) selectedBackground else unselectedBackground,
                RoundedCornerShape(25.dp)
            )
            .padding(paddingValues)
            .widthIn(minWidth)
            .nonRippleClickable(onClick)
    ) {
        Text(
            text,
            style = textStyle.copy(color = if (isSelected) selectedTextColor else unselectedTextColor)
        )
    }
}

@Preview
@Composable
fun SelectChipPreview() {
    Scaffold {
        Row(modifier = Modifier.padding(it)) {
            SelectChip(text = "test", isSelected = true) {

            }
            SelectChip(text = "test2", isSelected = false) {

            }
        }
    }
}