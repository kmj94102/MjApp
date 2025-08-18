package com.example.mjapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.screen.calendar.PlanInfo
import com.example.mjapp.ui.screen.calendar.ScheduleInfoItem
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.BaseContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle20
import com.example.mjapp.util.textStyle20B
import com.example.network.model.CalendarItem
import com.example.network.model.CalendarItem.PlanInfo
import com.example.network.model.CalendarItem.ScheduleInfo
import com.example.network.model.PokemonCounter

@Composable
fun HomeScreen(
    navHostController: NavHostController? = null,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    BaseContainer(
        status = status,
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                HomeSchedule(
                    navHostController = navHostController,
                    list = viewModel.state.value.getScheduleList()
                )
            }
            item {
                HomePokemonCounter(
                    navHostController = navHostController,
                    list = viewModel.state.value.getPokemonCounterList()
                )
            }
        }
    }
}

@Composable
fun HomeSchedule(
    navHostController: NavHostController? = null,
    list: List<CalendarItem>
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = "4월 9일 화요일",
                style = textStyle20B(MyColorWhite)
            )
            Spacer(modifier = Modifier.weight(1f))

            DetailMoverButton(
                title = "일정 전체보기",
                onClick = {
                    navHostController?.navigate(NavScreen2.Schedule)
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            list.forEach {
                if (it is ScheduleInfo) {
                    ScheduleInfoItem(item = it)
                } else if (it is PlanInfo) {
                    PlanInfo(item = it)
                }
            }
        }
    }
}

@Composable
fun HomePokemonCounter(
    navHostController: NavHostController? = null,
    list: List<PokemonCounter>
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "포켓몬 카운트",
                style = textStyle20B(MyColorWhite),
                modifier = Modifier.padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            DetailMoverButton(
                title = "카운트 상세보기",
                onClick = {
                    navHostController?.navigate(NavScreen2.PokemonCounter)
                },
                modifier = Modifier.padding(end = 24.dp)
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp)
        ) {
            items(list) {
                HomePokemonCounterItem(
                    item = it,
                    onClick = { navHostController?.navigate(NavScreen2.PokemonCounter) }
                )
            }
        }
    }
}

@Composable
fun HomePokemonCounterItem(
    item: PokemonCounter,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(140.dp)
            .background(MyColorLightBlack, RoundedCornerShape(32.dp))
            .nonRippleClickable(onClick)
    ) {
        AsyncImage(
            item.image,
            placeholder = painterResource(id = R.drawable.img_egg),
            error = painterResource(id = R.drawable.img_egg),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(top = 20.dp)
                .size(90.dp)
        )
        Text(
            item.getCountFormat(),
            style = textStyle20(MyColorWhite, textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 25.dp, start = 10.dp, end = 10.dp)
        )
    }
}

@Composable
fun DetailMoverButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.nonRippleClickable(onClick)) {
        Text(title, style = textStyle12(MyColorGray))
        Icon(
            painter = painterResource(R.drawable.ic_next_small),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 2.dp)
                .size(12.dp)
        )
    }
}