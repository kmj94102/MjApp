package com.example.mjapp.ui.screen.game.pokemon.add

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.screen.game.pokemon.PokemonCardImage
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B
import com.example.network.model.PokemonEvolutionItem
import com.example.mjapp.R
import com.example.mjapp.ui.screen.game.pokemon.dialog.EvolutionTypeDialog
import com.example.mjapp.ui.screen.game.pokemon.dialog.PokemonSearchDialog
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.toast

@Composable
fun PokemonAddScreen(
    onBackClick: () -> Unit,
    viewModel: PokemonAddViewModel = hiltViewModel()
) {
    val isBefore = remember { mutableStateOf(false) }
    val selectIndex = remember { mutableStateOf(0) }
    val isSearchDialogShow = remember { mutableStateOf(false) }
    val isEvolutionDialogShow = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        IconBox(
            boxColor = MyColorRed,
            modifier = Modifier.padding(top = 22.dp, start = 20.dp)
        ) {
            onBackClick()
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 15.dp, bottom = 50.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            viewModel.evolutionList.forEachIndexed { index, item ->
                item {
                    PokemonEvolutionContainer(
                        evolutionItem = item,
                        onRemove = {
                            viewModel.removeItem(index)
                        },
                        onImageClick = {
                            isBefore.value = it
                            selectIndex.value = index
                            isSearchDialogShow.value = true
                        },
                        onTypeClick = {
                            selectIndex.value = index
                            isEvolutionDialogShow.value = true
                        },
                        onConditionChange = {
                            viewModel.updateEvolutionCondition(index, it)
                        }
                    )
                }
            }

            item {
                IconBox(
                    boxShape = CircleShape,
                    boxColor = MyColorBeige,
                    iconSize = 24.dp,
                    iconRes = R.drawable.ic_plus,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    viewModel.addItem()
                }
            }
        }

        DoubleCard(
            topCardColor = MyColorRed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp, bottom = 10.dp)
                .nonRippleClickable {
                    viewModel.insertEvolution()
                }
        ) {
            Text(
                text = "등록하기",
                style = textStyle16B().copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        }
    }

    PokemonSearchDialog(
        isShow = isSearchDialogShow.value,
        onDismiss = {
            isSearchDialogShow.value = false
        },
        onSelect = { number, image ->
            viewModel.updateNumberInfo(
                index = selectIndex.value,
                isBeforeItem = isBefore.value,
                number = number,
                image = image
            )
        }
    )

    EvolutionTypeDialog(
        isShow = isEvolutionDialogShow.value,
        onDismiss = {
            isEvolutionDialogShow.value = false
        },
        onSelectItem = { type, condition ->
            viewModel.updateEvolutionType(index = selectIndex.value, evolutionType = type)
            viewModel.updateEvolutionCondition(
                index = selectIndex.value,
                evolutionCondition = condition
            )
            isEvolutionDialogShow.value = false
        }
    )

    val context = LocalContext.current
    when (val status =
        viewModel.status.collectAsState(initial = PokemonAddViewModel.Status.Init).value) {
        PokemonAddViewModel.Status.Success -> {
            context.toast("등록 완료")
            viewModel.clearItems()
            viewModel.updateInitStatus()
        }
        is PokemonAddViewModel.Status.Failure -> {
            context.toast(status.msg)
        }
        else -> {}
    }
}

@Composable
fun PokemonEvolutionContainer(
    evolutionItem: PokemonEvolutionItem,
    onRemove: () -> Unit,
    onImageClick: (Boolean) -> Unit,
    onTypeClick: () -> Unit,
    onConditionChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MyColorRed, RoundedCornerShape(15.dp))
            .padding(top = 10.dp, bottom = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        IconBox(
            boxShape = CircleShape,
            boxColor = MyColorRed,
            iconSize = 21.dp,
            iconRes = R.drawable.ic_close,
            modifier = Modifier.align(Alignment.End)
        ) {
            onRemove()
        }

        Row {
            PokemonCardImage(
                image = evolutionItem.beforeImage,
                number = evolutionItem.beforeNum,
                size = 100.dp,
                onClick = { _, _ ->
                    onImageClick(true)
                }
            )
            Spacer(modifier = Modifier.width(20.dp))
            PokemonCardImage(
                image = evolutionItem.afterImage,
                number = evolutionItem.afterNum,
                size = 100.dp,
                onClick = { _, _ ->
                    onImageClick(false)
                }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        DoubleCard(
            bottomCardColor = MyColorRed,
            modifier = Modifier
                .fillMaxWidth()
                .nonRippleClickable { onTypeClick() }
        ) {
            if (evolutionItem.evolutionType.isEmpty()) {
                Text(
                    text = "진화 타입",
                    style = textStyle16().copy(fontSize = 18.sp, color = MyColorLightGray),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                )
            } else {
                Text(
                    text = evolutionItem.evolutionType,
                    style = textStyle16().copy(fontSize = 18.sp),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        DoubleCardTextField(
            value = evolutionItem.evolutionCondition,
            onTextChange = {
                onConditionChange(it)
            },
            bottomCardColor = MyColorRed,
            singleLine = true,
            textStyle = textStyle16().copy(fontSize = 18.sp),
            hint = "진화 조건",
            modifier = Modifier.fillMaxWidth()
        )
    }
}