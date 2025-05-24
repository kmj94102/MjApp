package com.example.mjapp.ui.screen.game.persona.community

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.ui.theme.myFont
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle30B
import com.example.network.model.Persona3Community

@Composable
fun Persona3CommunityScreen(
    navHostController: NavHostController? = null,
    viewModel: Persona3CommunityViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val list by viewModel.list.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonGnb(
                title = "커뮤 진행도",
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                },
            )
        },
        bodyContent = {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(list) {
                    Persona3CommunityCard(
                        item = it,
                        onDecreaseClick = viewModel::decreaseRank,
                        onIncreaseClick = viewModel::increaseRank
                    )
                }
            }
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(Color(0xFF005AA4))
    )
}

@Composable
fun Persona3CommunityCard(
    item: Persona3Community,
    onIncreaseClick: (Int) -> Unit = {},
    onDecreaseClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(Color(0xFF131234), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(item.arcana, style = textStyle30B(MyColorWhite))
            Spacer(modifier = Modifier.width(5.dp))

            Box(
                modifier = Modifier
                    .size(1.dp, 14.dp)
                    .background(MyColorDarkBlue.copy(alpha = 0.5f))
            )
            Spacer(modifier = Modifier.width(5.dp))

            Text(item.name, style = textStyle14(MyColorDarkBlue.copy(alpha = 0.5f)))
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Spacer(Modifier.weight(1f))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(1.dp, MyColorDarkBlue, RoundedCornerShape(16.dp))
                    .padding(horizontal = 15.dp, vertical = 7.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_minus_2),
                    contentDescription = null,
                    tint = MyColorWhite,
                    modifier = Modifier.size(28.dp)
                        .nonRippleClickable {
                            onDecreaseClick(item.idx)
                        }
                )
            }
            Spacer(Modifier.width(16.dp))

            Text(
                item.rank.toString(),
                style = TextStyle(
                    color = Color(0xFF0EF2E5),
                    fontFamily = myFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    lineHeight = 42.sp,
                    letterSpacing = -(0.025).em
                )
            )
            Spacer(Modifier.width(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(1.dp, MyColorDarkBlue, RoundedCornerShape(16.dp))
                    .padding(horizontal = 15.dp, vertical = 7.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = null,
                    tint = MyColorWhite,
                    modifier = Modifier.size(28.dp)
                        .nonRippleClickable {
                            onIncreaseClick(item.idx)
                        }
                )
            }
        }
    }
}