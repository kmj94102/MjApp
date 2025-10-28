package com.example.mjapp.ui.screen.game.dmo.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle20B
import com.example.network.model.DmoUnionInfo

@Composable
fun DmoUnionScreen(
    navHostController: NavHostController? = null,
    viewModel: DmoUnionViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack),
        headerContent = {
            CommonGnb(
                title = "디지몬 유니온",
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                }
            )
        },
        bodyContent = {
            DmoUnionBody(viewModel.list)
        }
    )
}

@Composable
fun DmoUnionBody(list: List<DmoUnionInfo>) {
    LazyColumn {
        items(list.size) {
            DmoUnionItem(
                item = list[it],
                isOdd = it % 2 == 0,
                onClick = {}
            )
        }
    }
}

@Composable
fun DmoUnionItem(
    item: DmoUnionInfo,
    isOdd: Boolean,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isOdd) MyColorLightBlack else MyColorBlack)
            .padding(vertical = 15.dp, horizontal = 24.dp)
            .nonRippleClickable {
                onClick(item.unionId)
            }
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = if (item.isFavorite) MyColorRed else MyColorGray
            )
            Text(
                item.groupName,
                style = textStyle20B(color = MyColorWhite),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            itemVerticalAlignment = Alignment.CenterVertically
        ) {
            item.getRewardInfo().forEachIndexed { index, (contents, isComplete) ->
                Text(
                    contents,
                    style = textStyle14(
                        color = if (isComplete == true) MyColorDarkBlue else MyColorGray
                    )
                )

                if (index != item.getRewardInfo().lastIndex) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(9.dp)
                            .width(1.dp)
                            .background(MyColorGray)
                    )
                }
            }
        }
    }
}