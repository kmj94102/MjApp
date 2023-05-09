package com.example.mjapp.ui.screen.game.pokemon.dex

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.*
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.TypeInfo

@Composable
fun DetailDialog(
    number: String,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    viewModel.fetchPokemonDetail(number)
    val info = viewModel.info.value
    val context = LocalContext.current
    val state = remember {
        mutableStateOf(1)
    }

    Dialog(
        onDismissRequest = {
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
                Image(
                    painter = painterResource(
                        id = if (info?.pokemonInfo?.isCatch == true) {
                            R.drawable.img_monster_ballpng
                        } else {
                            R.drawable.img_monster_ball_empty
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .nonRippleClickable {

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
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    false -> {
                        if (info == null) {
                            Text(
                                text = "정보를 가져오지 못했어요.",
                                style = textStyle16B().copy(color = MyColorWhite),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        } else {
                            PokemonDetailBody(
                                pokemonDetailInfo = info,
                                selectState = state.value
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
                        .background(if (state.value == 1) MyColorRed else MyColorLightGray)
                        .border(1.dp, MyColorBlack, CircleShape)
                        .nonRippleClickable {
                            state.value = 1
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
                        .background(if (state.value == 2) MyColorRed else MyColorLightGray)
                        .border(1.dp, MyColorBlack, CircleShape)
                        .nonRippleClickable {
                            state.value = 2
                        }
                ) {
                    Text(text = "2", style = textStyle16B())
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

    when(val status = viewModel.status.value) {
        is PokemonDetailViewModel.Status.Init -> {}
        is PokemonDetailViewModel.Status.Error -> {
            context.toast(status.msg)
        }
        is PokemonDetailViewModel.Status.InsertCounterSuccess -> {
            context.toast("카운터 등록이 완료되었습니다.")
        }
    }
}

@Composable
fun PokemonDetailBody(
    pokemonDetailInfo: PokemonDetailInfo,
    selectState: Int
) {
    when (selectState) {
        1 -> {
            PokemonDescription(pokemonDetailInfo)
        }
        2 -> {
            PokemonStatusAndEvolution(pokemonDetailInfo)
        }
    }
}

@Composable
fun PokemonDescription(info: PokemonDetailInfo) {
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

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFF0F3061))
                    .border(2.dp, MyColorWhite, CircleShape)
                    .size(178.dp)
                    .constrainAs(currentInfo) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                AsyncImage(
                    model = info.pokemonInfo.image,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.img_egg),
                    modifier = Modifier.size(160.dp)
                )
            }

            info.beforeInfo?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF1F769A))
                        .border(2.dp, MyColorWhite, CircleShape)
                        .size(72.dp)
                        .constrainAs(beforeInfo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start, (-36).dp)
                        }
                ) {
                    AsyncImage(
                        model = info.beforeInfo?.image,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.img_egg),
                        modifier = Modifier.size(60.dp)
                    )
                }
                Box(modifier = Modifier
                    .background(MyColorWhite)
                    .constrainAs(line1) {
                        start.linkTo(beforeInfo.end)
                        end.linkTo(currentInfo.start)
                        top.linkTo(currentInfo.top)
                        bottom.linkTo(currentInfo.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.value(2.dp)
                    })
            }

            info.nextInfo?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF1F769A))
                        .border(2.dp, MyColorWhite, CircleShape)
                        .size(72.dp)
                        .constrainAs(nextInfo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, (-36).dp)
                        }
                ) {
                    AsyncImage(
                        model = info.nextInfo?.image,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.img_egg),
                        modifier = Modifier.size(60.dp)
                    )
                }
                Box(modifier = Modifier
                    .background(MyColorWhite)
                    .constrainAs(line2) {
                        start.linkTo(currentInfo.end)
                        end.linkTo(nextInfo.start)
                        top.linkTo(currentInfo.top)
                        bottom.linkTo(currentInfo.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.value(2.dp)
                    })
            }
        }
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
fun PokemonStatusAndEvolution(info: PokemonDetailInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
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
        val status = info.pokemonInfo.status.split(",")
        if (status.size == 6) {
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
                        Text(
                            text = "HP",
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = "공격",
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = "방어",
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = "특공",
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = "특방",
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = "스피드",
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(27.dp)
                    ) {
                        Text(
                            text = status[0],
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = status[1],
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = status[2],
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = status[3],
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = status[4],
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MyColorWhite)
                        )
                        Text(
                            text = status[5],
                            style = textStyle12().copy(
                                color = MyColorWhite,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

    }
}