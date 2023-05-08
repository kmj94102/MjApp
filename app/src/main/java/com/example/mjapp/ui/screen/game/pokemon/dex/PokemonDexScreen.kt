package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle12B
import com.example.network.model.PokemonSummary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonDexScreen(
    onBackClick: () -> Unit,
    viewModel: PokemonDexViewModel = hiltViewModel()
) {
    val isDetailDialogShow = remember {
        mutableStateOf(false)
    }

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
            Spacer(modifier = Modifier.weight(1f))
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

            }
            Spacer(modifier = Modifier.width(3.dp))
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 15.dp, bottom = 50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                FlowRow(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    viewModel.pokemonList.forEach {
                        PokemonItem(
                            info = it,
                            isShiny = viewModel.isShiny.value
                        ) { number ->
                            viewModel.updateSelectNumber(number)
                            isDetailDialogShow.value = true
                        }
                    }
                }
            }
        }
    }

    if (viewModel.isLoading.value) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    if (isDetailDialogShow.value) {
        DetailDialog(number = viewModel.selectNumber.value) {
            isDetailDialogShow.value = false
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
            .width(70.dp)
            .nonRippleClickable {
                onClick(info.number)
            }
    ) {
        DoubleCard(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(if (isShiny) info.shinySpotlight else info.spotlight)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.img_egg),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
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