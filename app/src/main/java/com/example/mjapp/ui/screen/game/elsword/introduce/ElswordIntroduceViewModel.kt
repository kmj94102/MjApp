package com.example.mjapp.ui.screen.game.elsword.introduce

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mjapp.ui.screen.game.elsword.ElswordCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ElswordIntroduceViewModel @Inject constructor(

) : ViewModel() {

    val characterList = ElswordCharacters.values()

    private val _selectCharacter = mutableStateOf(0)
    val selectCharacter: State<Int> = _selectCharacter

    val currentCharacter: ElswordCharacters
        get() = characterList[_selectCharacter.value]

    fun updateSelector(index: Int) {
        _selectCharacter.value = index
    }

    fun prevSelector() {
        val index = _selectCharacter.value
        if (index <= 0) {
            _selectCharacter.value = characterList.lastIndex
        } else {
            _selectCharacter.value = index - 1
        }
    }

    fun nextSelector() {
        val index = _selectCharacter.value
        if (index >= characterList.size - 1) {
            _selectCharacter.value = 0
        } else {
            _selectCharacter.value = index + 1
        }
    }

}