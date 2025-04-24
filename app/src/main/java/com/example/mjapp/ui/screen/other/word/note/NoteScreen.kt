package com.example.mjapp.ui.screen.other.word.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonButton
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.UnderLineText
import com.example.mjapp.ui.dialog.YearMonthSelectDialog
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorPurpleDark
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.ui.theme.MyColorTurquoiseDark
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle18
import com.example.mjapp.util.textStyle18B
import com.example.network.model.Note

@Composable
fun NoteScreen(
    onBackClick: () -> Unit,
    goToWordDetail: (Int, String) -> Unit,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isYearMonthDialogShow by remember { mutableStateOf(false) }

    HeaderBodyContainer(
        status = status,
        headerContent = {
            NoteHeader(onBackClick)
        },
        bodyContent = {
            NoteContent(
                viewModel = viewModel,
                goToWordDetail = goToWordDetail,
                showDialog = { isYearMonthDialogShow = true }
            )
        }
    )

    YearMonthSelectDialog(
        year = viewModel.state.value.year.toString(),
        month = viewModel.state.value.getMonthString(),
        isShow = isYearMonthDialogShow,
        onDismiss = { isYearMonthDialogShow = false },
        onSelect = viewModel::updateSelectMonth
    )
}

@Composable
fun NoteHeader(onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconBox(
            boxColor = MyColorBeige,
            onClick = onBackClick
        )

        Text(text = "단어 암기", style = textStyle18(), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun NoteContent(
    viewModel: NoteViewModel,
    showDialog: () -> Unit,
    goToWordDetail: (Int, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 15.dp)
        ) {
            UnderLineText(
                textValue = viewModel.state.value.getDate(),
                textStyle = textStyle18(),
                underLineColor = MyColorBeige,
                modifier = Modifier
                    .nonRippleClickable { showDialog() }
            )
            Spacer(modifier = Modifier.weight(1f))

            CommonButton(
                text = "전체",
                textStyle = textStyle16B().copy(fontSize = 14.sp),
                backgroundColor = if (viewModel.state.value.isLanguageSelect("all")) {
                    MyColorBeige
                } else {
                    MyColorWhite
                },
                innerPadding = PaddingValues(7.dp),
                onClick = {
                    viewModel.updateSelectLanguage("all")
                }
            )
            Spacer(modifier = Modifier.width(5.dp))

            CommonButton(
                text = "영어",
                textStyle = textStyle16B().copy(fontSize = 14.sp),
                backgroundColor = if (viewModel.state.value.isLanguageSelect("us")) {
                    MyColorBeige
                } else {
                    MyColorWhite
                },
                innerPadding = PaddingValues(7.dp),
                onClick = {
                    viewModel.updateSelectLanguage("us")
                }
            )
            Spacer(modifier = Modifier.width(5.dp))

            CommonButton(
                text = "일어",
                textStyle = textStyle16B().copy(fontSize = 14.sp),
                backgroundColor = if (viewModel.state.value.isLanguageSelect("jp")) {
                    MyColorBeige
                } else {
                    MyColorWhite
                },
                innerPadding = PaddingValues(7.dp),
                onClick = {
                    viewModel.updateSelectLanguage("jp")
                }
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.state.value.noteList.forEach {
                item { NoteItem(it, goToWordDetail) }
            }
        }
    }
}

@Composable
fun NoteItem(
    item: Note,
    goToWordDetail: (Int, String) -> Unit
) {
    DoubleCard(
        bottomCardColor = if (item.language == "us") {
            MyColorPurple
        } else {
            MyColorTurquoise
        },
        modifier = Modifier
            .fillMaxWidth()
            .nonRippleClickable { goToWordDetail(item.noteId, item.title) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 5.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, style = textStyle18B())
                Row {
                    Box(
                        modifier = Modifier
                            .background(
                                if (item.language == "us") {
                                    MyColorPurple
                                } else {
                                    MyColorTurquoise
                                }
                            )
                            .clip(shape = RoundedCornerShape(4.dp))
                            .padding(vertical = 2.dp, horizontal = 7.dp)
                    ) {
                        Text(
                            text = item.getLanguageKr(),
                            style = textStyle14(
                                color = if (item.language == "us") {
                                    MyColorPurpleDark
                                } else {
                                    MyColorTurquoiseDark
                                }
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(text = item.timestamp, style = textStyle14())
                }
            }
            Spacer(modifier = Modifier.height(2.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null
            )
        }
    }
}