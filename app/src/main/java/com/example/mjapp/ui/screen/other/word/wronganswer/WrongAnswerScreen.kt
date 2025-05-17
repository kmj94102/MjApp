package com.example.mjapp.ui.screen.other.word.wronganswer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.screen.other.word.detail.WordItem
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14B
import com.example.mjapp.util.textStyle16
import com.example.network.model.WrongAnswerItem

@Composable
fun WrongAnswerScreen(
    navHostController: NavHostController? = null,
    viewModel: WrongAnswerViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            WrongAnswerHeader(
                onBackClick = { navHostController?.popBackStack() },
                isTimestamp = viewModel.isTimestamp,
                updateSort = viewModel::updateSort
            )
        },
        bodyContent = {
            WrongAnswerBody(
                list = viewModel.state.value.list,
                fetchMoreData = viewModel::fetchMoreData
            )
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack)
    )
}

@Composable
fun WrongAnswerHeader(
    onBackClick: () -> Unit = {},
    isTimestamp: Boolean = true,
    updateSort: (Boolean) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 11.dp)
    ) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            CommonGnbBackButton(onBackClick)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Center)
                .background(MyColorLightBlack, RoundedCornerShape(40.dp))
                .padding(2.dp)
        ) {
            Text(
                "최신순",
                style = textStyle14B(color = MyColorWhite, textAlign = TextAlign.Center),
                modifier = Modifier
                    .nonRippleClickable { updateSort(true) }
                    .background(
                        if (isTimestamp) MyColorDarkBlue else Color.Transparent,
                        RoundedCornerShape(40.dp)
                    )
                    .padding(vertical = 12.dp)
                    .width(81.dp)
            )
            Text(
                "오답순",
                style = textStyle14B(color = MyColorWhite, textAlign = TextAlign.Center),
                modifier = Modifier
                    .nonRippleClickable { updateSort(false) }
                    .background(
                        if (isTimestamp) Color.Transparent else MyColorDarkBlue,
                        RoundedCornerShape(40.dp)
                    )
                    .padding(vertical = 12.dp)
                    .width(81.dp)
            )
        }

    }
}

@Composable
fun WrongAnswerBody(
    list: List<WrongAnswerItem> = emptyList(),
    fetchMoreData: (Int) -> Unit = {}
) {
    val state = rememberLazyListState()
    val index by remember { derivedStateOf { state.firstVisibleItemIndex } }

    LazyColumn(
        state = state,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 50.dp, start = 24.dp, end = 24.dp),
    ) {
        items(list) {
            WrongAnswerBodyItem(it)
        }
    }

    LaunchedEffect(index) {
        fetchMoreData(index)
    }
}

@Composable
fun WrongAnswerBodyItem(
    item: WrongAnswerItem
) {
    Box {
        WordItem(item.toWord())
        Text(
            "${item.count}회",
            style = textStyle16(color = MyColorDarkBlue),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 26.dp, end = 20.dp)
        )
    }
}