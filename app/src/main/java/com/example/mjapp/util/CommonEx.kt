package com.example.mjapp.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun <T> SnapshotStateList<T>.clearAndAddAll(items: List<T>) {
    clear()
    addAll(items)
}