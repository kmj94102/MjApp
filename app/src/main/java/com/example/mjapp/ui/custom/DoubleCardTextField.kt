package com.example.mjapp.ui.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B

@Composable
fun DoubleCardTextField(
    modifier: Modifier = Modifier,
    connerSize: Dp = 10.dp,
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorWhite,
    value: String,
    onTextChange: (String) -> Unit,
    textStyle: TextStyle = textStyle16(),
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    contentPadding: PaddingValues = PaddingValues(horizontal = 15.dp, vertical = 10.dp),
    unfocusedIndicatorColor: Color = Color.Transparent,
    focusedIndicatorColor: Color = Color.Transparent,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minHeight: Dp = 10.dp,
    tailIcon: @Composable () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    DoubleCard(
        connerSize = connerSize,
        topCardColor = topCardColor,
        bottomCardColor = bottomCardColor,
        minHeight = minHeight,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CommonTextField(
                value = value,
                onTextChange = onTextChange,
                textStyle = textStyle,
                hint = hint,
                visualTransformation = visualTransformation,
                keyboardType = keyboardType,
                imeAction = imeAction,
                contentPadding = contentPadding,
                unfocusedIndicatorColor = unfocusedIndicatorColor,
                focusedIndicatorColor = focusedIndicatorColor,
                readOnly = readOnly,
                singleLine = singleLine,
                maxLines = maxLines,
                onSearch = onSearch,
                modifier = Modifier.weight(1f)
            )

            Box(modifier = Modifier.nonRippleClickable { onSearch(value) }) {
                tailIcon()
            }
        }
    }
}

@Composable
fun DoubleCardText(
    modifier: Modifier = Modifier,
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorWhite,
    onClick: () -> Unit,
    text: String,
    hint: String = "",
    textStyle: TextStyle = textStyle16B().copy(textAlign = TextAlign.Center),
    textColor: Color = MyColorBlack,
    emptyTextColor: Color = MyColorGray,
    innerPadding: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 15.dp)
) {
    DoubleCard(
        topCardColor = topCardColor,
        bottomCardColor = bottomCardColor,
        modifier = modifier.nonRippleClickable(onClick)
    ) {
        Text(
            text = text.ifEmpty { hint },
            style = textStyle,
            color = if (text.isEmpty()) emptyTextColor else textColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        )
    }
}