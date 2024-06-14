package com.example.mjapp.ui.screen.other.english_words

import android.media.MediaPlayer
import com.example.network.model.Examination
import com.example.network.model.ExaminationScoringResult
import com.example.network.model.VocabularyListResult
import com.example.network.model.WrongAnswer

data class MemorizeState(
    val day: Int = 1,
    val list: List<VocabularyListResult> = listOf(),
)

data class WordPlayerController(
    val player: MediaPlayer = MediaPlayer(),
    val isReady: Boolean = false,
    val isPlaying: Boolean = false,
    val progress: String = "00:00 / 00:00",
    val currentPosition: Int = 0
)

data class ExamState(
    val day: Int = 1,
    val list: List<Examination> = listOf(),
    val result: ExaminationScoringResult = ExaminationScoringResult()
)

data class WrongAnswerState(
    val day: Int = 1,
    val list: List<WrongAnswer> = listOf()
)