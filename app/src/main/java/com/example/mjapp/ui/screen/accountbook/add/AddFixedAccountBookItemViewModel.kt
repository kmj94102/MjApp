package com.example.mjapp.ui.screen.accountbook.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.repository.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFixedAccountBookItemViewModel @Inject constructor(
    private val repository: AccountBookRepository
): ViewModel() {

    private fun re() = viewModelScope.launch {
        repository
    }

}