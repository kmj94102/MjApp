package com.example.mjapp.ui.screen.game.pokemon.generation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle18B
import com.example.network.model.GenerationCount

@Composable
fun GenerationDexScreen(
    onBackClick: () -> Unit,
    goToDetail: (Int) -> Unit,
    viewModel: GenerationDexViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsState()

    HeaderBodyContainer(
        status = status,
        reload = viewModel::fetchGenerationCountList,
        headerContent = {
            GenerationHeader(onBackClick)
        },
        bodyContent = {
            GenerationBody(viewModel.list, goToDetail)
        },
    )
}

@Composable
fun GenerationHeader(
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconBox(
            boxColor = MyColorRed,
            onClick = onBackClick,
        )
        Text(
            text = "타이틀 도감",
            style = textStyle16B().copy(textAlign = TextAlign.Center),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun GenerationBody(
    list: List<GenerationCount>,
    goToDetail: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        items(list) {
            GenerationItem(item = it, onClick = goToDetail)
        }
    }
}

@Composable
fun GenerationItem(
    item: GenerationCount,
    onClick: (Int) -> Unit
) {
    DoubleCard(
        bottomCardColor = MyColorRed,
        modifier = Modifier
            .fillMaxWidth()
            .nonRippleClickable { onClick(item.generationIdx) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(64.dp)
                .padding(horizontal = 5.dp)
        ) {
            AsyncImage(
                model = item.getImage(),
                contentDescription = null,
                modifier = Modifier.size(57.dp, 57.dp)
            )

            Text(text = item.name, style = textStyle18B())
            Spacer(modifier = Modifier.weight(1f))

            Text(text = item.getCount(), style = textStyle18B())
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null
            )
        }
    }
}