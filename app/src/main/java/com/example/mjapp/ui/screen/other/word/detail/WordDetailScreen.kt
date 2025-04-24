package com.example.mjapp.ui.screen.other.word.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.ui.custom.DashLine
import com.example.mjapp.ui.custom.DividerLine
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle18
import com.example.mjapp.util.textStyle18B
import com.example.network.model.Word

@Composable
fun WordDetailScreen(
    onBackClick: () -> Unit,
    viewModel: WordDetailViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            WordDetailHeader(
                title = viewModel.state.value.title,
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            WordDetailBody(viewModel.state.value.list)
        }
    )
}

@Composable
fun WordDetailHeader(
    title: String,
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconBox(
            boxColor = MyColorBeige,
            onClick = onBackClick
        )

        Text(text = title, style = textStyle18(), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun WordDetailBody(
    list: List<Word>
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
    ) {
        items(list) {
            WordItem(item = it)
        }
    }
}

@Composable
fun WordItem(item: Word) {
    DoubleCard(
        bottomCardColor = MyColorBeige,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = item.word,
                style = textStyle18B(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColorBeige)
                    .padding(vertical = 5.dp, horizontal = 15.dp)
            )
            DividerLine()

            Text(
                text = item.meaning,
                style = textStyle16(),
                modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp)
            )

            if(item.note1.trim().isNotEmpty()) {
                Text(
                    text = item.note1,
                    style = textStyle14(color = MyColorGray),
                    modifier = Modifier.padding(top = 14.dp, start = 15.dp, end = 15.dp)
                )
            }

            if(item.note2.trim().isNotEmpty()) {
                Text(
                    text = item.note2,
                    style = textStyle14(color = MyColorGray),
                    modifier = Modifier.padding(top = 14.dp, start = 15.dp, end = 15.dp)
                )
            }

            DashLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .height(1.dp)
            )

            item.examples.forEach {
                Text(
                    text = it.example,
                    style = textStyle14(color = MyColorGray),
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                )
                Text(
                    text = it.meaning,
                    style = textStyle14(color = MyColorGray),
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                )
            }
        }
    }
}