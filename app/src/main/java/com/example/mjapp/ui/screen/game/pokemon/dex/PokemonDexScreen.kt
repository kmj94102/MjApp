package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.R
import com.example.mjapp.ui.custom.ConditionAsyncImage
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.screen.game.pokemon.search.PokemonNameSearchDialog
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorGray
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
    val isDetailDialogShow = remember { mutableStateOf(false) }
    val isSearchDialogShow = remember { mutableStateOf(false) }
    val state = rememberLazyGridState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 22.dp, start = 20.dp, end = 17.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconBox {
                onBackClick()
            }
            Text(
                text = viewModel.search.value,
                style = textStyle16B().copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 40.dp)
            )
            IconBox(
                iconRes = R.drawable.ic_shiny,
                iconColor = if (viewModel.isShiny.value) MyColorRed else MyColorGray,
                boxColor = MyColorWhite
            ) {
                viewModel.toggleShinyState()
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconBox(
                iconRes = R.drawable.ic_search,
                iconSize = 19.dp,
                boxColor = MyColorBeige
            ) {
                isSearchDialogShow.value = true
            }
            Spacer(modifier = Modifier.width(3.dp))
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            state = state,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 15.dp, bottom = 50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(viewModel.list.size) {
                PokemonItem(
                    info = viewModel.list[it],
                    isShiny = viewModel.isShiny.value,
                    onClick = { number ->
                        viewModel.updateSelectNumber(number)
                        isDetailDialogShow.value = true
                    }
                )
            }
        }
    }

    LaunchedEffect(state.firstVisibleItemIndex) {
        if (viewModel.isMoreDate.value) {
            viewModel.fetchMoreData(state.firstVisibleItemIndex)
        }
    }

    if (viewModel.isLoading.value) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    if (isDetailDialogShow.value) {
        DetailDialog(
            number = viewModel.selectNumber.value,
            onCatchStateChange = {
                viewModel.updateCatchState(it)
            },
            onDismiss = {
                isDetailDialogShow.value = false
            },
            onSelectChange = {
                viewModel.updateSelectNumber(it)
            }
        )
    }

    if (isSearchDialogShow.value) {
        PokemonNameSearchDialog(
            onDismiss = { isSearchDialogShow.value = false },
            onSearch = {
                viewModel.updateSearchValue(it)
                isSearchDialogShow.value = false
            }
        )
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
            .width(70.dp)
            .nonRippleClickable {
                onClick(info.number)
            }
    ) {
        DoubleCard(modifier = Modifier.fillMaxWidth()) {
            ConditionAsyncImage(
                value = isShiny,
                trueImage = info.shinySpotlight,
                falseImage = info.spotlight,
                placeholder = painterResource(id = R.drawable.img_egg),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.colorMatrix(
                    ColorMatrix().apply {
                        setToSaturation(if (info.isCatch) 1f else 0f)
                    }
                ),
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            )
        }
        Text(
            text = "No.${info.number}",
            style = textStyle12().copy(fontSize = 10.sp),
            modifier = Modifier.padding(top = 5.dp)
        )
        Text(
            text = info.name,
            style = textStyle12B().copy(fontSize = 10.sp)
        )
    }
}