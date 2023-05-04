package com.example.mjapp.ui.screen.game.pokemon.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DashLine
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.R
import com.example.mjapp.ui.dialog.GenerateSelectDialog
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.util.*
import com.example.network.model.PokemonInfo

@Composable
fun PokemonAddScreen(
    onBackClick: () -> Unit,
    viewModel: PokemonAddViewModel = hiltViewModel()
) {
    val isDialogShow = remember {
        mutableStateOf(false)
    }
    val pokemonInfo = viewModel.pokemonInfo.value

    Column(modifier = Modifier.fillMaxSize()) {
        IconBox(modifier = Modifier.padding(top = 22.dp, start = 20.dp)) {
            onBackClick()
        }

        Spacer(modifier = Modifier.height(10.dp))
        DoubleCard(
            bottomCardColor = MyColorPurple,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp)
        ) {
            CommonTextField(
                value = viewModel.pokemonIndex.value,
                onTextChange = { viewModel.updateValue(PokemonAddViewModel.Index, it) },
                hint = "포켓몬 번호를 입력해주세요",
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Search,
                onSearch = { viewModel.searchPokemon() },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        DashLine(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 20.dp, end = 20.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(top = 10.dp, bottom = 50.dp, start = 20.dp, end = 20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(pokemonInfo.image)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.img_egg),
                        contentDescription = null
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(pokemonInfo.shinyImage)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.img_egg),
                        contentDescription = null
                    )
                }
                DoubleCard(
                    bottomCardColor = MyColorBeige,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CommonTextField(
                        value = pokemonInfo.name,
                        onTextChange = {
                            viewModel.updateValue(
                                type = PokemonAddViewModel.Name,
                                value = it
                            )
                        },
                        hint = "이름",
                        imeAction = ImeAction.Done,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    DoubleCard(
                        bottomCardColor = MyColorBeige,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp)
                            .nonRippleClickable {
                                isDialogShow.value = true
                            }
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Text(
                                if (viewModel.generate.value.isEmpty()) {
                                    "세대를 선택해주세요."
                                } else {
                                    viewModel.generate.value.plus(" 세대")
                                },
                                style = textStyle12(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp)
                            )
                        }
                    }
                    DoubleCard(
                        bottomCardColor = MyColorBeige,
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        CommonTextField(
                            value = viewModel.dbIndex.value,
                            onTextChange = {
                                viewModel.updateValue(
                                    PokemonAddViewModel.DbIndex,
                                    it
                                )
                            },
                            hint = "데이터베이스 포켓몬 번호",
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Text(
                    text = "타입",
                    style = textStyle16(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                DoubleCard(
                    bottomCardColor = MyColorBeige,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CommonTextField(
                        value = pokemonInfo.attribute,
                        onTextChange = {
                            viewModel.updateValue(
                                type = PokemonAddViewModel.Attribute,
                                value = it
                            )
                        },
                        hint = "타입",
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            item {
                Text(
                    text = "특성",
                    style = textStyle16(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                DoubleCard(
                    bottomCardColor = MyColorBeige,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CommonTextField(
                        value = pokemonInfo.characteristic,
                        onTextChange = {
                            viewModel.updateValue(
                                type = PokemonAddViewModel.Characteristic,
                                value = it
                            )
                        },
                        hint = "특성",
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            item {
                Text(
                    text = "분류",
                    style = textStyle16(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                DoubleCard(
                    bottomCardColor = MyColorBeige,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CommonTextField(
                        value = pokemonInfo.classification,
                        onTextChange = {
                            viewModel.updateValue(
                                type = PokemonAddViewModel.Classification,
                                value = it
                            )
                        },
                        hint = "분류",
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            item {
                Text(
                    text = "설명",
                    style = textStyle16(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                DoubleCard(
                    bottomCardColor = MyColorBeige,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CommonTextField(
                        value = pokemonInfo.description,
                        onTextChange = {
                            viewModel.updateValue(
                                type = PokemonAddViewModel.Description,
                                value = it
                            )
                        },
                        hint = "설명",
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            item {
                Text(
                    text = "스테이터스",
                    style = textStyle16(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                DoubleCard(
                    bottomCardColor = MyColorBeige,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CommonTextField(
                        value = pokemonInfo.status,
                        onTextChange = {
                            viewModel.updateValue(
                                type = PokemonAddViewModel.Status,
                                value = it
                            )
                        },
                        hint = "스테이터스",
                        imeAction = ImeAction.Done,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

        }

        DoubleCard(
            topCardColor = MyColorPurple,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp, bottom = 20.dp)
                .nonRippleClickable {
                    viewModel.insertPokemon()
                }
        ) {
            Text(
                text = "포켓몬 등록하기",
                style = textStyle16B().copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        }
    }

    val context = LocalContext.current
    LaunchedEffect(viewModel.message.value) {
        if (viewModel.message.value.isNotEmpty()) {
            context.toast(viewModel.message.value)
        }
    }

    GenerateSelectDialog(
        isShow = isDialogShow,
        okClickListener = {
            isDialogShow.value = false
            viewModel.updateValue(PokemonAddViewModel.Generate, it)
        },
        cancelClickListener = {
            isDialogShow.value = false
        }
    )

    if (viewModel.isLoading.value) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }
}