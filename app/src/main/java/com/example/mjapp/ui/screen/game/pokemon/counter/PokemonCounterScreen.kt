package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.SelectChip
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.pokemonBackground
import com.example.mjapp.util.textStyle30B
import com.example.network.model.PokemonCounter

@Composable
fun PokemonCounterScreen(
    onBackClick: () -> Unit,
    goToHistory: () -> Unit,
    viewModel: PokemonCounterViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyBottomContainer(
        status = status,
        modifier = Modifier.background(brush = pokemonBackground()),
        paddingValues = PaddingValues(0.dp),
        heightContent = {
            CommonGnb(
                startButton = {
                    CommonGnbBackButton(onBackClick)
                },
                endButton = {
                    Row {
                        Image(
                            painterResource(R.drawable.ic_history),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(28.dp)
                                .nonRippleClickable(goToHistory)
                        )
                        Icon(
                            painterResource(R.drawable.ic_plus),
                            contentDescription = null,
                            tint = MyColorWhite,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                title = "포켓몬 카운터"
            )
        },
        bodyContent = {
            PokemonCounterBody(
                list = viewModel.state.value.list,
                selectIndex = viewModel.state.value.selectIndex,
                updateSelectIndex = viewModel::updateSelectIndex,
                updateCount = viewModel::updateCounter
            )
        },
        bottomContent = {
            PokemonCounterLow(
                onDelete = viewModel::deleteCounter,
                onGet = viewModel::updateCatch
            )
        }
    )
}

@Composable
fun PokemonCounterBody(
    list: List<PokemonCounter>,
    selectIndex: Int,
    updateSelectIndex: (Int) -> Unit,
    updateCount: (Int) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(list.size) {
            PokemonCounterListItem(
                pokemon = list[it],
                isSelect = it == selectIndex,
                onClick = { updateSelectIndex(it) }
            )
        }
    }

    if (list.isNotEmpty()) {
        list.getOrNull(selectIndex)?.let {
            PokemonCounterItem(
                pokemon = it,
                onUpdateClick = updateCount
            )
        } ?: updateSelectIndex(list.lastIndex)
    }
}

@Composable
fun PokemonCounterListItem(
    pokemon: PokemonCounter,
    isSelect: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(1.dp, MyColorWhite, RoundedCornerShape(10.dp))
            .background(
                if (isSelect) MyColorRed else MyColorWhite.copy(0.3f),
                RoundedCornerShape(10.dp)
            )
            .nonRippleClickable(onClick)
    ) {
        AsyncImage(
            model = pokemon.image,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.img_egg),
            modifier = Modifier.size(35.dp)
        )
    }
}

@Composable
fun PokemonCounterItem(
    pokemon: PokemonCounter,
    onUpdateClick: (Int) -> Unit ={}
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(modifier = Modifier.padding(top = 25.dp, bottom = 15.dp)) {
            AsyncImage(
                model = pokemon.image,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_egg),
                modifier = Modifier.weight(1f)
            )
            AsyncImage(
                model = pokemon.shinyImage,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_egg),
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            Text(
                "${pokemon.count}",
                style = textStyle30B(color = MyColorWhite, textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, MyColorWhite, RoundedCornerShape(8.dp))
                    .padding(vertical = 13.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 30.dp)
        ) {
            SelectChip(
                text = "- 1",
                unselectedColor = MyColorWhite,
                onClick = { onUpdateClick(-1) },
                modifier = Modifier.weight(1f)
            )
            SelectChip(
                text = "- 5",
                unselectedColor = MyColorWhite,
                onClick = { onUpdateClick(-5) },
                modifier = Modifier.weight(1f)
            )
            SelectChip(
                text = "- 1Box",
                unselectedColor = MyColorWhite,
                onClick = { onUpdateClick(-30) },
            )
            SelectChip(
                text = "- 5Box",
                onClick = { onUpdateClick(-30 * 5) },
                unselectedColor = MyColorWhite,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 15.dp)
        ) {
            SelectChip(
                text = "+ 1",
                unselectedColor = MyColorWhite,
                onClick = { onUpdateClick(1) },
                modifier = Modifier.weight(1f)
            )
            SelectChip(
                text = "+ 5",
                unselectedColor = MyColorWhite,
                onClick = { onUpdateClick(5) },
                modifier = Modifier.weight(1f)
            )
            SelectChip(
                text = "+ 1Box",
                unselectedColor = MyColorWhite,
                onClick = { onUpdateClick(30) },
            )
            SelectChip(
                text = "+5Box",
                unselectedColor = MyColorWhite,
                onClick = { onUpdateClick(30 * 5) },
            )
        }
    }
}

@Composable
fun PokemonCounterLow(
    onDelete: () -> Unit = {},
    onGet: () -> Unit = {}
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 24.dp)
    ) {
        TextButton(
            text = "삭제",
            backgroundColor = MyColorGray,
            onClick = onDelete,
            modifier = Modifier.weight(1f)
        )
        TextButton(
            text = "GET",
            onClick = onGet,
            modifier = Modifier.weight(1f)
        )
    }
}