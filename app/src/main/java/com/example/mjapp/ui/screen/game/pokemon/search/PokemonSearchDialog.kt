package com.example.mjapp.ui.screen.game.pokemon.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B

@Composable
fun PokemonNameSearchDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSearch: (String) -> Unit
) {
    if (isShow.not()) return

    val searchValue = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(MyColorWhite)
        ) {
            IconBox(
                boxShape = CircleShape,
                boxColor = MyColorRed,
                iconRes = R.drawable.ic_close,
                iconSize = 21.dp,
                onClick = onDismiss,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .padding(end = 20.dp)
                    .align(Alignment.End)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF0E2F60), Color(0xFF1F789B))
                        )
                    )
            ) {
                DoubleCardTextField(
                    value = searchValue.value,
                    onTextChange = { searchValue.value = it },
                    hint = "포켓몬 이름",
                    imeAction = ImeAction.Search,
                    onSearch = onSearch,
                    bottomCardColor = MyColorRed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp)
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(25.dp))
                DoubleCard(
                    topCardColor = MyColorRed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 28.dp)
                        .padding(horizontal = 20.dp)
                        .nonRippleClickable {
                            onSearch(searchValue.value)
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(vertical = 9.dp)
                                .size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "검색하기", style = textStyle16B().copy(fontSize = 18.sp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}