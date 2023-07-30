package com.example.mjapp.ui.screen.game.elsword.introduce

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.mjapp.ui.screen.game.elsword.ElswordCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ElswordIntroduceViewModel @Inject constructor(

) : ViewModel() {

    private val characterList = ElswordCharacters.values()

    private val _selectCharacter = mutableIntStateOf(0)
    val selectCharacter: State<Int> = _selectCharacter

    val currentCharacter: ElswordCharacters
        get() = characterList[_selectCharacter.intValue]

    fun getNameList() = characterList.map { it.characterName }

    fun updateSelector(index: Int) {
        _selectCharacter.intValue = index
    }

    fun prevSelector() {
        val index = _selectCharacter.intValue
        if (index <= 0) {
            _selectCharacter.intValue = characterList.lastIndex
        } else {
            _selectCharacter.intValue = index - 1
        }
    }

    fun nextSelector() {
        val index = _selectCharacter.intValue
        if (index >= characterList.size - 1) {
            _selectCharacter.intValue = 0
        } else {
            _selectCharacter.intValue = index + 1
        }
    }

}