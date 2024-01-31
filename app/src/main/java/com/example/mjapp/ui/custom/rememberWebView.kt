package com.example.mjapp.ui.custom

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Message
import android.webkit.ConsoleMessage
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun rememberWebView(
    onPageFinished: (String) -> Unit
): WebView {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            webChromeClient = com.example.mjapp.ui.custom.getWebChromeClient()
            webViewClient = getWebViewClient(onPageFinished)
            settings.also {
                it.domStorageEnabled = true
                it.javaScriptEnabled = true
                it.useWideViewPort = true
                it.loadWithOverviewMode = true
                it.allowFileAccess = true
                it.allowContentAccess = true
                it.setSupportZoom(true)
                it.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                it.builtInZoomControls = true
                it.displayZoomControls = false
                it.javaScriptCanOpenWindowsAutomatically = true
                it.loadsImagesAutomatically = true
                it.mediaPlaybackRequiresUserGesture = false
            }
        }
    }
    return webView
}

private fun getWebChromeClient() = object : WebChromeClient() {
    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ) = super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?) =
        super.onConsoleMessage(consoleMessage)
}

private fun getWebViewClient(
    listener: (String) -> Unit
) = object : WebViewClient() {
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: SslError?
    ) {
        super.onReceivedSslError(view, handler, error)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        url?.let(listener)
    }
}