package com.example.mjapp.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonLottieAnimation
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.dialog.viewmodel.PokemonSearchViewModel
import com.example.mjapp.ui.screen.game.pokemon.PokemonCardImage
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.textStyle14

@Composable
fun PokemonSearchDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String, String) -> Unit,
    viewModel: PokemonSearchViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    PokemonDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        bodyContents = {
            Column(modifier = Modifier.height(460.dp)) {
                DoubleCardTextField(
                    value = viewModel.search.value,
                    onTextChange = viewModel::updateSearch,
                    bottomCardColor = MyColorRed,
                    hint = "포켓몬 이름 또는 번호로 검색",
                    imeAction = ImeAction.Search,
                    onSearch = viewModel::searchPokemonList,
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

                when {
                    status.isNetworkError -> PokemonSearchEmpty("데이터 조회에 실패하였습니다.")
                    status.isLoading -> PokemonSearchLoading()
                    viewModel.list.isEmpty() -> PokemonSearchEmpty()
                    else -> PokemonSearchBody(viewModel)
                }

                viewModel.selectItem.value?.let {
                    DoubleCardButton(
                        text = "선택하기",
                        topCardColor = MyColorRed,
                        onClick = {
                            onSelect(it.number, it.spotlight)
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 17.dp)
                    )
                }
            }
        },
    )
}

@Composable
fun ColumnScope.PokemonSearchLoading() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        CommonLottieAnimation(
            resId = R.raw.lottie_loading,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ColumnScope.PokemonSearchEmpty(
    message: String = "데이터를 찾지 못했습니다."
) {
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
                text = message,
                style = textStyle14().copy(
                    fontSize = 14.sp,
                    color = MyColorGray
                )
            )
        }
    }
}

@Composable
fun ColumnScope.PokemonSearchBody(
    viewModel: PokemonSearchViewModel
) {
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