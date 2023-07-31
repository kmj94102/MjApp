package com.example.mjapp.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.OutlineText
import com.example.mjapp.ui.custom.WeekCalendar
import com.example.mjapp.ui.screen.calendar.CalendarPlanContainer
import com.example.mjapp.ui.screen.calendar.CalendarScheduleContainer
import com.example.mjapp.ui.screen.game.elsword.ElswordCharacters
import com.example.mjapp.ui.screen.game.pokemon.counter.CustomIncreaseSettingDialog
import com.example.mjapp.ui.screen.game.pokemon.counter.PokemonCounterCard
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.*
import com.example.network.model.CalendarItem
import com.example.network.model.ElswordCounter
import com.example.network.model.PokemonCounter

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    var isPokemonCounterSettingShow by remember { mutableStateOf(false) }
    var selectValue by remember { mutableStateOf(PokemonCounter.init()) }
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
                viewModel = viewModel,
                onSettingsClick = {
                    selectValue = it
                    isPokemonCounterSettingShow = true
                }
            )
        }
    )

    CustomIncreaseSettingDialog(
        isShow = isPokemonCounterSettingShow,
        selectValue = selectValue,
        onDismiss = {
            isPokemonCounterSettingShow = false
        },
        onUpdateClick = { customIncrease, number ->
            viewModel.updateCustomIncrease(customIncrease, number)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeBody(
    viewModel: HomeViewModel,
    onSettingsClick: (PokemonCounter) -> Unit
) {
    val state = rememberPagerState { viewModel.counterList.size }

    LazyColumn(
        contentPadding = PaddingValues(top = 22.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        item {
            WeekCalendar(
                selectDate = viewModel.selectItem.value.detailDate,
                today = viewModel.today,
                list = viewModel.list,
                onDateSelect = viewModel::updateSelectDate,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            val list = viewModel.selectItem.value.itemList
            if (list.isEmpty()) {
                EmptySchedule()
            } else {
                list.forEach {
                    when (it) {
                        is CalendarItem.PlanInfo -> {
                            CalendarPlanContainer(
                                info = it,
                                modifier = Modifier.fillMaxWidth(),
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
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                maxItemsInEachRow = 2,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                viewModel.pokemonList.forEach {
                    PokemonCounterCard(
                        counter = it,
                        updateCounter = { value -> viewModel.updateCounter(value, it.number) },
                        deleteCounter = { viewModel.deleteCounter(it.number) },
                        updateCatch = { viewModel.updateCatch(it.number) },
                        onSettingClick = { onSettingsClick(it) },
                        modifier = Modifier
                            .width(100.dp)
                            .weight(1f)
                    )
                }
                if (viewModel.pokemonList.size % 2 != 0) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .weight(1f)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))

            if (viewModel.counterList.isEmpty()) {
                ElswordCounterEmptyContainer()
            } else {
                HorizontalPager(state = state) { index ->
                    ElswordCounterContainer(
                        elswordCounter = viewModel.counterList[index]
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