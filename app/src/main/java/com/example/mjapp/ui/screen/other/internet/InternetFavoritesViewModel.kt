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

    private val _list = mutableStateListOf<InternetFavorite>()
    val list: List<InternetFavorite> = _list

    private val _selectIndex = mutableIntStateOf(0)
    val selectIndex: State<Int> = _selectIndex

    val selectItem: InternetFavorite?
        get() = runCatching { _list[_selectIndex.intValue] }
            .getOrElse { null }

    private val selectAddress: String
        get() = selectItem?.address ?: homeUrl

    private val _loadState = MutableStateFlow(selectAddress)
    val loadState: StateFlow<String> = _loadState

    init {
        fetchFavorites()
    }

    fun insertFavorite(favorite: InternetFavorite) = viewModelScope.launch {
        if (_list.map { it.address }.contains(favorite.address)) {
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
            .onEach {
                _list.clearAndAddAll(it)
                updateSelectIndex()
                endLoading()
            }
            .catch {
                _list.clear()
                endLoading()
            }
            .launchIn(viewModelScope)
    }

    private fun updateSelectIndex() {
        val index = _list.indexOfFirst { it.address == initUrl }

        _selectIndex.intValue = if (index == -1) 0 else index
        _loadState.value = selectAddress
    }

    fun updateSelectIndex(index: Int) {
        _selectIndex.intValue = index
        _loadState.value = selectAddress
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
        _list.map { it.address }
            .contains(_loadState.value)

}