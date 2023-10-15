package com.example.mjapp.ui.screen.other.english_words.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.dialog.ExamResultDialog
import com.example.mjapp.ui.screen.other.english_words.EnglishEmpty
import com.example.mjapp.ui.screen.other.english_words.EnglishWordsHeader
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.util.textStyle16B

@Composable
fun ExamScreen(
    onBackClick: () -> Unit,
    goToWrongAnswer: (Int) -> Unit,
    viewModel: ExamViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isShow by remember { mutableStateOf(false) }

    HighMediumLowContainer(
        status = status,
        heightContent = {
            EnglishWordsHeader(
                day = viewModel.day.intValue,
                onBackClick = onBackClick,
                onDaySelect = viewModel::updateDay
            )
        },
        mediumContent = {
            if (viewModel.list.isNotEmpty()) {
                ExamMedium(viewModel = viewModel)
            } else {
                EnglishEmpty("시험 문제를 준비 중입니다.")
            }
        },
        lowContent = {
            if (viewModel.list.isNotEmpty()) {
                DoubleCardButton(
                    text = "제출하기",
                    topCardColor = MyColorBlue,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    viewModel.examinationScoring {
                        isShow = true
                    }
                }
            }
        }
    )

    ExamResultDialog(
        isShow = isShow,
        data = viewModel.result.value,
        onDismiss = {
            isShow = false
            onBackClick()
        },
        goToWrongAnswer = {
            isShow = false
            goToWrongAnswer(viewModel.day.intValue)
        }
    )
}

@Composable
fun ExamMedium(
    viewModel: ExamViewModel
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 30.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.list.forEachIndexed { index, exam ->
            item {
                DoubleCard(
                    bottomCardColor = MyColorBeige,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = exam.word,
                            style = textStyle16B(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MyColorBeige)
                                .padding(horizontal = 15.dp, vertical = 5.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MyColorBlack)
                        )
                        CommonTextField(
                            value = exam.meaning,
                            onTextChange = {
                                viewModel.updateMeaning(index, it)
                            },
                            hint = "단어 뜻 입력",
                            imeAction = ImeAction.Next,
                            unfocusedIndicatorColor = MyColorGray,
                            focusedIndicatorColor = MyColorBlack,
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp, horizontal = 15.dp)
                        )
                    }
                }
            }
        }
    }
}