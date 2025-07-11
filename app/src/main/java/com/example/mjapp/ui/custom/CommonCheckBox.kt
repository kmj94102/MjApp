package com.example.mjapp.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14

@Composable
fun CommonCheckBox(
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    text: String = "",
    textStyle: TextStyle = textStyle14(color = MyColorWhite),
    checkBoxSize: Dp = 22.dp,
    unCheckedColor: Color = MyColorLightGray,
    checkedColor: Color = MyColorDarkBlue,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .nonRippleClickable { onCheckedChange(isChecked.not()) }
    ) {
        Box(
            modifier = Modifier
                .size(checkBoxSize)
                .background(
                    if (isChecked) checkedColor else unCheckedColor,
                    RoundedCornerShape(6.dp)
                )
        )
        Spacer(Modifier.width(8.dp))
        Text(text, style = textStyle)
    }
}