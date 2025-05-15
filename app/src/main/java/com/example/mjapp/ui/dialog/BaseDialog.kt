package com.example.mjapp.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonLottieAnimation
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.structure.BaseStatus
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle18B

// todo - 디자인 전체 수정후 title 제거 예정
@Composable
fun BaseDialog(
    isShow: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    shape: RoundedCornerShape = RoundedCornerShape(25.dp),
    backgroundColor: Color = MyColorBlack,
    isCancelable: Boolean = true,
    title: String = "",
    dialogHeight: Dp = 0.dp,
    innerPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
    topContents: @Composable () -> Unit = { DialogCloseButton(onClose = onDismiss) },
    bodyContents: @Composable ColumnScope.() -> Unit = {},
    bottomContents: @Composable () -> Unit = {},
) {
    if (isShow.not()) return

    Dialog(
        onDismissRequest = { if (isCancelable) onDismiss() }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape)
                .background(backgroundColor)
                .padding(paddingValues = innerPadding)
                .then(
                    if (dialogHeight == 0.dp) Modifier.wrapContentHeight()
                    else Modifier.height(dialogHeight)
                )
        ) {
            topContents()
            bodyContents()
            bottomContents()
        }
    }
}

@Composable
fun ConfirmCancelDialog(
    isShow: Boolean,
    isCancelable: Boolean = true,
    title: String,
    cancelText: String = "취소",
    onCancelClick: () -> Unit,
    confirmText: String = "확인",
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit,
    topButtonContents: @Composable () -> Unit = { DialogCloseButton(onClose = onDismiss) },
    bodyContents: @Composable ColumnScope.() -> Unit = {},
    color: Color,
) {
    BaseDialog(
        isShow = isShow,
        isCancelable = isCancelable,
        topContents = topButtonContents,
        bodyContents = bodyContents,
        bottomContents = {
            Row {
                DoubleCardButton(
                    text = cancelText,
                    onClick = onCancelClick,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                DoubleCardButton(
                    text = confirmText,
                    onClick = onConfirmClick,
                    topCardColor = color,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        onDismiss = onDismiss
    )
}

@Composable
fun ConfirmCancelDialog(
    isShow: Boolean,
    isCancelable: Boolean = true,
    cancelText: String = "취소",
    cancelTextStyle: TextStyle = textStyle18B(MyColorGray),
    cancelBackgroundColor: Color = MyColorLightBlack,
    onCancelClick: () -> Unit,
    confirmText: String = "확인",
    onConfirmClick: () -> Unit,
    confirmTextStyle: TextStyle = textStyle18B(MyColorWhite),
    confirmBackgroundColor: Color = MyColorDarkBlue,
    onDismiss: () -> Unit,
    innerPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
    topContents: @Composable () -> Unit = { DialogCloseButton(onClose = onDismiss) },
    bodyContents: @Composable ColumnScope.() -> Unit = {},
) {
    BaseDialog(
        isShow = isShow,
        isCancelable = isCancelable,
        innerPadding = innerPadding,
        topContents = topContents,
        bodyContents = bodyContents,
        bottomContents = {
            Row {
                TextButton(
                    text = cancelText,
                    textStyle = cancelTextStyle,
                    onClick = onCancelClick,
                    innerPadding = PaddingValues(vertical = 13.dp),
                    backgroundColor = cancelBackgroundColor,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    text = confirmText,
                    textStyle = confirmTextStyle,
                    onClick = onConfirmClick,
                    innerPadding = PaddingValues(vertical = 13.dp),
                    backgroundColor = confirmBackgroundColor,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        onDismiss = onDismiss
    )
}


@Composable
fun DialogCloseButton(
    onClose: () -> Unit,
    color: Color = MyColorRed
) {
    IconBox(
        boxColor = color,
        boxShape = CircleShape,
        iconSize = 21.dp,
        iconRes = R.drawable.ic_close,
        onClick = onClose
    )
}

@Composable
fun StatusDialog(
    status: BaseStatus,
    isShow: Boolean,
    isCancelable: Boolean = true,
    title: String,
    onDismiss: () -> Unit,
    dialogHeight: Dp = 0.dp,
    topButtonContents: @Composable () -> Unit = { DialogCloseButton(onClose = onDismiss) },
    bodyContents: @Composable ColumnScope.() -> Unit = {},
    bottomContents: @Composable () -> Unit = {}
) {
    BaseDialog(
        isShow = isShow,
        isCancelable = isCancelable,
        title = title,
        dialogHeight = dialogHeight,
        topContents = topButtonContents,
        bodyContents = {
            when {
                status.isLoading -> {
                    CommonLottieAnimation(
                        resId = R.raw.lottie_loading,
                        modifier = Modifier
                            .padding(vertical = 30.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                status.isNetworkError -> {
                    CommonLottieAnimation(
                        resId = R.raw.lottie_error,
                        modifier = Modifier
                            .padding(vertical = 30.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                else -> {
                    bodyContents()
                }
            }
        },
        bottomContents = bottomContents,
        onDismiss = onDismiss
    )
}

@Composable
fun ConfirmCancelStatusDialog(
    status: BaseStatus,
    isShow: Boolean,
    isCancelable: Boolean = true,
    title: String,
    cancelText: String = "취소",
    onCancelClick: () -> Unit,
    confirmText: String = "확인",
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit,
    topContents: @Composable () -> Unit = { DialogCloseButton(onClose = onDismiss) },
    bodyContents: @Composable ColumnScope.() -> Unit = {},
    color: Color,
) {
    StatusDialog(
        status = status,
        isShow = isShow,
        title = title,
        isCancelable = isCancelable,
        topButtonContents = topContents,
        bodyContents = bodyContents,
        bottomContents = {
            Row {
                DoubleCardButton(
                    text = cancelText,
                    onClick = onCancelClick,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                DoubleCardButton(
                    text = confirmText,
                    onClick = onConfirmClick,
                    topCardColor = color,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        onDismiss = onDismiss
    )
}

@Composable
fun PokemonDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    topIcon: @Composable () -> Unit = {},
    bodyContents: @Composable ColumnScope.() -> Unit = {},
    bottomContents: @Composable RowScope.() -> Unit = { Spacer(modifier = Modifier.height(30.dp)) }
) {
    if (isShow.not()) return
    val brush = Brush.verticalGradient(listOf(Color(0xFF0E2F60), Color(0xFF1F789B)))

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .border(1.dp, MyColorBlack, RoundedCornerShape(15.dp))
                .background(MyColorWhite)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                topIcon()
                Spacer(modifier = Modifier.weight(1f))
                DialogCloseButton(onClose = onDismiss)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = brush)
            ) {
                bodyContents()
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                bottomContents()
            }
        }
    }
}