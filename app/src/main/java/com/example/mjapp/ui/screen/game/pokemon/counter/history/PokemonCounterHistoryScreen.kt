package com.example.mjapp.ui.screen.game.pokemon.counter.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.formatAmount
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.pokemonBackground
import com.example.mjapp.util.textStyle20
import com.example.mjapp.util.textStyle20B
import com.example.network.model.PokemonCounter

@Composable
fun PokemonCounterHistoryScreen(
    navHostController: NavHostController? = null,
    viewModel: PokemonCounterHistoryViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val countList by viewModel.list.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        paddingValues = PaddingValues(0.dp),
        headerContent = {
            CommonGnb(
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                },
                title = "카운터 히스토리"
            )
        },
        bodyContent = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 50.dp, start = 24.dp, end = 24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(countList.size) {
                    HistoryItem(
                        item = countList[it],
                        onDelete = { viewModel.deleteCounter(countList[it].index) },
                        onRestore = {
                            viewModel.updateRestore(countList[it].index, countList[it].number)
                        }
                    )
                }
            }
        },
        modifier = Modifier.background(pokemonBackground())
    )
}

@Composable
fun HistoryItem(
    item: PokemonCounter,
    onDelete: () -> Unit,
    onRestore: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MyColorWhite.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
    ) {
        Icon(
            painterResource(R.drawable.ic_close),
            contentDescription = null,
            tint = MyColorWhite,
            modifier = Modifier
                .padding(end = 8.dp, top = 8.dp)
                .size(24.dp)
                .align(Alignment.End)
                .nonRippleClickable(onDelete)
        )
        AsyncImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier.size(90.dp)
        )
        Text(item.count.formatAmount(), style = textStyle20B(color = MyColorWhite))

        Text(
            "복구하기",
            style = textStyle20(color = MyColorWhite, textAlign = TextAlign.Center),
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .background(
                    color = MyColorWhite.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
                .padding(vertical = 5.dp)
                .nonRippleClickable(onRestore)
        )
    }
}