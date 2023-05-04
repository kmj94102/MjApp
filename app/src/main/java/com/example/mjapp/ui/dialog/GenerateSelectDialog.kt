package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle16B

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenerateSelectDialog(
    isShow: State<Boolean>,
    okClickListener: (String) -> Unit,
    cancelClickListener: () -> Unit
) {
    val pagerState = rememberPagerState()
    val generateList = (1..9).toList().map { "$it" }

    if (isShow.value) {
        Dialog(onDismissRequest = {}) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .border(1.dp, MyColorBlack, RoundedCornerShape(25.dp))
                    .background(MyColorWhite)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "포켓몬 세대 선택",
                        style = textStyle12().copy(fontSize = 22.sp),
                        modifier = Modifier
                            .padding(top = 11.dp)
                            .align(Alignment.Center)
                    )
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp, top = 11.dp)
                            .size(30.dp)
                            .clip(CircleShape)
                            .border(1.dp, MyColorBlack, CircleShape)
                            .background(MyColorRed)
                            .align(Alignment.CenterEnd)
                            .nonRippleClickable { cancelClickListener() }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = null,
                            modifier = Modifier
                                .size(21.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(36.dp))
                SelectSpinner(
                    selectList = generateList,
                    state = pagerState,
                    initValue = "1",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(36.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 17.dp)
                ) {
                    DoubleCard(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "취소",
                            style = textStyle16B().copy(fontSize = 18.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .nonRippleClickable { cancelClickListener() }
                        )
                    }
                    DoubleCard(topCardColor = MyColorRed, modifier = Modifier.weight(1f)) {
                        Text(
                            text = "확인",
                            style = textStyle16B().copy(fontSize = 18.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .nonRippleClickable { okClickListener(generateList[pagerState.currentPage]) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}