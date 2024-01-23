package com.example.mjapp.ui.screen.other.internet

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.clearAndAddAll
import com.example.network.model.InternetFavorite
import com.example.network.repository.InternetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _list = mutableStateListOf<InternetFavorite>()
    val list: List<InternetFavorite> = _list

    private val _selectIndex = mutableIntStateOf(0)
    val selectIndex: State<Int> = _selectIndex

    private val selectAddress: String
        get() = runCatching {
            _list[_selectIndex.intValue].address
        }.getOrElse {
            "https://www.naver.com"
        }

    private val _reloadState = MutableSharedFlow<Unit>()
    val reloadState: SharedFlow<Unit> = _reloadState

    private val _loadState = MutableStateFlow(selectAddress)
    val loadState: StateFlow<String> = _loadState

    init {
        fetchFavorites()
    }

    fun insertFavorite(favorite: InternetFavorite) = viewModelScope.launch {
        repository
            .insertFavorite(favorite.toEntity())
            .onSuccess {
                updateMessage("등록 완료")
            }
            .onFailure {
                updateMessage(it.message ?: "등록에 실패하였습니다.")
            }
    }

    fun fetchFavorites() {
        repository
            .fetchFavorites()
            .onStart { startLoading() }
            .onEach {
                _list.clearAndAddAll(it)
                updateSelectIndex(0)
                endLoading()
            }
            .catch {
                _list.clear()
                endLoading()
            }
            .launchIn(viewModelScope)
    }

    fun updateSelectIndex(index: Int) {
        _selectIndex.intValue = index
        _loadState.value = selectAddress
    }

    fun reload() = viewModelScope.launch {
        _reloadState.emit(Unit)
    }

    fun deleteItem(id: Int) = viewModelScope.launch {
        repository.deleteItem(id)
    }

}