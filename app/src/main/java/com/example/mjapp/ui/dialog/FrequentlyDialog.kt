package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.ImageDoubleCard
import com.example.mjapp.ui.dialog.viewmodel.FrequentlyViewModel
import com.example.mjapp.ui.screen.accountbook.add.IncomeExpenditureType
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.util.formatAmountWithSign
import com.example.mjapp.util.rememberLifecycleEvent
import com.example.mjapp.util.textStyle14B
import com.example.mjapp.util.textStyle16B
import com.example.network.model.FixedItem
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FrequentlyDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (FixedItem) -> Unit,
    viewModel: FrequentlyViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val state = rememberPagerState { viewModel.list.size }
    var isSetting by remember { mutableStateOf(false) }

    ConfirmCancelStatusDialog(
        status = status,
        isShow = isShow,
        title = "즐겨찾기",
        color = MyColorTurquoise,
        onDismiss = onDismiss,
        onCancelClick = onDismiss,
        onConfirmClick = {
            onSelect(viewModel.list[state.currentPage])
            onDismiss()
        },
        topContents = {
            IconBox(
                iconSize = 22.dp,
                iconRes = R.drawable.ic_setting,
                boxShape = CircleShape,
                boxColor = MyColorTurquoise,
                onClick = { isSetting = isSetting.not() }
            )
            Spacer(modifier = Modifier.width(10.dp))
            DialogCloseButton(onClose = onDismiss)
        },
        bodyContents = {
            FrequentlySpinner(
                selectList = viewModel.list,
                state = state,
                isSetting = isSetting,
                onDelete = viewModel::deleteFrequently,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 35.dp, horizontal = 20.dp)
            )
        },
    )

    val lifecycleEvent = rememberLifecycleEvent()

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchFrequentlyAccountBook()
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FrequentlySpinner(
    modifier: Modifier = Modifier,
    selectList: List<FixedItem>,
    isSetting: Boolean,
    state: PagerState,
    onDelete: (Int) -> Unit
) {
    Box(modifier = modifier) {
        VerticalPager(
            contentPadding = PaddingValues(vertical = 60.dp),
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) { index ->
            val pageOffset = ((state.currentPage - index)
                    + state.currentPageOffsetFraction).absoluteValue
            val item = selectList[index]
            val color = when {
                item.amount < 0 -> MyColorRed
                item.amount > 0 -> MyColorTurquoise
                else -> MyColorGray
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                ImageDoubleCard(
                    resId = IncomeExpenditureType.getImageByType(item.usageType),
                    imageSize = DpSize(24.dp, 24.dp),
                    innerPadding = PaddingValues(3.dp),
                    topCardColor = color,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(30.dp)
                )

                Text(
                    text = item.whereToUse,
                    style = textStyle16B(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .graphicsLayer {
                            alpha = lerp(
                                start = 0.2f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                )

                if (isSetting) {
                    Text(
                        text = item.amount.formatAmountWithSign(),
                        style = textStyle14B().copy(color = color),
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .graphicsLayer {
                                alpha = lerp(
                                    start = 0.2f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                    )
                } else {
                    DialogCloseButton(onClose = { onDelete(item.id) })
                }

            }
        }

        Box(
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(MyColorGray)
        )
        Box(
            modifier = Modifier
                .padding(top = 120.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(MyColorGray)
        )
    }
}