package com.example.mjapp.ui.screen.other.english_words

import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import com.example.mjapp.ui.structure.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnglishWordsViewModel @Inject constructor(

): BaseViewModel() {

    private val _day = mutableIntStateOf(1)
    val day: IntState = _day

    fun updateDay(day: Int) {
        _day.value = day
    }

}