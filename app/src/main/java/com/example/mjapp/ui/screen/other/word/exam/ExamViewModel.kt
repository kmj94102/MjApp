package com.example.mjapp.ui.screen.other.word.exam

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseViewModel
import com.example.mjapp.util.update
import com.example.network.model.NetworkError
import com.example.network.model.NoteIdParam
import com.example.network.model.toWordTestResult
import com.example.network.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val repository: VocabularyRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val info = savedStateHandle.toRoute<NavScreen2.WordExam>()

    private val _state = mutableStateOf(ExamState())
    val state: State<ExamState> = _state

    init {
        fetchExamItems()
    }

    fun getTitle() = info.title

    private fun fetchExamItems() {
        repository
            .fetchWords(NoteIdParam(info.index))
            .setLoadingState()
            .onEach { list ->
                _state.update { state ->
                    state.copy(list = list.map { it.toWordTest() })
                }
            }
            .catch {
                if (it is NetworkError) updateNetworkErrorState(true)
                else updateMessage("오류가 발생하였습니다.")

                it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }

    fun getNewHint(index: Int) {
        _state.update { state ->
            state.copy(
                list = state.list.mapIndexed { idx, wordTest ->
                    if (idx == index) wordTest.getNewHint() else wordTest
                }
            )
        }
    }

    fun setMyAnswer(value: String, index: Int) {
        _state.update { state ->
            state.copy(
                list = state.list.mapIndexed { idx, wordTest ->
                    if(idx == index) wordTest.updateMyAnswer(value) else wordTest
                }
            )
        }
    }

    fun submit() {
        val result = state.value.list.toWordTestResult()

        repository
            .insertWrongAnswer(result.wrongAnswerParams)
            .setLoadingState()
            .onEach {
                _state.update {
                    it.copy(
                        result = result,
                        isResultShow = true
                    )
                }
            }
            .catch {
                if(it is NetworkError) updateNetworkErrorState()
                else updateMessage(it.message ?: "??")
            }
            .launchIn(viewModelScope)
    }

    fun onResultDialogDismiss() {
        _state.update { it.copy(isResultShow = false) }
    }

}