package com.example.mjapp.ui.custom

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.util.textStyle16

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun CommonTextField(
    value: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = textStyle16(),
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    contentPadding: PaddingValues = PaddingValues(horizontal = 5.dp, vertical = 0.dp),
    unfocusedIndicatorColor: Color = Color.Transparent,
    focusedIndicatorColor: Color = Color.Transparent,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onSearch: (String) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onTextChange,
        maxLines = maxLines,
        singleLine = singleLine,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onSearch.invoke(value)
            },
            onDone = {
                keyboardController?.hide()
            }
        ),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                placeholder = {
                    Text(text = hint, fontSize = textStyle.fontSize, color = MyColorLightGray)
                },
                contentPadding = contentPadding,
            )
        },
        modifier = modifier
            .indicatorLine(
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    unfocusedIndicatorColor = unfocusedIndicatorColor,
                    focusedIndicatorColor = focusedIndicatorColor
                )
            )
    )
}