package com.example.mjapp.ui.screen.game.pokemon.generation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.R
import com.example.mjapp.ui.custom.AsyncImageDoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.dialog.GenerationDetailDialog
import com.example.mjapp.ui.screen.game.pokemon.dex.PokemonListEmptyItem
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle16B
import com.example.network.model.GenerationDex
import com.example.network.model.getCatchStatus

@Composable
fun GenerationDetailScreen(
    onBackClick: () -> Unit,
    viewModel: GenerationDetailViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsState()
    HeaderBodyContainer(
        status = status,
        reload = viewModel::fetchGenerationList,
        headerContent = {
            GenerationDetailHeader(
                catchStatus = viewModel.state.value.list.getCatchStatus(),
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            GenerationDetailBody(
                list = viewModel.state.value.list,
                onClick = viewModel::setSelectPokemon
            )
        }
    )

    GenerationDetailDialog(
        isShow = viewModel.state.value.isDialogShow,
        number = viewModel.state.value.selectNumber,
        isCatch = viewModel.state.value.isCatch,
        updateCatch = { viewModel.updateCatch() },
        onDismiss = { viewModel.dismissDialog() },
    )
}

@Composable
fun GenerationDetailHeader(
    catchStatus: String,
    onBackClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        IconBox(
            boxColor = MyColorRed,
            onClick = onBackClick,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = catchStatus, style = textStyle16B())
    }
}

@Composable
fun GenerationDetailBody(
    list: List<GenerationDex>,
    onClick: (String, Int, Boolean) -> Unit
) {
    val state = rememberLazyGridState()
    if (list.isEmpty()) {
        PokemonListEmptyItem()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            state = state,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 15.dp, bottom = 50.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list.size) {
                GenerationItem(
                    info = list[it],
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun GenerationItem(
    info: GenerationDex,
    onClick: (String, Int, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .nonRippleClickable {
                onClick(info.number, info.idx, info.isCatch)
            }
    ) {
        AsyncImageDoubleCard(
            condition = true,
            trueImage = info.spotlight,
            falseImage = info.spotlight,
            placeholderRes = R.drawable.img_egg,
            size = DpSize(56.dp, 56.dp),
            innerPadding = PaddingValues(6.dp),
            saturation = if (info.isCatch) 1f else 0f,
            topCardColor = if (info.isCatch) MyColorWhite else MyColorLightGray,
            modifier = Modifier.size(71.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = info.name,
            style = textStyle12B().copy(fontSize = 10.sp)
        )
    }
}