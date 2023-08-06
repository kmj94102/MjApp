package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorRed
import com.example.network.model.PokemonEvolutionCondition

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EvolutionTypeDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelectItem: (String, String) -> Unit
) {
    val list = PokemonEvolutionCondition.getEvolutionList()
    val state = rememberPagerState { list.size }

    ConfirmCancelDialog(
        isShow = isShow,
        title = "진화 타입을 선택",
        onCancelClick = onDismiss,
        onConfirmClick = {
            list.getOrNull(state.currentPage)
                ?.let { onSelectItem(it.name, it.getInitEvolutionCondition()) }
                ?: onDismiss()
        },
        onDismiss = onDismiss,
        bodyContents = {
            Box(
                modifier = Modifier
                    .padding(vertical = 34.dp, horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                SelectSpinner(
                    selectList = list.map { it.name },
                    state = state,
                    initValue = list[0].name,
                    width = 1500.dp,
                )
            }
        },
        color = MyColorRed
    )
}