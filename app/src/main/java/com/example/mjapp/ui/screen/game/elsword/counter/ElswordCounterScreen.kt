package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.screen.game.elsword.ElswordCharacters
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle24B
import com.example.network.model.ElswordCharacter
import com.example.network.model.ElswordQuestDetail
import com.example.network.model.ElswordQuestUpdate

@Composable
fun ElswordCounterScreen(
    onBackClick: () -> Unit,
    goToAdd: () -> Unit,
    viewModel: ElswordCounterViewModel = hiltViewModel()
) {
    val selectName = remember { mutableStateOf("") }
    val isStatusChangeShow = remember { mutableStateOf(false) }
    val isQuestSelectShow = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColorWhite)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 22.dp)
                .padding(horizontal = 20.dp)
        ) {
            IconBox(
                boxColor = MyColorRed
            ) {
                onBackClick()
            }
            Spacer(modifier = Modifier.weight(1f))
            IconBox(
                boxColor = MyColorTurquoise,
                iconRes = R.drawable.ic_plus
            ) {
                goToAdd()
            }
        }
        if (viewModel.list.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.img_elsword_empty),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = "퀘스트를 등록해주세요",
                        style = textStyle12().copy(fontSize = 14.sp, color = MyColorGray),
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        } else {
            viewModel.list.getOrNull(viewModel.selectCounter.value)?.let {
                ElswordCounterContents(
                    detailInfo = it,
                    onSelect = { name ->
                        selectName.value = name
                        isStatusChangeShow.value = true
                    },
                    onListChange = {
                        if (viewModel.list.size >= 2) {
                            isQuestSelectShow.value = true
                        }
                    }
                )
            }
        }
    }

    QuestSelectDialog(
        list = viewModel.list.map { it.name },
        selectItem = viewModel.list.getOrNull(viewModel.selectCounter.value)?.name ?: "",
        isShow = isQuestSelectShow.value,
        onDismiss = {
            isQuestSelectShow.value = false
        },
        onSelect = {
            isQuestSelectShow.value = false
            viewModel.chaneSelector(it)
        }
    )

    QuestStatusChangeDialog(
        isShow = isStatusChangeShow.value,
        onDismiss = {
            isStatusChangeShow.value = false
        },
        onUpdate = {
            isStatusChangeShow.value = false
            viewModel.updateQuest(
                ElswordQuestUpdate(
                    id = viewModel.list.getOrNull(viewModel.selectCounter.value)?.id ?: 0,
                    name = selectName.value,
                    type = it
                )
            )
        }
    )
}

@Composable
fun ElswordCounterContents(
    detailInfo: ElswordQuestDetail,
    onListChange: () -> Unit,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            val (text, line) = createRefs()
            Text(
                text = detailInfo.name,
                style = textStyle24B(),
                modifier = Modifier
                    .constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .nonRippleClickable {
                        onListChange()
                    }
            )

            Box(
                modifier = Modifier
                    .background(MyColorRed)
                    .height(1.dp)
                    .constrainAs(line) {
                        top.linkTo(text.bottom, 2.dp)
                        start.linkTo(text.start)
                        end.linkTo(text.end)
                        width = Dimension.fillToConstraints
                    }
            )
        }

        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "진행도 : ${detailInfo.progress}%", style = textStyle12().copy(fontSize = 14.sp))

        LazyColumn(
            contentPadding = PaddingValues(top = 15.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            detailInfo.getCharactersWithGroup().forEach { (group, characters) ->
                item {
                    ElswordQuestItem(group, characters, onSelect)
                }
            }
        }
    }
}

@Composable
fun ElswordQuestItem(
    group: String,
    characters: List<ElswordCharacter>,
    onSelect: (String) -> Unit
) {
    val color = ElswordCharacters.getCharacterColor(group)

    DoubleCard(
        bottomCardColor = color,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            characters.forEachIndexed { index, character ->
                ElswordQuestImage(
                    character = character,
                    color = color,
                    modifier = Modifier
                        .height(145.dp)
                        .weight(1f)
                        .nonRippleClickable {
                            onSelect(character.name)
                        }
                )
                if (index < characters.size - 1) {
                    Box(
                        modifier = Modifier
                            .size(1.dp, 145.dp)
                            .background(MyColorBlack)
                    )
                }
            }
        }
    }
}

@Composable
fun ElswordQuestImage(
    character: ElswordCharacter,
    color: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = modifier) {
        AsyncImage(
            model = character.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToSaturation(if (character.isComplete) 1f else 0f)
                }
            ),
            modifier = Modifier.fillMaxSize()
        )
        if (character.isOngoing) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color.copy(alpha = alpha))
            ) {
                Text(
                    text = "진행중",
                    style = textStyle24B().copy(color = MyColorWhite.copy(alpha = alpha))
                )
            }
        }
    }
}