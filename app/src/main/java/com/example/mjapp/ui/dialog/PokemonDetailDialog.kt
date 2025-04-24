package com.example.mjapp.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.ConditionAsyncImage
import com.example.mjapp.ui.custom.ConditionImage
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.dialog.viewmodel.PokemonDetailViewModel
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle24B
import com.example.mjapp.util.toast
import com.example.network.model.EvolutionInfo
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.TypeInfo
import kotlinx.coroutines.delay

/**
 * 포켓몬 상세 다이얼로그
 * @param number 포켓몬 번호
 * @param onDismiss 다이얼로그 종료 리스너
 * @param onSelectChange 다른 포켓몬 선택 리스너
 * **/
@Composable
fun PokemonDetailDialog(
    isShow: Boolean,
    number: String,
    onDismiss: () -> Unit,
    onCatchStateChange: (Boolean) -> Unit,
    onSelectChange: (String) -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    if (isShow.not()) return
    val status by viewModel.status.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectState by remember { mutableIntStateOf(1) }

    PokemonDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        topIcon = {
            PokemonDetailTopIcon(viewModel = viewModel)
        },
        bodyContents = {
            val info = viewModel.info.value
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp)
            ) {
                when {
                    status.isLoading -> PokemonSearchLoading()
                    info == null -> PokemonDetailEmpty()
                    else -> PokemonDetailBody(
                        pokemonDetailInfo = info,
                        isShiny = viewModel.isShiny.value,
                        selectState = selectState,
                        onItemClick = onSelectChange
                    )
                }
            }
        },
        bottomContents = {
            PokemonDetailBottom(
                viewModel = viewModel,
                selectState = selectState,
                updateSelectState = { selectState = it }
            )
        }
    )

    LaunchedEffect(number) {
        viewModel.fetchPokemonDetail(number)
    }

    LaunchedEffect(status.message) {
        if (status.message.trim().isEmpty()) return@LaunchedEffect
        context.toast(status.message)
        delay(500)
        status.updateMessage("")
    }

    LaunchedEffect(status.isFinish) {
        if (status.isFinish.not()) return@LaunchedEffect
        onCatchStateChange(viewModel.info.value?.pokemonInfo?.isCatch == true)
        delay(500)
        status.updateFinish(false)
    }
}

@Composable
fun PokemonDetailTopIcon(
    viewModel: PokemonDetailViewModel
) {
    val isCatch = viewModel.info.value?.pokemonInfo?.isCatch == true
    ConditionImage(
        value = isCatch,
        trueImageRes = R.drawable.img_monster_ballpng,
        falseImageRes = R.drawable.img_monster_ball_empty,
        modifier = Modifier
            .size(30.dp)
            .nonRippleClickable(viewModel::updateCatch)
    )
}

@Composable
fun ColumnScope.PokemonDetailEmpty() {
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
            androidx.compose.material3.Text(
                text = "정보를 가져오지 못했어요.",
                style = textStyle14().copy(
                    fontSize = 14.sp,
                    color = MyColorGray
                )
            )
        }
    }
}

