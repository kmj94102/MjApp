package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonButton
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.dialog.PokemonSearchDialog
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle24B
import com.example.network.model.PokemonCounter

@Composable
fun PokemonCounterScreen(
    onBackClick: () -> Unit,
    goToHistory: () -> Unit,
    viewModel: PokemonCounterViewModel = hiltViewModel()
) {
    var isCustomIncreaseSettingShow by remember { mutableStateOf(false) }
    var isPokemonSearchShow by remember { mutableStateOf(false) }
    val selectValue = remember { mutableStateOf(PokemonCounter.init()) }
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            PokemonCounterHeader(
                onBackClick = onBackClick,
                goToHistory = goToHistory
            )
        },
        bodyContent = {
            PokemonCounterBody(
                viewModel = viewModel,
                onSettingClick = {
                    selectValue.value = it
                    isCustomIncreaseSettingShow = true
                },
                onAddClick = {
                    isPokemonSearchShow = true
                }
            )
        }
    )

    CustomIncreaseSettingDialog(
        isShow = isCustomIncreaseSettingShow,
        selectValue = selectValue.value,
        onDismiss = {
            isCustomIncreaseSettingShow = false
        },
        onUpdateClick = { customIncrease, number ->
            viewModel.updateCustomIncrease(customIncrease, number)
        }
    )

    PokemonSearchDialog(
        isShow = isPokemonSearchShow,
        onDismiss = { isPokemonSearchShow = false },
        onSelect = { number, _ ->
            viewModel.insertPokemonCounter(number)
        }
    )
}

@Composable
fun PokemonCounterHeader(
    onBackClick: () -> Unit,
    goToHistory: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        IconBox(
            boxColor = MyColorRed,
            onClick = onBackClick,
        )
        Spacer(modifier = Modifier.weight(1f))
        IconBox(
            boxColor = MyColorTurquoise,
            iconRes = R.drawable.ic_history,
            onClick = goToHistory,
        )
    }
}

@Composable
fun PokemonCounterBody(
    viewModel: PokemonCounterViewModel,
    onSettingClick: (PokemonCounter) -> Unit,
    onAddClick: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 15.dp, bottom = 50.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        viewModel.list.forEach { pokemonCounter ->
            item {
                PokemonCounterCard(
                    counter = pokemonCounter,
                    updateCounter = { value ->
                        viewModel.updateCounter(value, pokemonCounter.number)
                    },
                    deleteCounter = {
                        viewModel.deleteCounter(pokemonCounter.index)
                    },
                    updateCatch = {
                        viewModel.updateCatch(pokemonCounter.number)
                    },
                    onSettingClick = {
                        onSettingClick(pokemonCounter)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            PokemonCounterEmptyCard(onClick = onAddClick)
        }
    }
}

@Composable
fun PokemonCounterCard(
    counter: PokemonCounter,
    updateCounter: (Int) -> Unit,
    deleteCounter: () -> Unit,
    updateCatch: () -> Unit,
    onSettingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DoubleCard(
        bottomCardColor = MyColorRed,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 5.dp)
            ) {
                IconBox(
                    boxColor = MyColorTurquoise,
                    boxShape = CircleShape,
                    boxSize = DpSize(24.dp, 24.dp),
                    iconSize = 18.dp,
                    iconRes = R.drawable.ic_setting,
                    onClick = onSettingClick
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconBox(
                    boxColor = MyColorRed,
                    boxShape = CircleShape,
                    boxSize = DpSize(24.dp, 24.dp),
                    iconSize = 16.dp,
                    iconRes = R.drawable.ic_close,
                    onClick = deleteCounter
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = counter.image,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(max = 70.dp)
                )
                AsyncImage(
                    model = counter.shinyImage,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(max = 70.dp)
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
            ) {
                CommonTextField(
                    value = "${counter.count}",
                    onTextChange = {},
                    readOnly = true,
                    keyboardType = KeyboardType.Number,
                    textStyle = textStyle24B().copy(color = MyColorRed, textAlign = TextAlign.End)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                CommonButton(
                    text = "- 1",
                    backgroundColor = MyColorWhite,
                    borderColor = MyColorRed,
                    modifier = Modifier.weight(1f)
                ) {
                    updateCounter(maxOf(0, counter.count - 1))
                }
                Spacer(modifier = Modifier.width(5.dp))
                CommonButton(
                    text = "- ${counter.customIncrease}",
                    backgroundColor = MyColorWhite,
                    borderColor = MyColorRed,
                    modifier = Modifier.weight(1f)
                ) {
                    updateCounter(maxOf(0, counter.count - counter.customIncrease))
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                CommonButton(
                    text = "+ 1",
                    backgroundColor = MyColorWhite,
                    borderColor = MyColorTurquoise,
                    modifier = Modifier.weight(1f)
                ) {
                    updateCounter(minOf(999_999, counter.count + 1))
                }
                Spacer(modifier = Modifier.width(5.dp))
                CommonButton(
                    text = "+ ${counter.customIncrease}",
                    backgroundColor = MyColorWhite,
                    borderColor = MyColorTurquoise,
                    modifier = Modifier.weight(1f)
                ) {
                    updateCounter(minOf(999_999, counter.count + counter.customIncrease))
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            CommonButton(
                text = "GET",
                backgroundColor = MyColorRed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                updateCatch()
            }
        }
    }
}

@Composable
fun PokemonCounterEmptyCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DoubleCard(
        topCardColor = MyColorRed,
        modifier = modifier
            .fillMaxWidth()
            .height(257.dp)
            .nonRippleClickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_egg),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "잡을 포켓몬을\n추가해주세요.",
                style = textStyle12().copy(
                    fontSize = 14.sp,
                    color = MyColorWhite,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            CommonButton(
                text = "추가",
                backgroundColor = MyColorWhite,
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick
            )
        }
    }
}