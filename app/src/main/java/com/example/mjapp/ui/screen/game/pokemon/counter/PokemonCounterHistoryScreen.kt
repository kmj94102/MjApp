package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonButton
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle24B
import com.example.network.model.PokemonCounter

@Composable
fun PokemonCounterHistoryScreen(
    onBackClick: () -> Unit,
    viewModel: PokemonCounterHistoryViewModel = hiltViewModel()
) {
    val status = viewModel.status.collectAsStateWithLifecycle().value

    HeaderBodyContainer(
        status = status,
        headerContent = {
            IconBox(
                boxColor = MyColorRed,
                onClick = onBackClick
            )
        },
        bodyContent = {
            PokemonCounterHistoryBody(viewModel = viewModel)
        }
    )
}

@Composable
fun PokemonCounterHistoryBody(
    viewModel: PokemonCounterHistoryViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 15.dp, bottom = 50.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        viewModel.list.forEach {
            item {
                HistoryItem(
                    item = it,
                    onDelete = {
                        viewModel.deleteCounter(it.index)
                    },
                    onRestore = {
                        viewModel.updateRestore(it.index, it.number)
                    }
                )
            }
        }
    }
}

@Composable
fun HistoryItem(
    item: PokemonCounter,
    onDelete: () -> Unit,
    onRestore: () -> Unit
) {
    DoubleCard(
        bottomCardColor = MyColorRed
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_egg),
                modifier = Modifier.size(70.dp)
            )
            AsyncImage(
                model = item.shinyImage,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_egg),
                modifier = Modifier.size(70.dp)
            )
        }
        Text(
            text = item.count.toString(),
            style = textStyle24B().copy(color = MyColorRed, textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 5.dp)
        ) {
            CommonButton(
                text = "복구",
                backgroundColor = MyColorWhite,
                modifier = Modifier.weight(1f),
                onClick = onRestore
            )
            CommonButton(
                text = "삭제",
                backgroundColor = MyColorRed,
                modifier = Modifier.weight(1f),
                onClick = onDelete
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}