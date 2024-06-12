package com.example.mjapp.ui.screen.game.elsword.counter.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonButton
import com.example.mjapp.ui.custom.CommonAnimatedProgressBar
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.textStyle24B
import com.example.network.model.ElswordQuestSimple

@Composable
fun ElswordCounterAddScreen(
    onBackClick: () -> Unit,
    viewModel: ElswordCounterAddViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            IconBox(
                boxColor = MyColorRed,
                onClick = onBackClick
            )
        },
        bodyContent = {
            ElswordCounterAddBody(viewModel)
        }
    )
}

@Composable
fun ElswordCounterAddBody(
    viewModel: ElswordCounterAddViewModel
) {
    ElswordCounterAddCard(viewModel)

    LazyColumn(
        contentPadding = PaddingValues(bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(top = 15.dp)
    ) {
        viewModel.state.value.list.forEach {
            item {
                ElswordCounterItem(
                    elswordCounter = it,
                    onDeleteClick = viewModel::deleteCounter
                )
            }
        }
    }
}

@Composable
fun ElswordCounterAddCard(
    viewModel: ElswordCounterAddViewModel
) {
    val item = viewModel.state.value
    DoubleCard(
        bottomCardColor = MyColorRed,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 10.dp, bottom = 15.dp)
        ) {
            CommonTextField(
                value = item.name,
                onTextChange = viewModel::updateName,
                hint = "퀘스트 명",
                unfocusedIndicatorColor = MyColorGray,
                focusedIndicatorColor = MyColorBlack,
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.fillMaxWidth()
            )
            CommonTextField(
                value = "${item.maxCount}",
                onTextChange = viewModel::updateMaxCount,
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
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::insertCounter
            )
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
                iconSize = 21.dp,
                onClick = { onDeleteClick(elswordCounter.id) }
            )
        }

        CommonAnimatedProgressBar(
            percent = elswordCounter.progress.toInt(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(bottom = 10.dp)
        )
    }
}