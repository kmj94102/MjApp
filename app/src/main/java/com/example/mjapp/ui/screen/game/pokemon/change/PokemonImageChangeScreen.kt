package com.example.mjapp.ui.screen.game.pokemon.change

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.screen.game.pokemon.PokemonCardImage
import com.example.mjapp.ui.dialog.NumberSelectDialog
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.toast

@Composable
fun PokemonImageChangeScreen(
    onBackClick: () -> Unit,
    viewModel: PokemonImageChangeViewModel = hiltViewModel()
) {
    var isShow by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 22.dp, bottom = 15.dp, start = 20.dp, end = 17.dp)
    ) {
        IconBox(
            boxColor = MyColorRed
        ) {
            onBackClick()
        }
        Spacer(modifier = Modifier.height(15.dp))

        Card(
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = MyColorWhite
            ),
            border = BorderStroke(1.dp, MyColorRed),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MyColorRed)
                ) {
                    Text(
                        viewModel.item?.number ?: "0000",
                        style = textStyle16B().copy(fontSize = 22.sp),
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .align(Alignment.Center)
                            .nonRippleClickable {
                                isShow = true
                            }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(24.dp)
                            .align(Alignment.CenterStart)
                            .nonRippleClickable {
                                viewModel.prevItem()
                            }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(24.dp)
                            .align(Alignment.CenterEnd)
                            .nonRippleClickable {
                                viewModel.nextItem()
                            }
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                ) {
                    PokemonCardImage(
                        image = viewModel.item?.spotlight ?: "",
                        number = viewModel.item?.number ?: "",
                        size = 100.dp,
                        onClick = { _, _ -> }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(47.dp)
                    )

                    PokemonCardImage(
                        image = viewModel.getNewImage(),
                        number = viewModel.item?.number ?: "",
                        size = 100.dp,
                        onClick = { _, _ -> }
                    )
                }

                CommonTextField(
                    value = viewModel.state.value.number,
                    onTextChange = {
                        viewModel.updateNumber(it)
                    },
                    hint = "포켓몬 번호",
                    unfocusedIndicatorColor = MyColorGray,
                    focusedIndicatorColor = MyColorBlack,
                    contentPadding = PaddingValues(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )

                DoubleCard(
                    topCardColor = MyColorRed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .padding(top = 20.dp, bottom = 15.dp)
                        .nonRippleClickable {
                            viewModel.updateSpotlight()
                        }
                ) {
                    Text(
                        text = "변경하기",
                        style = textStyle16B(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    )
                }

            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }

    val context = LocalContext.current
    val status = viewModel.status.value
    LaunchedEffect(status) {
        if (status is PokemonImageChangeViewModel.Status.Result) {
            context.toast(status.msg)
            viewModel.updateSpotlight()
        }
    }

    NumberSelectDialog(
        isShow = isShow,
        onDismiss = { isShow = false },
        onUpdateClick = {
            viewModel.updateIndex(it)
        }
    )
}