@Composable
fun PokemonDetailBody(
    pokemonDetailInfo: PokemonDetailInfo,
    isShiny: Boolean,
    onItemClick: (String) -> Unit,
    selectState: Int
) {
    when (selectState) {
        1 -> PokemonDescription(
            info = pokemonDetailInfo,
            isShiny = isShiny,
            onItemClick = onItemClick
        )
        2 -> PokemonStatusAndEvolution(
            info = pokemonDetailInfo,
            isShiny = isShiny,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun PokemonDescription(
    info: PokemonDetailInfo,
    isShiny: Boolean,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 17.dp, bottom = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "No.${info.pokemonInfo.number}",
                style = textStyle16().copy(fontSize = 18.sp, color = MyColorWhite)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "약점", style = textStyle14().copy(color = MyColorWhite))
            info.getWeekImageList().forEach {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(20.dp)
                )
            }
        }

        Text(
            text = info.pokemonInfo.name,
            style = textStyle24B().copy(color = MyColorWhite),
            modifier = Modifier.padding(start = 20.dp, top = 10.dp)
        )
        Text(
            text = info.getClassAndCharacter(),
            style = textStyle14().copy(color = MyColorWhite),
            modifier = Modifier.padding(start = 20.dp, top = 5.dp)
        )
        Row(modifier = Modifier.padding(top = 5.dp, start = 20.dp)) {
            info.getTyeInfoList().forEach {
                PokemonTypeClip(typeInfo = it)
            }
        }
        Text(
            text = info.pokemonInfo.description,
            style = textStyle14().copy(fontSize = 18.sp, color = MyColorWhite),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            val (beforeInfo, currentInfo, nextInfo, line1, line2) = createRefs()

            CirclePokemonImage(
                trueImage = info.pokemonInfo.shinyImage,
                falseImage = info.pokemonInfo.image,
                isShiny = isShiny,
                modifier = Modifier
                    .constrainAs(currentInfo) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            info.beforeInfo?.let {
                CirclePokemonImage(
                    boxColor = Color(0xFF1F769A),
                    boxSize = 72.dp,
                    trueImage = it.shinyImage,
                    falseImage = it.image,
                    imageSize = 60.dp,
                    isShiny = isShiny,
                    modifier = Modifier
                        .constrainAs(beforeInfo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start, (-36).dp)
                        }
                        .nonRippleClickable { onItemClick(it.number) }
                )
                Box(
                    modifier = Modifier
                        .constrainAs(line1) {
                            start.linkTo(beforeInfo.end)
                            end.linkTo(currentInfo.start)
                            top.linkTo(currentInfo.top)
                            bottom.linkTo(currentInfo.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.value(2.dp)
                        }
                        .background(MyColorWhite)
                )
            }

            info.nextInfo?.let {
                CirclePokemonImage(
                    boxColor = Color(0xFF1F769A),
                    boxSize = 72.dp,
                    trueImage = it.shinyImage,
                    falseImage = it.image,
                    imageSize = 60.dp,
                    isShiny = isShiny,
                    modifier = Modifier
                        .constrainAs(nextInfo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, (-36).dp)
                        }
                        .nonRippleClickable { onItemClick(it.number) }
                )
                Box(
                    modifier = Modifier
                        .constrainAs(line2) {
                            start.linkTo(currentInfo.end)
                            end.linkTo(nextInfo.start)
                            top.linkTo(currentInfo.top)
                            bottom.linkTo(currentInfo.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.value(2.dp)
                        }
                        .background(MyColorWhite)
                )
            }
        }
    }
}

@Composable
fun CirclePokemonImage(
    modifier: Modifier = Modifier,
    boxColor: Color = Color(0xFF0F3061),
    boxSize: Dp = 178.dp,
    trueImage: String,
    falseImage: String,
    imageSize: Dp = 160.dp,
    isShiny: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .background(boxColor)
            .border(2.dp, MyColorWhite, CircleShape)
            .size(boxSize)
    ) {
        ConditionAsyncImage(
            value = isShiny,
            trueImage = trueImage,
            falseImage = falseImage,
            placeholder = painterResource(id = R.drawable.img_egg),
            modifier = Modifier.size(imageSize)
        )
    }
}

@Composable
fun PokemonTypeClip(typeInfo: TypeInfo) {
    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .size(70.dp, 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(typeInfo.color))
    ) {
        Image(
            painter = painterResource(id = typeInfo.imageRes),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 4.dp)
                .size(20.dp)
        )
        Text(
            text = typeInfo.koreanName,
            style = textStyle14().copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 20.dp)
        )
    }
}

