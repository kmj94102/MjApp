package com.example.mjapp.ui.screen.game.persona

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle20B
import com.example.network.model.Persona3CommunityUpdateParam
import com.example.network.model.Persona3Schedule
import com.example.network.model.getUpdateCommunityParam

@Composable
fun Persona3Screen(
    navHostController: NavHostController? = null,
    viewModel: Persona3ViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val info by viewModel.info.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            Persona3Header(navHostController)
        },
        bodyContent = {
            Persona3Body(info, viewModel::updateSchedule)
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(Color(0xFF005AA4))
    )
}

@Composable
fun Persona3Header(navHostController: NavHostController? = null) {
    CommonGnb(
        title = "커뮤 스케줄",
        startButton = {
            CommonGnbBackButton { navHostController?.popBackStack() }
        },
        endButton = {
            Row {
                Image(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .nonRippleClickable {
                            navHostController?.navigate(NavScreen2.Persona3Quest)
                        }
                )
                Spacer(Modifier.width(10.dp))

                Image(
                    painter = painterResource(R.drawable.ic_history),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .nonRippleClickable {
                            navHostController?.navigate(NavScreen2.Persona3Community)
                        }
                )
            }
        }
    )
}

@Composable
fun Persona3Body(
    info: Map<String, List<Persona3Schedule>>,
    onCompleteClick: (String, List<Int>, List<Persona3CommunityUpdateParam>) -> Unit = { _, _, _ -> }
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        var header = ""
        info.forEach { key, list ->
            if (key.split(".").getOrNull(0) != header) {
                val month = key.split(".").getOrElse(0) { "" }

                stickyHeader {
                    Text(
                        "${month}월",
                        style = textStyle20B(color = Color(0xFF0EF2E5)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF005AA4))
                            .padding(12.dp)
                    )
                }
                header = month
            }

            item {
                Persona3Item(
                    list = list,
                    onCompleteClick = { idxList, communityList ->
                        onCompleteClick(key, idxList, communityList)
                    }
                )
            }
        }
    }
}

@Composable
fun Persona3Item(
    list: List<Persona3Schedule>,
    onCompleteClick: (List<Int>, List<Persona3CommunityUpdateParam>) -> Unit = { _, _ -> }
) {
    val item = list.first()
    Column(
        modifier = Modifier
            .background(Color(0xCC131234), shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text(
            item.getDayInfo(),
            style = textStyle12B(color = MyColorWhite),
            modifier = Modifier
                .background(Color(0xFF0036FB), RoundedCornerShape(12.dp))
                .padding(8.dp)
        )
        Spacer(Modifier.height(12.dp))

        list.forEach {
            Text(it.title, style = textStyle14(MyColorGray))
            Spacer(Modifier.height(6.dp))
            Text(
                it.contents,
                style = textStyle16(MyColorWhite),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }
        Spacer(Modifier.height(14.dp))

        TextButton(
            text = "완료",
            borderColor = Color(0xFF0EF2E5),
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth()
        ) {
            onCompleteClick(list.map { it.idx }, list.getUpdateCommunityParam())
        }
    }
}