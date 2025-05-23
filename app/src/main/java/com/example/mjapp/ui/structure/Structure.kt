package com.example.mjapp.ui.structure

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonLottieAnimation
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.toast
import kotlinx.coroutines.delay

@Composable
fun BaseStructureScreen(
    status: BaseStatus,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    errorScreen: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    Scaffold {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
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
    }

    LaunchedEffect(status.message) {
        if (status.message.trim().isEmpty()) return@LaunchedEffect
        context.toast(status.message)
        delay(500)
        status.updateMessage("")
    }

    LaunchedEffect(status.isFinish) {
        if (status.isFinish) onBackClick?.invoke()
    }
}

@Composable
fun BaseContainer(
    status: BaseStatus,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(top = 22.dp, start = 20.dp, end = 17.dp),
    reload: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    color: Color = MyColorRed,
    errorScreen: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    BaseStructureScreen(
        status = status,
        modifier = modifier,
        onBackClick = onBackClick,
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
            if (errorScreen != null)
                errorScreen()
            else {
                NetworkErrorScreen(
                    onBackClick = onBackClick,
                    color = color,
                    reload = {
                        reload?.invoke()
                    }
                )
            }
        }
    )
}

@Composable
fun HeaderBodyContainer(
    status: BaseStatus,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues =
        PaddingValues(top = 22.dp, start = 20.dp, end = 17.dp, bottom = 0.dp),
    reload: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    color: Color = MyColorRed,
    errorScreen: (@Composable () -> Unit)? = null,
    headerContent: @Composable ColumnScope.() -> Unit,
    bodyContent: @Composable ColumnScope.() -> Unit,
) {
    BaseContainer(
        status = status,
        modifier = modifier,
        paddingValues = paddingValues,
        reload = reload,
        onBackClick = onBackClick,
        color = color,
        errorScreen = errorScreen
    ) {
        headerContent()
        Column(modifier = Modifier.weight(1f)) {
            bodyContent()
        }
    }
}

@Composable
fun HeaderBodyBottomContainer(
    status: BaseStatus,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues =
        PaddingValues(top = 22.dp, start = 20.dp, end = 17.dp, bottom = 10.dp),
    reload: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    color: Color = MyColorRed,
    errorScreen: (@Composable () -> Unit)? = null,
    heightContent: @Composable ColumnScope.() -> Unit,
    bodyContent: @Composable ColumnScope.() -> Unit,
    bottomContent: @Composable ColumnScope.() -> Unit
) {
    BaseContainer(
        status = status,
        paddingValues = paddingValues,
        reload = reload,
        onBackClick = onBackClick,
        color = color,
        errorScreen = errorScreen,
        modifier = modifier
    ) {
        heightContent()
        Column(modifier = Modifier.weight(1f)) {
            bodyContent()
        }
        bottomContent()
    }
}

@Composable
fun NetworkErrorScreen(
    onBackClick: (() -> Unit)? = null,
    color: Color = MyColorRed,
    reload: () -> Unit,
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
        onBackClick?.let {
            IconBox(
                boxColor = color,
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 22.dp, start = 20.dp)
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Dialog(onDismissRequest = {}) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CommonLottieAnimation(
                resId = R.raw.lottie_loading,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}