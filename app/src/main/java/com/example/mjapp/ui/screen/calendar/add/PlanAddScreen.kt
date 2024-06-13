package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.custom.DoubleCardText
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.dialog.DateSelectDialog
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B

@Composable
fun PlanAddScreen(
    onBackClick: () -> Unit,
    goToScheduleAdd: (String) -> Unit,
    viewModel: PlanAddViewModel = hiltViewModel()
) {
    var isDialogShow by remember { mutableStateOf(false) }
    val status by viewModel.status.collectAsStateWithLifecycle()

    HighMediumLowContainer(
        status = status,
        onBackClick = onBackClick,
        heightContent = {
            PlanAddHeight(
                onBackClick = onBackClick,
                onScheduleAddClick = { goToScheduleAdd(viewModel.initDate) }
            )
        },
        mediumContent = {
            PlanAddMedium(
                viewModel = viewModel,
                onDateSelect = { isDialogShow = true }
            )
        },
        lowContent = {
            PlanAddLow(viewModel::insertPlan)
        }
    )

    DateSelectDialog(
        date = viewModel.planTasks.value.planDate,
        isShow = isDialogShow,
        onDismiss = {
            isDialogShow = false
        },
        onSelect = viewModel::updateDate
    )
}

@Composable
fun PlanAddHeight(
    onBackClick: () -> Unit,
    onScheduleAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 3.dp)
    ) {
        IconBox(onClick = onBackClick)
        Spacer(modifier = Modifier.weight(1f))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MyColorWhite)
                .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
                .nonRippleClickable(onScheduleAddClick)
        ) {
            Text(text = "일정", style = textStyle16B(), modifier = Modifier.padding(5.dp))
        }
        Spacer(modifier = Modifier.width(5.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MyColorPurple)
                .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
        ) {
            Text(text = "계획", style = textStyle16B(), modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun PlanAddMedium(
    viewModel: PlanAddViewModel,
    onDateSelect: () -> Unit,
) {
    val planTasks = viewModel.planTasks.value

    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            DoubleCardText(
                bottomCardColor = MyColorPurple,
                text = planTasks.planDate.ifEmpty { "날짜 선택" },
                textStyle = textStyle16B(),
                onClick = onDateSelect,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            DoubleCardTextField(
                value = planTasks.title,
                onTextChange = viewModel::updateTitle,
                hint = "계획 제목",
                textStyle = textStyle16(),
                bottomCardColor = MyColorPurple,
                modifier = Modifier.fillMaxWidth()
            )
        }

        planTasks.taskList.forEachIndexed { index, task ->
            item {
                DoubleCardTextField(
                    value = task.contents,
                    onTextChange = { viewModel.updatePlanContents(index, it) },
                    hint = "계획 내용",
                    textStyle = textStyle16(),
                    modifier = Modifier.fillMaxWidth(),
                    bottomCardColor = MyColorPurple,
                    tailIcon = {
                        IconBox(
                            boxColor = MyColorRed,
                            boxShape = CircleShape,
                            iconRes = R.drawable.ic_close,
                            iconSize = 16.dp,
                            onClick = { viewModel.removePlanItem(index) },
                            modifier = Modifier.padding(end = 10.dp)
                        )
                    }
                )
            }
        }

        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                IconBox(
                    boxShape = CircleShape,
                    boxColor = MyColorRed,
                    iconRes = R.drawable.ic_plus,
                    onClick = viewModel::addPlanItem
                )
            }
        }
    }
}

@Composable
fun PlanAddLow(onClick: () -> Unit) {
    DoubleCardButton(
        text = "등록하기",
        topCardColor = MyColorPurple,
        onClick = onClick
    )
}