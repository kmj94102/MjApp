package com.example.mjapp.ui.screen.game.elsword.introduce

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12B

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ElswordIntroduceScreen(
    onBackClick: () -> Unit,
    viewModel: ElswordIntroduceViewModel = hiltViewModel()
) {
    val list = viewModel.characterList

    Column(modifier = Modifier.fillMaxSize()) {
        IconBox(modifier = Modifier.padding(top = 22.dp, start = 20.dp)) {
            onBackClick()
        }
        Spacer(modifier = Modifier.height(15.dp))
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
                modifier = Modifier
                    .padding(start = 20.dp)
                    .align(Alignment.CenterStart)
            ) {
                viewModel.prevSelector()
            }
            IconBox(
                boxColor = MyColorRed,
                boxShape = CircleShape,
                iconRes = R.drawable.ic_next,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.CenterEnd)
            ) {
                viewModel.nextSelector()
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ElswordLineCard(
                    image = viewModel.currentCharacter.jobImage[0],
                    color = viewModel.currentCharacter.color,
                    modifier = Modifier.weight(1f)
                )
                ElswordLineCard(
                    image = viewModel.currentCharacter.jobImage[1],
                    color = viewModel.currentCharacter.color,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ElswordLineCard(
                    image = viewModel.currentCharacter.jobImage[2],
                    color = viewModel.currentCharacter.color,
                    modifier = Modifier.weight(1f)
                )
                ElswordLineCard(
                    image = viewModel.currentCharacter.jobImage[3],
                    color = viewModel.currentCharacter.color,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            list.forEachIndexed { index, character ->
                Text(
                    text = character.characterName,
                    style = textStyle12B().copy(
                        fontSize = 14.sp,
                        color = if (index == viewModel.selectCharacter.value) {
                            character.color
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
        Spacer(modifier = Modifier.height(22.dp))
    }
}

@Composable
fun ElswordLineCard(
    @DrawableRes
    image: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    DoubleCard(bottomCardColor = color, modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}