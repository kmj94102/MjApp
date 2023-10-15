package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorRed

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OneSelectDialog(
    isShow: Boolean,
    title: String,
    list: List<String>,
    color: Color = MyColorRed,
    selectItem: String,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val state = rememberPagerState { list.size }

    ConfirmCancelDialog(
        isShow = isShow,
        title = title,
        onCancelClick = onDismiss,
        onConfirmClick = {
            onSelect(list[state.currentPage])
            onDismiss()
        },
        onDismiss = onDismiss,
        bodyContents = {
            Box(
                modifier = Modifier
                    .padding(vertical = 34.dp, horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                SelectSpinner(
                    selectList = list,
                    state = state,
                    initValue = selectItem,
                    width = 1500.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        },
        color = color
    )
}