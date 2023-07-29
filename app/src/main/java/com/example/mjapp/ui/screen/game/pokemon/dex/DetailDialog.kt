package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.ConditionAsyncImage
import com.example.mjapp.ui.custom.ConditionImage
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.*
import com.example.network.model.EvolutionInfo
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.TypeInfo

/**
 * 포켓몬 상세 다이얼로그
 * @param number 포켓몬 번호
 * @param onDismiss 다이얼로그 종료 리스너
 * @param onSelectChange 다른 포켓몬 선택 리스너
 * **/
@Composable
fun DetailDialog(
    isShow: Boolean,
    number: String,
    onDismiss: () -> Unit,
    onCatchStateChange: (Boolean) -> Unit,
    onSelectChange: (String) -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    if (isShow.not()) return

    viewModel.fetchPokemonDetail(number)
    val info = viewModel.info.value
    val context = LocalContext.current
    val selectState = remember { mutableStateOf(1) }

    Dialog(
        onDismissRequest = {
            viewModel.statusReset()
            onDismiss()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(Color.White)
        ) {
            /** 상단 영역 **/
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 20.dp)
            ) {
                ConditionImage(
                    value = info?.pokemonInfo?.isCatch == true,
                    trueImageRes = R.drawable.img_monster_ballpng,
                    falseImageRes = R.drawable.img_monster_ball_empty,
                    modifier = Modifier
                        .size(28.dp)
                        .nonRippleClickable {
                            viewModel.updateCatch()
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                IconBox(
                    boxShape = CircleShape,
                    boxColor = MyColorRed,
                    iconSize = 21.dp,
                    iconRes = R.drawable.ic_close
                ) {
                    onDismiss()
                }
            }
            /** 바디 영역 **/
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF0E2F60), Color(0xFF1F789B))
                        )
                    )
            ) {
                when (viewModel.isLoading.value) {
                    true -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    false -> {
                        if (info == null) {
                            Text(
                                text = "정보를 가져오지 못했어요.",
                                style = textStyle16B().copy(color = MyColorWhite),
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterHorizontally)
                            )
                        } else {
                            PokemonDetailBody(
                                pokemonDetailInfo = info,
                                isShiny = viewModel.isShiny.value,
                                selectState = selectState.value,
                                onItemClick = {
                                    onSelectChange(it)
                                }
                            )
                        }
                    }
                }
            }

            /** 하단 영역 **/
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 20.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(if (selectState.value == 1) MyColorRed else MyColorLightGray)
                        .border(1.dp, MyColorBlack, CircleShape)
                        .nonRippleClickable {
                            selectState.value = 1
                        }
                ) {
                    Text(text = "1", style = textStyle16B())
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(if (selectState.value == 2) MyColorRed else MyColorLightGray)
                        .border(1.dp, MyColorBlack, CircleShape)
                        .nonRippleClickable {
                            selectState.value = 2
                        }
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
                    iconColor = if (viewModel.isShiny.value) MyColorRed else MyColorLightGray
                ) {
                    viewModel.toggleIsShiny()
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_stop_watch),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .nonRippleClickable {
                            viewModel.insertCounter()
                        }
                )
            }
        }
    }

    when (val status = viewModel.status.value) {
        is PokemonDetailViewModel.Status.Init -> {}
        is PokemonDetailViewModel.Status.Error -> {
            context.toast(status.msg)
        }
        is PokemonDetailViewModel.Status.InsertCounterSuccess -> {
            context.toast("카운터 등록이 완료되었습니다.")
        }
        is PokemonDetailViewModel.Status.CatchUpdateSuccess -> {
            onCatchStateChange(info?.pokemonInfo?.isCatch == true)
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
        1 -> {
            PokemonDescription(
                pokemonDetailInfo,
                isShiny,
                onItemClick
            )
        }
        2 -> {
            PokemonStatusAndEvolution(
                pokemonDetailInfo,
                isShiny
            )
        }
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
            Text(text = "약점", style = textStyle12().copy(color = MyColorWhite))
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
            style = textStyle12().copy(color = MyColorWhite),
            modifier = Modifier.padding(start = 20.dp, top = 5.dp)
        )
        Row(modifier = Modifier.padding(top = 5.dp, start = 20.dp)) {
            info.getTyeInfoList().forEach {
                PokemonTypeClip(typeInfo = it)
            }
        }
        Text(
            text = info.pokemonInfo.description,
            style = textStyle12().copy(fontSize = 18.sp, color = MyColorWhite),
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
                        .nonRippleClickable {
                            onItemClick(it.number)
                        }
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
                        .nonRippleClickable {
                            onItemClick(it.number)
                        }
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
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
                .padding(start = 4.dp)
                .size(20.dp)
        )
        Text(
            text = typeInfo.koreanName,
            style = textStyle12().copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
    }
}

@Composable
fun PokemonStatusAndEvolution(
    info: PokemonDetailInfo,
    isShiny: Boolean
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

        item {
            Spacer(modifier = Modifier.height(25.dp))
        }
        if (info.evolutionInfo.isEmpty()) {
            item {
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
        } else {
            item {
                info.evolutionInfo.forEach {
                    PokemonEvolutionContainer(
                        evolutionInfo = it,
                        isShiny = isShiny,
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
        style = textStyle12().copy(
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
    modifier: Modifier = Modifier
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