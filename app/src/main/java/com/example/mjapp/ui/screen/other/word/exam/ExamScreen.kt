package com.example.mjapp.ui.screen.other.word.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DividerLine
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.dialog.ExamResultDialog
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle18
import com.example.network.model.WordTest

@Composable
fun ExamScreen(
    onBackClick: () -> Unit,
    viewModel: ExamViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HighMediumLowContainer(
        status = status,
        heightContent = {
            ExamHeader(
                title = "",
                onBackClick = onBackClick
            )
        },
        mediumContent = {
            ExamBody(
                list = viewModel.state.value.list,
                updateHint = viewModel::getNewHint,
                updateMyAnswer = viewModel::setMyAnswer
            )
        },
        lowContent = {
            ExamFooter(onSubmit = viewModel::submit)
        }
    )

    ExamResultDialog(
        isShow = viewModel.state.value.isResultShow,
        data = viewModel.state.value.result,
        onDismiss = {
            viewModel.onResultDialogDismiss()
            onBackClick()
        },
        goToWrongAnswer = {}
    )
}

@Composable
fun ExamHeader(
    title: String,
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconBox(
            boxColor = MyColorBeige,
            onClick = onBackClick
        )

        Text(
            text = title,
            style = textStyle18(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ExamBody(
    list: List<WordTest>,
    updateHint: (Int) -> Unit,
    updateMyAnswer: (String, Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        list.forEachIndexed { index, testItem ->
            item {
                ExamItem(
                    item = testItem,
                    updateHint = { updateHint(index) },
                    updateMyAnswer = { updateMyAnswer(it, index) }
                )
            }
        }
    }
}

@Composable
fun ExamItem(
    item: WordTest,
    updateHint: () -> Unit,
    updateMyAnswer: (String) -> Unit
) {
    DoubleCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorBeige)
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = item.meaning,
                style = textStyle16(),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_reload),
                contentDescription = null,
                modifier = Modifier
                    .nonRippleClickable { updateHint() }
            )
        }
        DividerLine()

        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)) {
            Text(
                text = item.hint,
                style = textStyle14()
            )
            Spacer(modifier = Modifier.height(15.dp))

            CommonTextField(
                value = item.myAnswer,
                onTextChange = updateMyAnswer
            )
        }
    }
}

@Composable
fun ExamFooter(
    onSubmit: () -> Unit
) {
    DoubleCardButton(
        text = "제출하기",
        topCardColor = MyColorRed,
        onClick = onSubmit,
        modifier = Modifier.fillMaxWidth()
    )
}