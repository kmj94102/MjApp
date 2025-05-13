package com.example.mjapp.ui.screen.other.word.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DividerLine
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.dialog.ExamResultDialog
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle18B
import com.example.network.model.WordTest

@Composable
fun ExamScreen(
    navHostController: NavHostController?= null,
    viewModel: ExamViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyBottomContainer(
        status = status,
        heightContent = {
            CommonGnb(
                title = viewModel.getTitle(),
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                }
            )
        },
        bodyContent = {
            ExamBody(
                list = viewModel.state.value.list,
                updateHint = viewModel::getNewHint,
                updateMyAnswer = viewModel::setMyAnswer
            )
        },
        bottomContent = {
            TextButton(
                text = "제출하기",
                textStyle = textStyle18B(color = MyColorWhite),
                borderColor = Color.Transparent,
                backgroundColor = MyColorDarkBlue,
                onClick = viewModel::submit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 17.dp, horizontal = 24.dp)
            )
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack)
    )

    ExamResultDialog(
        isShow = viewModel.state.value.isResultShow,
        data = viewModel.state.value.result,
        onDismiss = {
            viewModel.onResultDialogDismiss()
            navHostController?.popBackStack()
        },
        goToWrongAnswer = {}
    )
}

@Composable
fun ExamBody(
    list: List<WordTest>,
    updateHint: (Int) -> Unit,
    updateMyAnswer: (String, Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp, bottom = 30.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(list) { index, item ->
            ExamItem(
                item = item,
                updateHint = { updateHint(index) },
                updateMyAnswer = { updateMyAnswer(it, index) }
            )
        }
    }
}

@Composable
fun ExamItem(
    item: WordTest,
    updateHint: () -> Unit,
    updateMyAnswer: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyColorLightBlack, RoundedCornerShape(16.dp))
            .padding(top = 20.dp)
    ) {
        Text(
            item.meaning,
            style = textStyle16B(color = MyColorWhite),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(12.dp))

        Text(
            item.hint,
            style = textStyle14(MyColorWhite),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(5.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .nonRippleClickable(updateHint)
        ) {
            Text("다른 문장 보기", style = textStyle14(MyColorGray).copy(fontSize = 12.sp))
            Icon(
                painterResource(R.drawable.ic_reload),
                contentDescription = null,
                tint = MyColorGray,
                modifier = Modifier.padding(start = 2.dp).size(16.dp)
            )
        }
        Spacer(Modifier.height(20.dp))

        DividerLine(color = MyColorBlack.copy(alpha = 0.3f))

        CommonTextField(
            value = item.myAnswer,
            onTextChange = updateMyAnswer,
            hint = "답을 입력해 주세요",
            textStyle = textStyle14(color = MyColorWhite),
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
        )
    }
}