@Composable
fun PokemonStatusAndEvolution(
    info: PokemonDetailInfo,
    isShiny: Boolean,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        item {
            Text(
                text = "No.${info.pokemonInfo.number}",
                style = textStyle16().copy(fontSize = 18.sp, color = MyColorWhite),
                modifier = Modifier.padding(top = 17.dp)
            )
            Text(
                text = info.pokemonInfo.name,
                style = textStyle24B().copy(color = MyColorWhite),
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        val status = info.pokemonInfo.status.split(",")
        if (status.size == 6) {
            item {
                OutlinedCard(
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, MyColorWhite),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(27.dp)
                                .background(Color(0xFF0F3061))
                        ) {
                            PokemonStatusText("HP")
                            DividingLine()
                            PokemonStatusText("공격")
                            DividingLine()
                            PokemonStatusText("방어")
                            DividingLine()
                            PokemonStatusText("특공")
                            DividingLine()
                            PokemonStatusText("특방")
                            DividingLine()
                            PokemonStatusText("스피드")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(27.dp)
                        ) {
                            PokemonStatusText(status[0])
                            DividingLine()
                            PokemonStatusText(status[1])
                            DividingLine()
                            PokemonStatusText(status[2])
                            DividingLine()
                            PokemonStatusText(status[3])
                            DividingLine()
                            PokemonStatusText(status[4])
                            DividingLine()
                            PokemonStatusText(status[5])
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(25.dp)) }
        if (info.evolutionInfo.isEmpty()) {
            item {
                EvolutionEmpty()
            }
        } else {
            item {
                info.evolutionInfo.forEach {
                    PokemonEvolutionContainer(
                        evolutionInfo = it,
                        isShiny = isShiny,
                        onItemClick = onItemClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EvolutionEmpty() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_pokemon_empty_2),
            contentDescription = null,
            modifier = Modifier
                .size(211.dp, 197.dp)
                .padding(bottom = 10.dp)
        )
        Text(
            text = "진화 정보가 없습니다",
            style = textStyle16B().copy(color = MyColorLightGray)
        )
    }
}

@Composable
private fun DividingLine() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(MyColorWhite)
    )
}

@Composable
private fun RowScope.PokemonStatusText(text: String) {
    Text(
        text = text,
        style = textStyle14().copy(
            color = MyColorWhite,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier.weight(1f)
    )
}

@Composable
fun PokemonEvolutionContainer(
    evolutionInfo: EvolutionInfo,
    isShiny: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (before, after, evolutionIcon, condition, line) = createRefs()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .border(1.dp, MyColorWhite, CircleShape)
                .constrainAs(before) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .nonRippleClickable { onItemClick(evolutionInfo.beforeNumber) }
        ) {
            AsyncImage(
                model = if (isShiny) evolutionInfo.beforeShinyDot else evolutionInfo.beforeDot,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_egg),
                modifier = Modifier.size(72.dp)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .border(1.dp, MyColorWhite, CircleShape)
                .constrainAs(after) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .nonRippleClickable { onItemClick(evolutionInfo.afterNumber) }
        ) {
            AsyncImage(
                model = if (isShiny) evolutionInfo.afterShinyDot else evolutionInfo.afterDot,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_egg),
                modifier = Modifier.size(72.dp)
            )
        }

        Box(
            modifier = Modifier
                .height(1.dp)
                .background(MyColorWhite)
                .constrainAs(line) {
                    top.linkTo(before.top)
                    bottom.linkTo(before.bottom)
                    start.linkTo(before.end)
                    end.linkTo(after.start)
                    width = Dimension.fillToConstraints
                }
        )

        AsyncImage(
            model = evolutionInfo.evolutionImage,
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .constrainAs(evolutionIcon) {
                    bottom.linkTo(line.top, 5.dp)
                    start.linkTo(before.end)
                    end.linkTo(after.start)
                }
        )

        Text(
            text = evolutionInfo.evolutionCondition,
            style = textStyle16B().copy(color = MyColorWhite),
            modifier = Modifier.constrainAs(condition) {
                top.linkTo(line.bottom, 5.dp)
                start.linkTo(before.end, 5.dp)
                end.linkTo(after.start, 5.dp)
                width = Dimension.fillToConstraints
            }
        )

    }
}

@Composable
fun RowScope.PokemonDetailBottom(
    viewModel: PokemonDetailViewModel,
    selectState: Int,
    updateSelectState: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(if (selectState == 1) MyColorRed else MyColorLightGray)
            .border(1.dp, MyColorBlack, CircleShape)
            .nonRippleClickable { updateSelectState(1) }
    ) {
        Text(text = "1", style = textStyle16B())
    }
    Spacer(modifier = Modifier.width(10.dp))
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(if (selectState == 2) MyColorRed else MyColorLightGray)
            .border(1.dp, MyColorBlack, CircleShape)
            .nonRippleClickable { updateSelectState(2) }
    ) {
        Text(text = "2", style = textStyle16B())
    }
    Spacer(modifier = Modifier.width(10.dp))
    IconBox(
        boxColor = MyColorWhite,
        boxShape = CircleShape,
        boxSize = DpSize(28.dp, 28.dp),
        iconSize = 22.dp,
        iconRes = R.drawable.ic_shiny,
        iconColor = if (viewModel.isShiny.value) MyColorRed else MyColorLightGray,
        onClick = viewModel::toggleIsShiny
    )
    Spacer(modifier = Modifier.weight(1f))
    Icon(
        painter = painterResource(id = R.drawable.ic_stop_watch),
        contentDescription = null,
        modifier = Modifier
            .size(24.dp)
            .nonRippleClickable(viewModel::insertCounter)
    )
}