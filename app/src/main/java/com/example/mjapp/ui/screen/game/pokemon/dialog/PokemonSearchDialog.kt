package com.example.mjapp.ui.screen.game.pokemon.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.screen.game.pokemon.PokemonCardImage
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle16B

@Composable
fun PokemonSearchDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String, String) -> Unit,
    viewModel: PokemonSearchViewModel = hiltViewModel()
) {
    if (isShow) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White)
            ) {
                IconBox(
                    boxShape = CircleShape,
                    boxColor = MyColorRed,
                    iconRes = R.drawable.ic_close,
                    iconSize = 21.dp,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp, end = 20.dp)
                        .align(Alignment.End)
                ) {
                    onDismiss()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                        .height(460.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF0E2F60), Color(0xFF1F789B))
                            )
                        )
                ) {
                    DoubleCardTextField(
                        value = viewModel.search.value,
                        onTextChange = {
                            viewModel.updateSearch(it)
                        },
                        bottomCardColor = MyColorRed,
                        hint = "포켓몬 이름 또는 번호로 검색",
                        imeAction = ImeAction.Search,
                        onSearch = {
                            viewModel.searchPokemonList()
                        },
                        tailIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp, horizontal = 20.dp)
                    )

                    if (viewModel.list.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_pokemon_empty_2),
                                    contentDescription = null,
                                    modifier = Modifier.size(211.dp, 197.dp)
                                )
                                Text(
                                    text = "데이터를 찾지 못했습니다.",
                                    style = textStyle12().copy(
                                        fontSize = 14.sp,
                                        color = MyColorGray
                                    )
                                )
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(
                                top = 10.dp,
                                bottom = 30.dp,
                                start = 20.dp,
                                end = 20.dp
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            viewModel.list.forEachIndexed { index, item ->
                                item {
                                    PokemonCardImage(
                                        number = item.number,
                                        image = item.spotlight,
                                        isSelect = item.number == viewModel.selectItem.value?.number,
                                        size = 100.dp,
                                        onClick = { _, _ ->
                                            viewModel.updateSelectItem(index)
                                        }
                                    )
                                }
                            }
                        }
                    }

                    viewModel.selectItem.value?.let {
                        DoubleCard(
                            topCardColor = MyColorRed,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 17.dp, bottom = 20.dp)
                                .nonRippleClickable {
                                    onSelect(it.number, it.spotlight)
                                    onDismiss()
                                }
                        ) {
                            Text(
                                text = "선택하기",
                                style = textStyle16B().copy(
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}