package com.example.network.model

fun Throwable.checkNetworkError() = message?.uppercase()?.contains("HTTP") == true

object NetworkError : Throwable()

fun <T> Result<T>.getFailureThrow() =
    onFailure {
        it.printStackTrace()
        throw if (it.checkNetworkError()) NetworkError else it
    }
