package com.example.mjapp.ui.screen.game.elsword.counter.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.R
import com.example.mjapp.ui.custom.*
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.isNumeric
import com.example.mjapp.util.textStyle24B
import com.example.mjapp.util.toast
import com.example.network.model.ElswordQuestSimple

@Composable
fun ElswordCounterAddScreen(
    onBackClick: () -> Unit,
    viewModel: ElswordCounterAddViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColorWhite)
    ) {
        IconBox(
            boxColor = MyColorRed,
            modifier = Modifier.padding(top = 22.dp, start = 20.dp)
        ) {
            onBackClick()
        }

        DoubleCard(
            bottomCardColor = MyColorBeige,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(top = 10.dp, bottom = 15.dp)
            ) {
                CommonTextField(
                    value = viewModel.name.value,
                    onTextChange = {
                        viewModel.updateName(it)
                    },
                    hint = "퀘스트 명",
                    unfocusedIndicatorColor = MyColorGray,
                    focusedIndicatorColor = MyColorBlack,
                    contentPadding = PaddingValues(5.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                CommonTextField(
                    value = "${viewModel.maxCount.value}",
                    onTextChange = {
                        if (isNumeric(it)) {
                            viewModel.updateMaxCount(it.toInt())
                        }
                    },
                    keyboardType = KeyboardType.Number,
                    hint = "퀘스트 개수",
                    unfocusedIndicatorColor = MyColorGray,
                    focusedIndicatorColor = MyColorBlack,
                    contentPadding = PaddingValues(5.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                CommonButton(
                    text = "추가",
                    backgroundColor = MyColorRed,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    viewModel.insertCounter()
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(top = 15.dp)
        ) {
            viewModel.list.forEach {
                item {
                    ElswordCounterItem(elswordCounter = it) { id ->
                        viewModel.deleteCounter(id)
                    }
                }
            }
        }
    }
    
    val context = LocalContext.current
    LaunchedEffect(viewModel.status.value) {
        if (viewModel.status.value.isNotEmpty()) {
            context.toast(viewModel.status.value)
        }
    }
}

@Composable
fun ElswordCounterItem(
    elswordCounter: ElswordQuestSimple,
    onDeleteClick: (Int) -> Unit
) {
    DoubleCard(
        bottomCardColor = MyColorRed,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            Text(
                text = elswordCounter.name,
                style = textStyle24B(),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            )
            IconBox(
                boxShape = CircleShape,
                boxColor = MyColorRed,
                iconRes = R.drawable.ic_close,
                iconSize = 21.dp
            ) {
                onDeleteClick(elswordCounter.id)
            }
        }

        CommonProgressBar(
            percent = elswordCounter.progress.toInt(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(bottom = 10.dp)
        )
    }
}