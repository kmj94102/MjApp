package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.R
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.util.textStyle12

@Composable
fun ElswordCounterScreen(
    onBackClick: () -> Unit,
    goToAdd: () -> Unit,
    viewModel: ElswordCounterViewModel = hiltViewModel()
) {
    val counterList = viewModel.counterList.collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MyColorWhite)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 22.dp)
                .padding(horizontal = 20.dp)
        ) {
            IconBox {
                onBackClick()
            }
            Spacer(modifier = Modifier.weight(1f))
            IconBox(
                boxColor = MyColorTurquoise,
                iconRes = R.drawable.ic_plus
            ) {
                goToAdd()
            }
        }
        if (counterList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
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
        } else {
            counterList.getOrNull(viewModel.selectCounter.value)?.let {

            }
        }
    }
}