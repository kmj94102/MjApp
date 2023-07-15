package com.example.mjapp.ui.screen.accountbook

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.util.formatAmountWithSign

@Composable
fun TitleAmountRow(
    modifier: Modifier = Modifier,
    title: String,
    amount: Int,
    titleStyle: TextStyle = TextStyle(color = MyColorGray, fontSize = 14.sp),
    amountStyle: TextStyle = TextStyle(fontSize = 16.sp),
    isAmountBold: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = title, style = titleStyle)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = amount.formatAmountWithSign(),
            style = amountStyle.copy(
                color = if (amount >= 0) MyColorTurquoise else MyColorRed
            ),
            fontWeight = if (isAmountBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

