package com.example.mjapp.ui.screen.other.word.note

import android.icu.util.Calendar
import com.example.network.model.Note
import com.example.network.model.NoteParam
import java.util.Date

data class NoteState(
    val year: Int,
    val month: Int,
    val language: String,
    val noteList: List<Note>
) {
    companion object {
        fun init(today: Date): NoteState {
            val date = Calendar.getInstance()
            date.time = today

            return NoteState(
                year = date.get(Calendar.YEAR),
                month = date.get(Calendar.MONTH) + 1,
                language = "all",
                noteList = emptyList()
            )
        }
    }

    fun getDate() = "$year.${month.toString().padStart(2, '0')}"

    fun toNoteParam() = NoteParam(
        year = year,
        month = month,
        language = language
    )

    fun isLanguageSelect(value: String) = value == language

    fun getMonthString() = month.toString().padStart(2, '0')
}