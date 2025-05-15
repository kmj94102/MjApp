package com.example.mjapp.ui.dialog

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonLottieAnimation
import com.example.mjapp.ui.theme.MyColorBlue
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.network.model.WordTestResult

@Composable
fun ExamResultDialog(
    isShow: Boolean,
    data: WordTestResult,
    onDismiss: () -> Unit,
    goToWrongAnswer: () -> Unit,
) {
    ConfirmCancelDialog(
        isShow = isShow,
        isCancelable = false,
        cancelText = "테스트 종료",
        onCancelClick = {
            goToWrongAnswer()
        },
        confirmText = "오답노트 보기",
        onConfirmClick = {
            onDismiss()
        },
        onDismiss = onDismiss,
        topContents = {
            Text(
                text = "테스트 결과",
                style = textStyle16B(color = MyColorWhite, textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )
        },
        bodyContents = {
            Spacer(Modifier.height(14.dp))
            CommonLottieAnimation(
                resId = R.raw.lottie_result,
                iterations = 1,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(20.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = MyColorBlue
                        )
                    ) {
                        append("${data.score} ")
                    }
                    append(data.getCount())
                },
                style = textStyle16(color = MyColorDarkBlue),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(24.dp))
        }
    )
}