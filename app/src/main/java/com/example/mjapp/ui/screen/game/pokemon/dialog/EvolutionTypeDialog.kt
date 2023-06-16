package com.example.mjapp.ui.screen.game.pokemon.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.network.model.PokemonEvolutionCondition

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EvolutionTypeDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelectItem: (String, String) -> Unit
) {
    val list = PokemonEvolutionCondition.getEvolutionList()
    val state = rememberPagerState { list.size }

    if (isShow) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(MyColorWhite)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                ) {
                    Text(
                        text = "진화 타입을 선택",
                        style = textStyle16().copy(textAlign = TextAlign.Center),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 30.dp)
                    )
                    IconBox(
                        boxShape = CircleShape,
                        boxColor = MyColorRed,
                        iconSize = 21.dp,
                        iconRes = R.drawable.ic_close
                    ) {
                        onDismiss()
                    }
                }

                SelectSpinner(
                    selectList = list.map { it.name },
                    state = state,
                    initValue = list[0].name,
                    width = 1500.dp,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 35.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 17.dp, bottom = 20.dp)
                ) {
                    DoubleCard(
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable { onDismiss() }
                    ) {
                        Text(
                            text = "취소",
                            style = textStyle16B().copy(textAlign = TextAlign.Center),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    DoubleCard(
                        topCardColor = MyColorRed,
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable {
                                list
                                    .getOrNull(state.currentPage)
                                    ?.let {
                                        onSelectItem(it.name, it.getInitEvolutionCondition())
                                    } ?: onDismiss()
                            }
                    ) {
                        Text(
                            text = "확인",
                            style = textStyle16B().copy(textAlign = TextAlign.Center),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}