package com.example.mjapp.ui.structure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonLottieAnimation
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.toast

@Composable
fun BaseStructureScreen(
    status: BaseStatus,
    errorScreen: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColorWhite)
    ) {
        if (status.isNetworkError) {
            errorScreen()
        } else {
            content()
        }

        if (status.isLoading) {
            LoadingScreen()
        }
    }

    LaunchedEffect(status.message) {
        if (status.message.trim().isEmpty()) return@LaunchedEffect
        context.toast(status.message)
    }
}

@Composable
fun BaseContainer(
    status: BaseStatus,
    paddingValues: PaddingValues = PaddingValues(top = 22.dp, start = 20.dp, end = 17.dp),
    reload: (() -> Unit)? = null,
    errorScreen: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    BaseStructureScreen(
        status = status,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        },
        errorScreen = {
            errorScreen?.invoke() ?: NetworkErrorScreen { reload?.invoke() }
        }
    )
}

@Composable
fun HighMediumLowContainer(
    status: BaseStatus,
    paddingValues: PaddingValues =
        PaddingValues(top = 22.dp, start = 20.dp, end = 17.dp, bottom = 10.dp),
    reload: (() -> Unit)? = null,
    errorScreen: (@Composable () -> Unit)? = null,
    heightContent: @Composable () -> Unit,
    mediumContent: @Composable () -> Unit,
    lowContent: @Composable () -> Unit
) {
    BaseContainer(
        status = status,
        paddingValues = paddingValues,
        reload = reload,
        errorScreen = errorScreen
    ) {
        heightContent()
        Box(modifier = Modifier.weight(1f)) {
            mediumContent()
        }
        lowContent()
    }
}

@Composable
fun NetworkErrorScreen(
    reload: () -> Unit
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CommonLottieAnimation(
                resId = R.raw.lottie_error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.network_error_message),
                style = textStyle16(),
                textAlign = TextAlign.Center
            )
            DoubleCard(
                topCardColor = MyColorRed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 17.dp, top = 50.dp)
                    .nonRippleClickable {
                        reload()
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.reload),
                    style = textStyle16B(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CommonLottieAnimation(
            resId = R.raw.lottie_loading,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}