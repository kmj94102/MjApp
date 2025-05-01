package com.example.mjapp.ui.screen.game.pokemon.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.SelectChip
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.pokemonBackground
import com.example.mjapp.util.textStyle20B
import com.example.network.model.GenerationInfo
import com.example.network.model.TypeInfo

@Composable
fun PokemonSearchScreen(
    navHostController: NavHostController? = null,
    viewModel: PokemonSearchViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HeaderBodyBottomContainer(
        status = status,
        modifier = Modifier.background(pokemonBackground()),
        paddingValues = PaddingValues(0.dp),
        heightContent = {
            CommonGnb(
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                },
                title = "포켓몬 검색"
            )
        },
        bodyContent = {
            PokemonSearchBody(
                state = state,
                onUpdateName = viewModel::updateName,
                onUpdateType = viewModel::updateType,
                onUpdateRegistrationStatus = viewModel::updateRegistrationStatus,
                onUpdateGeneration = viewModel::updateGeneration
            )
        },
        bottomContent = {
            PokemonSearchBottom(
                onClearClick = viewModel::clear,
                onSearchClick = {
                    val savedStateHandle = navHostController?.previousBackStackEntry
                        ?.savedStateHandle
                    savedStateHandle?.set("filter", viewModel.state.value.toPokemonSearchItem())
                    navHostController?.popBackStack()
                }
            )
        }
    )
}

@Composable
fun PokemonSearchBody(
    state: NavScreen2.PokemonSearch = NavScreen2.PokemonSearch(),
    onUpdateName: (String) -> Unit = {},
    onUpdateType: (String) -> Unit = {},
    onUpdateRegistrationStatus: (String) -> Unit = {},
    onUpdateGeneration: (String) -> Unit = {},
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        contentPadding = PaddingValues(bottom = 30.dp),
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        item {
            CommonTextField(
                value = state.name,
                onTextChange = onUpdateName,
                hint = "이름을 입력해 주세요",
                leadingIcon = {
                    Image(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(24.dp)
                    )
                },
                contentPadding = PaddingValues(vertical = 19.dp),
                modifier = Modifier
                    .background(MyColorWhite.copy(0.3f), RoundedCornerShape(30.dp))
                    .fillMaxWidth()
            )
        }

        item {
            RegistrationStatus(
                selectInfo = state.registrations,
                onSelect = onUpdateRegistrationStatus
            )
        }

        item {
            PokemonSearchTypeList(
                typeList = state.types,
                onUpdateType = onUpdateType
            )
        }

        item {
            PokemonSearchGenerationList(
                generationList = state.generations,
                onUpdateGeneration = onUpdateGeneration
            )
        }
    }
}

@Composable
fun PokemonSearchTypeList(
    typeList: List<String> = emptyList(),
    onUpdateType: (String) -> Unit = {}
) {
    Column {
        Text(
            "타입",
            style = textStyle20B(color = MyColorWhite),
            modifier = Modifier.padding(bottom = 15.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SelectChip(
                text = "전체",
                isSelected = typeList.isEmpty(),
                onClick = { onUpdateType(NavScreen2.PokemonSearch.ALL) }
            )
            TypeInfo.entries.dropLast(1).map { it.koreanName }.forEach {
                SelectChip(
                    text = it,
                    isSelected = typeList.contains(it),
                    onClick = { onUpdateType(it) },
                    modifier = Modifier.widthIn(min = 60.dp)
                )
            }
        }
    }
}

@Composable
fun PokemonSearchGenerationList(
    generationList: List<String> = emptyList(),
    onUpdateGeneration: (String) -> Unit = {}
) {
    Column {
        Text(
            "세대",
            style = textStyle20B(color = MyColorWhite),
            modifier = Modifier.padding(bottom = 15.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GenerationInfo.entries.forEach {
                SelectChip(
                    text = it.koreanName,
                    isSelected = if (it.generation == 0) {
                        generationList.isEmpty()
                    }else {
                        generationList.contains(it.generation.toString())
                    },
                    onClick = { onUpdateGeneration(it.generation.toString()) },
                    modifier = Modifier.widthIn(min = 60.dp)
                )
            }
        }
    }
}

@Composable
fun RegistrationStatus(
    selectInfo: String = NavScreen2.PokemonSearch.ALL,
    onSelect: (String) -> Unit = {}
) {
    Column {
        Text(
            "등록 상황",
            style = textStyle20B(color = MyColorWhite),
            modifier = Modifier.padding(bottom = 15.dp)
        )

        Row {
            SelectChip(
                text = "전체",
                isSelected = NavScreen2.PokemonSearch.ALL == selectInfo
            ) {
                onSelect(NavScreen2.PokemonSearch.ALL)
            }
            Spacer(modifier = Modifier.width(10.dp))

            SelectChip(
                text = "안 잡은 포켓몬 만",
                isSelected = NavScreen2.PokemonSearch.IS_NOT_CATCH == selectInfo
            ) {
                onSelect(NavScreen2.PokemonSearch.IS_NOT_CATCH)
            }
        }
    }
}

@Composable
fun PokemonSearchBottom(
    onClearClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 20.dp)) {
        TextButton(
            text = "초기화",
            backgroundColor = MyColorGray,
            onClick = onClearClick,
            modifier = Modifier.weight(1f)
        )
        TextButton(
            text = "검색",
            onClick = onSearchClick,
            modifier = Modifier.weight(1f)
        )
    }
}