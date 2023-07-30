package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.UnderLineText
import com.example.mjapp.ui.screen.game.elsword.ElswordCharacters
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle24B
import com.example.network.model.ElswordCharacter
import com.example.network.model.ElswordQuestDetail

@Composable
fun ElswordCounterScreen(
    onBackClick: () -> Unit,
    goToAdd: () -> Unit,
    viewModel: ElswordCounterViewModel = hiltViewModel()
) {
    var isStatusChangeShow by remember { mutableStateOf(false) }
    var isQuestSelectShow by remember { mutableStateOf(false) }
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            ElswordCounterHeader(
                onBackClick = onBackClick,
                goToAdd = goToAdd
            )
        },
        bodyContent = {
            ElswordCounterBody(
                viewModel = viewModel,
                goToAdd = goToAdd,
                onStatusChangeClick = { isStatusChangeShow = true },
                onQuestClick = { isQuestSelectShow = true }
            )
        }
    )

    QuestSelectDialog(
        list = viewModel.list.map { it.name },
        selectItem = viewModel.list.getOrNull(viewModel.selectCounter.value)?.name ?: "",
        isShow = isQuestSelectShow,
        onDismiss = { isQuestSelectShow = false },
        onSelect = {
            isQuestSelectShow = false
            viewModel.chaneSelector(it)
        }
    )

    QuestStatusChangeDialog(
        item = viewModel.dialogItem.value,
        isShow = isStatusChangeShow,
        onDismiss = { isStatusChangeShow = false },
        onUpdate = { name, type, progress ->
            isStatusChangeShow = false
            viewModel.updateQuest(name, type, progress)
        }
    )
}

@Composable
fun ElswordCounterHeader(
    onBackClick: () -> Unit,
    goToAdd: () -> Unit
) {
    Row {
        IconBox(
            boxColor = MyColorRed,
            onClick = onBackClick
        )
        Spacer(modifier = Modifier.weight(1f))
        IconBox(
            boxColor = MyColorTurquoise,
            iconRes = R.drawable.ic_plus,
            onClick = goToAdd
        )
    }
}

@Composable
fun ElswordCounterBody(
    viewModel: ElswordCounterViewModel,
    goToAdd: () -> Unit,
    onStatusChangeClick: () -> Unit,
    onQuestClick: () -> Unit
) {
    viewModel.list.getOrNull(viewModel.selectCounter.value)?.let {
        ElswordCounterContents(
            detailInfo = it,
            onSelect = { name ->
                viewModel.setDialogItem(name)
                onStatusChangeClick()
            },
            onListChange = {
                if (viewModel.list.size >= 2) {
                    onQuestClick()
                }
            }
        )
    } ?: ElswordCounterEmpty(goToAdd)
}

@Composable
fun ElswordCounterEmpty(
    goToAdd: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1f)
            .nonRippleClickable(goToAdd)
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
}

@Composable
fun ElswordCounterContents(
    detailInfo: ElswordQuestDetail,
    onListChange: () -> Unit,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        UnderLineText(
            textValue = detailInfo.name,
            textStyle = textStyle24B(),
            underLineColor = MyColorRed,
            modifier = Modifier.nonRippleClickable(onListChange)
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "진행도 : ${detailInfo.getProgress()}%",
            style = textStyle12().copy(fontSize = 14.sp)
        )

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
    val imageAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    val textAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1.0f,
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
                    .background(color.copy(alpha = imageAlpha))
            ) {
                Text(
                    text = "진행중",
                    style = textStyle24B().copy(color = MyColorWhite.copy(alpha = textAlpha))
                )
            }
        }
    }
}