package com.example.mjapp.ui.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonLottieAnimation
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlue
import com.example.mjapp.util.textStyle16
import com.example.network.model.ExaminationScoringResult

@Composable
fun ExamResultDialog(
    isShow: Boolean,
    data: ExaminationScoringResult,
    onDismiss: () -> Unit,
) {
    ConfirmCancelDialog(
        isShow = isShow,
        isCancelable = false,
        title = "채점 결과",
        onCancelClick = {
            onDismiss()
        },
        onConfirmClick = {
            onDismiss()
        },
        onDismiss = onDismiss,
        bodyContents = {
            CommonLottieAnimation(
                resId = R.raw.lottie_result,
                iterations = 1,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MyColorBlue
                        )
                    ) {
                        append(data.getScore())
                    }
                    append(data.getCount())
                },
                style = textStyle16().copy(fontSize = 14.sp),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )
        },
        color = MyColorBeige
    )
}