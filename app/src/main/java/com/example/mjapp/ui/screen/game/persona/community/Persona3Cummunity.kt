package com.example.mjapp.ui.screen.game.persona.community

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.SelectChip
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle30B
import com.example.network.model.Persona3CommunityResult

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
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 50.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(list) {
                    Persona3CommunityCard(
                        item = it,
                        updateRank = viewModel::updateRank,
                        updateSelectRank = viewModel::updateSelectRank
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
    item: Persona3CommunityResult,
    updateRank: (Int, Int) -> Unit = { _, _ -> },
    updateSelectRank: (Int, Int) -> Unit = { _, _ -> },
) {
    Column(
        modifier = Modifier
            .background(Color(0xFF131234), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(item.arcana, style = textStyle30B(MyColorWhite))
            Spacer(modifier = Modifier.weight(1f))

            Text("${item.rank} RANK", style = textStyle30B(MyColorDarkBlue))
        }
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Persona3RankChip(
                rank = 2,
                isSelected = item.selectRank == 2,
                modifier = Modifier.weight(1f),
                onClick = { updateSelectRank(item.idx, 2) }
            )
            Persona3RankChip(
                rank = 3,
                isSelected = item.selectRank == 3,
                modifier = Modifier.weight(1f),
                onClick = { updateSelectRank(item.idx, 3) }
            )
            Persona3RankChip(
                rank = 4,
                isSelected = item.selectRank == 4,
                modifier = Modifier.weight(1f),
                onClick = { updateSelectRank(item.idx, 4) }
            )
            Persona3RankChip(
                rank = 5,
                isSelected = item.selectRank == 5,
                modifier = Modifier.weight(1f),
                onClick = { updateSelectRank(item.idx, 5) }
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Persona3RankChip(
                rank = 6,
                isSelected = item.selectRank == 6,
                modifier = Modifier.weight(1f),
                onClick = { updateSelectRank(item.idx, 6) }
            )
            Persona3RankChip(
                rank = 7,
                isSelected = item.selectRank == 7,
                modifier = Modifier.weight(1f),
                onClick = { updateSelectRank(item.idx, 7) }
            )
            Persona3RankChip(
                rank = 8,
                isSelected = item.selectRank == 8,
                modifier = Modifier.weight(1f),
                onClick = { updateSelectRank(item.idx, 8) }
            )
            Persona3RankChip(
                rank = 9,
                isSelected = item.selectRank == 9,
                modifier = Modifier.weight(1f),
                onClick = { updateSelectRank(item.idx, 9) }
            )
        }
        val selectData = item.list.firstOrNull { it.rank == item.selectRank }
        selectData?.let {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .border(1.dp, MyColorDarkBlue, RoundedCornerShape(16.dp))
                    .background(MyColorDarkBlue.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = it.contents,
                    style = textStyle14(color = MyColorWhite),
                    modifier = Modifier.padding(16.dp)
                )

                TextButton(
                    text = "완료",
                    onClick = { updateRank(item.idx, item.selectRank) },
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    backgroundColor = MyColorDarkBlue,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}

@Composable
fun Persona3RankChip(
    modifier: Modifier = Modifier,
    rank: Int,
    isSelected: Boolean = false,
    onClick: (Int) -> Unit
) {
    SelectChip(
        text = "${rank}랭크",
        isSelected = isSelected,
        selectedColor = MyColorDarkBlue,
        selectedBackground = MyColorDarkBlue.copy(alpha = 0.5f),
        selectedTextColor = MyColorWhite,
        unselectedColor = MyColorDarkBlue,
        paddingValues = PaddingValues(vertical = 12.dp),
        modifier = modifier,
        onClick = { onClick(rank) }
    )
}