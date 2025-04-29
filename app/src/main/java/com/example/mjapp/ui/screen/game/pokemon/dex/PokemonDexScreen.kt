package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.pokemonBackground
import com.example.mjapp.util.textStyle14
import com.example.network.model.PokemonSummary

@Composable
fun PokemonDexScreen(
    navHostController: NavHostController? = null,
    viewModel: PokemonDexViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        modifier = Modifier.background(
            brush = pokemonBackground()
        ),
        paddingValues = PaddingValues(0.dp),
        headerContent = {
            CommonGnb(
                startButton = {
                    CommonGnbBackButton {
                        navHostController?.popBackStack()
                    }
                },
                endButton = {
                    Image(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier.nonRippleClickable {
                        }
                    )
                },
                title = "전국 도감"
            )
        },
        bodyContent = {
            PokemonDexBody(
                list = viewModel.state.value.list,
                fetchMoreData = viewModel::fetchMoreData,
                onClick = {
                    navHostController?.navigate(NavScreen2.PokemonDetail(it.number))
                }
            )
        }
    )
}

@Composable
fun PokemonDexBody(
    list: List<PokemonSummary>,
    fetchMoreData: (Int) -> Unit = {},
    onClick: (PokemonSummary) -> Unit = {}
) {
    val state = rememberLazyGridState()
    val index by remember { derivedStateOf { state.firstVisibleItemIndex } }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(bottom = 50.dp, start = 24.dp, end = 24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list.size) {
            PokemonDexItem(list[it], onClick)
        }
    }

    LaunchedEffect(index) {
        fetchMoreData(index)
    }
}

@Composable
fun PokemonDexItem(
    pokemon: PokemonSummary,
    onClick: (PokemonSummary) -> Unit = {}
) {
    val backgroundColor = if (pokemon.isCatch) MyColorWhite else Color(0xFF767676)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                backgroundColor.copy(alpha = 0.3f),
                RoundedCornerShape(8.dp)
            )
            .border(1.dp, MyColorWhite, RoundedCornerShape(8.dp))
            .padding(10.dp)
            .nonRippleClickable{ onClick(pokemon) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.spotlight)
                .crossfade(true)
                .build(),
            contentDescription = pokemon.name,
            placeholder = painterResource(R.drawable.img_egg),
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToSaturation(if (pokemon.isCatch) 1f else 0f)
                }
            ),
            modifier = Modifier.size(60.dp)
        )

        Text(
            pokemon.name,
            style = textStyle14(color = MyColorWhite),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}