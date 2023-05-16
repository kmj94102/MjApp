package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.network.repository.ElswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ElswordCounterViewModel @Inject constructor(
    private val repository: ElswordRepository
) : ViewModel() {

    private val _selectCounter = mutableStateOf(0)
    val selectCounter: State<Int> = _selectCounter

    val counterList = repository.fetchCounterList()

}