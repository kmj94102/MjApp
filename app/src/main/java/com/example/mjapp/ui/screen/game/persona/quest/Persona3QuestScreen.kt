package com.example.mjapp.ui.screen.game.persona.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle18B
import com.example.mjapp.util.textStyle20B
import com.example.network.database.entity.Persona3Quest

@Composable
fun Persona3QuestScreen(
    navHostController: NavHostController? = null,
    viewModel: Persona3QuestViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val list by viewModel.list.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        paddingValues = PaddingValues(),
        modifier = Modifier.background(Color(0xFF005AA4)),
        headerContent = {
            CommonGnb(
                title = "퀘스트",
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                }
            )
        },
        bodyContent = {
            Persona3QuestBody(
                list = list,
                onClick = viewModel::updatePersona3Quest
            )
        }
    )
}

@Composable
fun Persona3QuestBody(
    list: List<Persona3Quest>,
    onClick: (Int) -> Unit = {}
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(list) { Persona3QuestItem(it, onClick) }
    }
}

@Composable
fun Persona3QuestItem(
    item: Persona3Quest,
    onClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(Color(0xFF0F1F4A), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = item.title,
                style = textStyle20B(color = MyColorWhite),
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.fillMaxWidth()
            )

            if (item.deadline.isNotEmpty()) {
                Text(
                    text = item.deadline,
                    style = textStyle14(color = MyColorGray),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.height(12.dp))

            if (item.condition.isNotEmpty()) {
                Text(
                    text = item.condition,
                    style = textStyle16B(color = MyColorDarkBlue),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
            }

            Text(
                text = item.contents,
                style = textStyle16(color = MyColorWhite),
                modifier = Modifier.fillMaxWidth()
            )

            if (item.guide.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = item.guide,
                    style = textStyle16(color = MyColorWhite),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.height(20.dp))

            Text(
                text = item.reward,
                style = textStyle18B(color = MyColorWhite),
                modifier = Modifier.fillMaxWidth()
            )
        }

        TextButton(
            text = "완료",
            backgroundColor = MyColorDarkBlue,
            onClick = { onClick(item.id) },
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}