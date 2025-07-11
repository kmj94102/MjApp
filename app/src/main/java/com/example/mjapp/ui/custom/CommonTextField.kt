package com.example.mjapp.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorBlue
import com.example.mjapp.ui.theme.MyColorHintGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.util.textStyle14

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun CommonTextField(
    value: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = textStyle14(),
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
    shape: Shape = RoundedCornerShape(16),
    backgroundColor: Color = MyColorLightBlack,
    unfocusedIndicatorColor: Color = MyColorLightBlack,
    focusedIndicatorColor: Color = MyColorBlue,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onSearch: (String) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val focusState = remember { mutableStateOf(false) }
    val borderColor = if (focusState.value) focusedIndicatorColor else unfocusedIndicatorColor

    Box(
        modifier = Modifier
            .background(backgroundColor, shape)
            .border(1.dp, borderColor, shape)
    ) {
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
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    contentPadding = contentPadding,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = hint,
                            style = textStyle14(color = MyColorHintGray)
                        )
                    }
                )
            },
            cursorBrush = SolidColor(MyColorBlue),
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState.value = it.isFocused }
        )
    }
}