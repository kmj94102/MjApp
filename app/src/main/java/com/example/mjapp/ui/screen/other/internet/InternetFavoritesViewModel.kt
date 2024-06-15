package com.example.mjapp.ui.screen.other.internet

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.update
import com.example.network.model.InternetFavorite
import com.example.network.repository.InternetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternetFavoritesViewModel @Inject constructor(
    private val repository: InternetRepository
) : BaseViewModel() {

    private val homeUrl = "https://m.naver.com/"
    private var initUrl = ""

    private val _state = mutableStateOf(InternetState())
    val state: State<InternetState> = _state

    private val _loadState = MutableStateFlow(_state.value.getSelectAddress())
    val loadState: StateFlow<String> = _loadState

    init {
        fetchFavorites()
    }

    fun insertFavorite(favorite: InternetFavorite) = viewModelScope.launch {
        if (_state.value.list.map { it.address }.contains(favorite.address)) {
            updateMessage("이미 등록 된 주소입니다.")
            return@launch
        }

        repository
            .insertFavorite(favorite.toEntity())
            .onSuccess {
                updateMessage("등록 완료")
                initUrl = favorite.address
            }
            .onFailure {
                updateMessage(it.message ?: "등록에 실패하였습니다.")
            }
    }

    fun fetchFavorites() {
        repository
            .fetchFavorites()
            .onStart { startLoading() }
            .onEach { newList ->
                _state.update { it.copy(list = newList) }
                updateSelectIndex()
                endLoading()
            }
            .catch {
                _state.update { it.copy(list = emptyList()) }
                endLoading()
            }
            .launchIn(viewModelScope)
    }

    private fun updateSelectIndex() {
        val index = _state.value.list.indexOfFirst { it.address == initUrl }

        _state.update { it.copy(selectIndex = if (index == -1) 0 else index) }
        _loadState.value = _state.value.getSelectAddress()
    }

    fun updateSelectIndex(index: Int) {
        _state.update { it.copy(selectIndex = index) }
        _loadState.value = _state.value.getSelectAddress()
    }

    fun goToHome() {
        _loadState.value = homeUrl
    }

    fun deleteItem(id: Int) = viewModelScope.launch {
        repository.deleteItem(id)
    }

    fun updateUrl(url: String) {
        _loadState.value = url
    }

    fun isFavorite() =
        _state.value.list.map { it.address }
            .contains(_loadState.value)

}