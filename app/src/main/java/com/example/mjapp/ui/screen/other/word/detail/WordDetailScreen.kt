package com.example.mjapp.ui.screen.other.word.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle30B
import com.example.network.model.Word

@Composable
fun WordDetailScreen(
    navHostController: NavHostController? = null,
    viewModel: WordDetailViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val list by viewModel.list.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        paddingValues = PaddingValues(0.dp),
        headerContent = {
            CommonGnb(
                title = viewModel.getTitle(),
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                }
            )
        },
        bodyContent = {
            WordDetailBody(list)
        },
        modifier = Modifier.background(MyColorBlack)
    )
}

@Composable
fun WordDetailBody(
    list: List<Word>
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp, bottom = 40.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(list) {
            WordItem(item = it)
        }
    }
}

@Composable
fun WordItem(item: Word) {
    Column(
        modifier = Modifier
            .background(MyColorLightBlack, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text(item.word, style = textStyle30B(MyColorWhite), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(7.dp))

        Text(item.meaning, style = textStyle16(MyColorWhite), modifier = Modifier.fillMaxWidth())

        if (item.note1.trim().isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(item.note1, style = textStyle14(MyColorGray), modifier = Modifier.fillMaxWidth())
        }

        if (item.note2.trim().isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(item.note1, style = textStyle14(MyColorGray), modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorDarkBlue.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                .border(1.dp, MyColorDarkBlue, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            item.examples.forEach {
                Text(
                    it.example,
                    style = textStyle14(MyColorGray),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    it.meaning,
                    style = textStyle14(MyColorGray),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}