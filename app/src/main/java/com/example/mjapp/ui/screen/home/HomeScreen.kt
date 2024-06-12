package com.example.mjapp.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.OutlineText
import com.example.mjapp.ui.custom.WeekCalendar
import com.example.mjapp.ui.screen.calendar.CalendarPlanContainer
import com.example.mjapp.ui.screen.calendar.CalendarScheduleContainer
import com.example.mjapp.ui.screen.game.elsword.ElswordCharacters
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.getToday
import com.example.mjapp.util.items
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle18B
import com.example.mjapp.util.textStyle24B
import com.example.network.model.CalendarItem
import com.example.network.model.ElswordCounter
import com.example.network.model.PokemonCounter

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            Text(
                text = getToday("yyyy년 MM월"),
                style = textStyle24B().copy(color = MyColorPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
        },
        bodyContent = {
            HomeBody(
                viewModel = viewModel
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBody(
    viewModel: HomeViewModel
) {
    val list = viewModel.state.value.getElswordQuestList()
    val state = rememberPagerState { list.size }

    LazyColumn(
        contentPadding = PaddingValues(top = 22.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        item {
            WeekCalendar(
                selectDate = viewModel.state.value.getSelectCalendarItem().detailDate,
                today = viewModel.today,
                list = viewModel.state.value.list,
                onDateSelect = viewModel::updateSelectDate,
                modifier = Modifier.fillMaxWidth()
            )
        }

        items(
            items = viewModel.state.value.getSelectCalendarItem().itemList,
            emptyItemContent = { EmptySchedule() },
            itemContent = {
                when (it) {
                    is CalendarItem.PlanInfo -> {
                        CalendarPlanContainer(
                            info = it,
                            modifier = Modifier.fillMaxWidth(),
                            onTaskClick = { _, _ -> },
                            deleteListener = viewModel::deletePlanTasks
                        )
                    }

                    is CalendarItem.ScheduleInfo -> {
                        CalendarScheduleContainer(
                            info = it,
                            modifier = Modifier.fillMaxWidth(),
                            deleteListener = viewModel::deleteSchedule
                        )
                    }
                }
            }
        )

        item {
            viewModel.state.value.getPokemonCounter()?.let {
                PokemonHomeContainer(
                    item = it,
                    itemSelectInfo = viewModel.state.value.getPokemonCounterProgress(),
                    onPrevClick = { viewModel.updatePokemonSelectIndex(-1) },
                    onNextClick = { viewModel.updatePokemonSelectIndex(1) }
                )
            }
        }

        item {
            if (list.isEmpty()) {
                ElswordCounterEmptyContainer()
            } else {
                HorizontalPager(state = state) { index ->
                    ElswordCounterContainer(
                        elswordCounter = list[index]
                    ) {
                        viewModel.updateElswordCounter(index)
                    }
                }
            }
        }
    }
}

@Composable
fun EmptySchedule() {
    DoubleCard(
        bottomCardColor = MyColorPurple,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "등록 된 일정이 없습니다.",
            style = textStyle12().copy(
                color = MyColorGray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp)
        )
    }
}

@Composable
fun ElswordCounterEmptyContainer() {
    DoubleCard(
        topCardColor = Color(0xFFA72F38),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 25.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_elsword_counter_empty),
                contentDescription = null,
                modifier = Modifier
                    .size(75.dp, 85.dp)
            )
            Text(
                text = "목표로 할 케릭터를\n선택해 주세요",
                style = textStyle16B().copy(textAlign = TextAlign.Center, color = MyColorWhite),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ElswordCounterContainer(
    modifier: Modifier = Modifier,
    elswordCounter: ElswordCounter,
    onNextClick: () -> Unit
) {
    DoubleCard(
        topCardColor = ElswordCharacters.getCharacterColor(elswordCounter.characterGroup),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = elswordCounter.image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .align(Alignment.TopStart)
            )
            OutlineText(
                text = "${elswordCounter.progress}/${elswordCounter.max}",
                style = textStyle24B().copy(color = MyColorWhite),
                outlineColor = MyColorBlack,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
                    .clip(RoundedCornerShape(5.dp))
                    .background(MyColorWhite)
                    .nonRippleClickable { onNextClick() }
            ) {
                Text(
                    text = if (elswordCounter.max - 1 == elswordCounter.progress) "완료" else "다음",
                    style = textStyle16B(),
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 7.dp)
                )
            }
        }
    }
}

@Composable
fun PokemonHomeContainer(
    item: PokemonCounter,
    itemSelectInfo: String,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit
) {
    DoubleCard(
        bottomCardColor = MyColorPurple
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColorPurple)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = item.name,
                    style = textStyle16B().copy(fontSize = 18.sp),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = itemSelectInfo,
                    style = textStyle12(color = MyColorWhite).copy(fontSize = 14.sp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MyColorBlack)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                IconBox(
                    boxColor = MyColorRed,
                    boxShape = CircleShape,
                    iconRes = R.drawable.ic_back,
                    onClick = onPrevClick
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Row {
                        AsyncImage(
                            model = item.image,
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.img_egg),
                            modifier = Modifier.size(72.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        AsyncImage(
                            model = item.shinyImage,
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.img_egg),
                            modifier = Modifier.size(72.dp)
                        )
                    }
                    Text(text = "${item.count}", style = textStyle18B(color = MyColorRed))
                }
                IconBox(
                    boxColor = MyColorRed,
                    boxShape = CircleShape,
                    iconRes = R.drawable.ic_next,
                    onClick = onNextClick
                )
            }
        }
    }
}