package com.example.mjapp.ui.structure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel: ViewModel() {

    protected val _status = MutableStateFlow(BaseStatus())
    val status: StateFlow<BaseStatus> = _status

}