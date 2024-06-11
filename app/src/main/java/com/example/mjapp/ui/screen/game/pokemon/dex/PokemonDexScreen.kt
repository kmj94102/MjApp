package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.R
import com.example.mjapp.ui.custom.AsyncImageDoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.dialog.PokemonDetailDialog
import com.example.mjapp.ui.screen.game.pokemon.search.PokemonNameSearchDialog
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle16B
import com.example.network.model.PokemonSummary

@Composable
fun PokemonDexScreen(
    onBackClick: () -> Unit,
    viewModel: PokemonDexViewModel = hiltViewModel()
) {
    var uiState by remember { mutableStateOf(PokemonDexUiState()) }
    val status by viewModel.status.collectAsState()

    HeaderBodyContainer(
        status = status,
        reload = viewModel::fetchPokemonDex,
        onBackClick = onBackClick,
        headerContent = {
            PokemonDexHeader(
                onBackClick = onBackClick,
                viewModel = viewModel,
                onSearchClick = { uiState = uiState.copy(isSearchDialogShow = true) }
            )
        },
        bodyContent = {
            PokemonDexBody(
                viewModel = viewModel,
                onDetailClick = { uiState = uiState.copy(isDetailDialogShow = true) }
            )
        }
    )

    PokemonDetailDialog(
        isShow = uiState.isDetailDialogShow,
        number = viewModel.state.value.selectNumber,
        onCatchStateChange = { viewModel.updateCatchState(it) },
        onDismiss = { uiState = uiState.copy(isDetailDialogShow = false) },
        onSelectChange = { viewModel.updateSelectNumber(it) }
    )

    PokemonNameSearchDialog(
        isShow = uiState.isSearchDialogShow,
        onDismiss = { uiState = uiState.copy(isSearchDialogShow = false) },
        onSearch = {
            viewModel.updateSearchValue(it)
            uiState = uiState.copy(isSearchDialogShow = false)
        }
    )
}

@Composable
fun PokemonDexHeader(
    onBackClick: () -> Unit,
    viewModel: PokemonDexViewModel,
    onSearchClick: () -> Unit
) {
    val state = viewModel.state.value
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconBox(
            boxColor = MyColorRed,
            onClick = onBackClick
        )
        Text(
            text = state.search,
            style = textStyle16B().copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .weight(1f)
                .padding(start = 40.dp)
        )
        IconBox(
            iconRes = R.drawable.ic_shiny,
            iconColor = if (state.isShiny) MyColorRed else MyColorGray,
            boxColor = MyColorWhite,
            onClick = viewModel::toggleShinyState
        )
        Spacer(modifier = Modifier.width(10.dp))
        IconBox(
            iconRes = R.drawable.ic_search,
            iconSize = 19.dp,
            boxColor = MyColorBeige,
            onClick = onSearchClick
        )
        Spacer(modifier = Modifier.width(3.dp))
    }
}

@Composable
fun PokemonDexBody(
    viewModel: PokemonDexViewModel,
    onDetailClick: () -> Unit
) {
    val state = rememberLazyGridState()
    val index by remember { derivedStateOf { state.firstVisibleItemIndex } }
    val dataState = viewModel.state.value

    if (dataState.list.isEmpty()) {
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
            items(dataState.list.size) {
                PokemonItem(
                    info = dataState.list[it],
                    isShiny = dataState.isShiny,
                    onClick = { number ->
                        viewModel.updateSelectNumber(number)
                        onDetailClick()
                    }
                )
            }
        }
    }
    LaunchedEffect(index) {
        if (dataState.isMoreDate) {
            viewModel.fetchMoreData(index)
        }
    }
}

@Composable
fun PokemonListEmptyItem() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.img_pokemon_empty_1),
                contentDescription = null,
                modifier = Modifier
                    .size(211.dp, 197.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "검색 결과가 없습니다.",
                style = textStyle16B().copy(color = MyColorLightGray)
            )
        }
    }
}

@Composable
fun PokemonItem(
    info: PokemonSummary,
    isShiny: Boolean,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .nonRippleClickable { onClick(info.number) }
    ) {
        AsyncImageDoubleCard(
            condition = isShiny,
            trueImage = info.shinySpotlight,
            falseImage = info.spotlight,
            placeholderRes = R.drawable.img_egg,
            size = DpSize(56.dp, 56.dp),
            innerPadding = PaddingValues(6.dp),
            saturation = if (info.isCatch) 1f else 0f,
            topCardColor = if (info.isCatch) MyColorWhite else MyColorLightGray,
            modifier = Modifier.size(71.dp)
        )
        Text(
            text = info.getNumberFormat(),
            style = textStyle12().copy(fontSize = 10.sp),
            modifier = Modifier.padding(top = 5.dp)
        )
        Text(
            text = info.name,
            style = textStyle12B().copy(fontSize = 10.sp)
        )
    }
}