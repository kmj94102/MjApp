package com.example.mjapp.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun <T> List<T>.clearAndAddAll(items: List<T>) =
    toMutableList().also {
        it.clear()
        it.addAll(items)
    }

fun <T> SnapshotStateList<T>.clearAndAddAll(items: List<T>) {
    clear()
    addAll(items)
}

fun <T> MutableState<T>.update(transform: (T) -> T) {
    this.value = transform(this.value)
}