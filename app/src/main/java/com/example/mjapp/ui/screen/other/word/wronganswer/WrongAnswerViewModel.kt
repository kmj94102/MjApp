package com.example.mjapp.ui.screen.other.word.wronganswer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.update
import com.example.network.model.NetworkError
import com.example.network.model.WrongAnswerParam
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WrongAnswerViewModel @Inject constructor(
    private val repository: VocabularyRepository
) : BaseViewModel() {
    private val _state = mutableStateOf(WrongAnswerState())
    val state: State<WrongAnswerState> = _state

    val isTimestamp
        get() = _state.value.sort == WrongAnswerParam.SORT_TIMESTAMP

    private var page = 0
    private val limit = 100

    init {
        fetchWrongAnswer()
    }

    private fun fetchWrongAnswer() {
        repository
            .fetchWrongAnswer(
                WrongAnswerParam(
                    sort = _state.value.sort,
                    skip = page,
                    limit = limit
                )
            )
            .setLoadingState()
            .onEach { result ->
                _state.update {
                    it.copy(
                        list = it.list + result.list,
                        totalCount = result.totalCount
                    )
                }
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState()
                else it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }

    fun fetchMoreData(index: Int) {
        if (_state.value.totalCount <= index + (page * limit)) return

        if (index >= 20 + (page * limit)) {
            page += 1
            fetchWrongAnswer()
        }
    }

    fun updateSort(isTimestamp: Boolean) {
        if (_state.value.sort == if (isTimestamp) WrongAnswerParam.SORT_TIMESTAMP else WrongAnswerParam.SORT_COUNT) return

        _state.update {
            it.copy(
                sort = if (isTimestamp) WrongAnswerParam.SORT_TIMESTAMP else WrongAnswerParam.SORT_COUNT,
                list = emptyList(),
                totalCount = 0
            )
        }
        page = 0
        fetchWrongAnswer()
    }

}