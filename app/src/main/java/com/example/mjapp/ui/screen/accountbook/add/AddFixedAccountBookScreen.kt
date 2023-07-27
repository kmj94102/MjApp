package com.example.mjapp.ui.screen.accountbook.add

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.UnderLineText
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.*

@Composable
fun AddFixedAccountBookScreen(
    onBackClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp, start = 20.dp)
        ) {
            IconBox(boxColor = MyColorTurquoise) {
                onBackClick()
            }

            Text(
                text = "고정 내역 등록",
                style = textStyle16B(),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        UnderLineText(
            textValue = "2023.06",
            textStyle = textStyle24B(),
            underLineColor = MyColorTurquoise,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))

        AddFixedAccountBookItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp)
        )

        FixedAccountBookCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp)
        )

    }
}

@Composable
fun AddFixedAccountBookItem(
    modifier: Modifier = Modifier
) {
    DoubleCard(
        bottomCardColor = MyColorTurquoise,
        modifier = modifier
    ) {
        Text(
            text = "고정 내역 추가",
            style = textStyle16B(),
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorTurquoise)
                .padding(vertical = 5.dp, horizontal = 15.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MyColorBlack)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            DoubleCard(
                topCardColor = MyColorTurquoise,
                connerSize = 5.dp,
                modifier = Modifier
                    .width(30.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(27.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_game),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MyColorTurquoise)
                    .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
            ) {
                Text(
                    text = "수입",
                    style = textStyle16B(),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MyColorWhite)
                    .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
            ) {
                Text(
                    text = "지출",
                    style = textStyle16B(),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        }

        CommonTextField(
            value = "",
            onTextChange = {

            },
            hint = "사용 내용",
            unfocusedIndicatorColor = MyColorGray,
            focusedIndicatorColor = MyColorBlack,
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            CommonTextField(
                value = "01일",
                onTextChange = {

                },
                textStyle = textStyle16().copy(textAlign = TextAlign.Center),
                hint = "날짜",
                unfocusedIndicatorColor = MyColorGray,
                focusedIndicatorColor = MyColorBlack,
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.width(60.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))

            CommonTextField(
                value = "",
                onTextChange = {

                },
                textStyle = textStyle16().copy(textAlign = TextAlign.End),
                hint = "금액",
                keyboardType = KeyboardType.Number,
                unfocusedIndicatorColor = MyColorGray,
                focusedIndicatorColor = MyColorBlack,
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.width(15.dp))

        Card(
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, MyColorBlack),
            colors = CardDefaults.cardColors(
                containerColor = MyColorTurquoise
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = "고정 내역 추가",
                style = textStyle16B(),
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun FixedAccountBookCard(
    modifier: Modifier = Modifier
) {
    DoubleCard(
        bottomCardColor = MyColorTurquoise,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {

                    }
                )
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 15.dp)
        ) {
            DoubleCard(
                topCardColor = MyColorTurquoise,
                connerSize = 5.dp,
                modifier = Modifier
                    .width(50.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(47.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_game),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
            ) {
                Text(text = "게임", style = textStyle12())
                Text(
                    text = "사용 내용",
                    style = textStyle16B(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(horizontal = 15.dp)
        ) {
            CommonTextField(
                value = "01일",
                onTextChange = {

                },
                textStyle = textStyle16().copy(textAlign = TextAlign.Center),
                hint = "날짜",
                unfocusedIndicatorColor = MyColorGray,
                focusedIndicatorColor = MyColorBlack,
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.width(60.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))

            CommonTextField(
                value = "- 1,000,000 원",
                onTextChange = {

                },
                textStyle = textStyle16().copy(textAlign = TextAlign.End, color = MyColorRed),
                hint = "금액",
                keyboardType = KeyboardType.Number,
                unfocusedIndicatorColor = MyColorGray,
                focusedIndicatorColor = MyColorBlack,
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.width(15.dp))

        Card(
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, MyColorBlack),
            colors = CardDefaults.cardColors(
                containerColor = MyColorTurquoise
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = "등록하기",
                style = textStyle16B(),
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}