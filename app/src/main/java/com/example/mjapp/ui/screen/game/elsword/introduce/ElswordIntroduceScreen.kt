package com.example.mjapp.ui.screen.game.elsword.introduce

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.OutlineText
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12B

@Composable
fun ElswordIntroduceScreen(
    onBackClick: () -> Unit,
    viewModel: ElswordIntroduceViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HighMediumLowContainer(
        status = status,
        heightContent = {
            IconBox(
                boxColor = MyColorRed,
                onClick = onBackClick,
                modifier = Modifier.padding(bottom = 15.dp)
            )
        },
        mediumContent = {
            ElswordIntroduceMedium(viewModel = viewModel)
        },
        lowContent = {
            ElswordIntroduceLow(viewModel = viewModel)
        }
    )
}

@Composable
fun ElswordIntroduceMedium(
    viewModel: ElswordIntroduceViewModel
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            Image(
                painter = painterResource(id = viewModel.currentCharacter.sdImage),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center)
            )
            IconBox(
                boxColor = MyColorRed,
                boxShape = CircleShape,
                onClick = viewModel::prevSelector,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            IconBox(
                boxColor = MyColorRed,
                boxShape = CircleShape,
                iconRes = R.drawable.ic_next,
                onClick = viewModel::nextSelector,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                (0..3).forEach {
                    item {
                        ElswordLineCard(
                            image = viewModel.currentCharacter.jobImage[it],
                            name = viewModel.currentCharacter.jobName[it],
                            color = viewModel.currentCharacter.color,
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ElswordIntroduceLow(
    viewModel: ElswordIntroduceViewModel
) {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 3.dp, bottom = 12.dp)
    ) {
        viewModel.getNameList().forEachIndexed { index, name ->
            Text(
                text = name,
                style = textStyle12B().copy(
                    fontSize = 14.sp,
                    color = if (index == viewModel.selectCharacter.value) {
                        viewModel.currentCharacter.color
                    } else {
                        MyColorBlack
                    }
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .nonRippleClickable {
                        viewModel.updateSelector(index)
                    }
            )
        }
    }
}

@Composable
fun ElswordLineCard(
    @DrawableRes
    image: Int,
    name: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    DoubleCard(bottomCardColor = color, modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.align(Alignment.Center)
            )
            OutlineText(
                text = name,
                style = textStyle12B().copy(color = MyColorWhite),
                outlineColor = MyColorBlack,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 5.dp)
            )
        }
    }
}