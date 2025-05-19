package com.example.mjapp.ui.screen.game.persona.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle30B
import kotlin.math.roundToInt

@Composable
fun Persona3CommunityScreen(
    navHostController: NavHostController? = null,
    viewModel: Persona3CommunityViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

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
            Persona3CommunityCard()
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(Color(0xFF005AA4))
    )
}

@Composable
fun Persona3CommunityCard() {
    var progress by remember { mutableIntStateOf(2) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xCC131234), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text("광대", style = textStyle30B(MyColorDarkBlue))
            Spacer(Modifier.width(5.dp))

            Box(
                modifier = Modifier
                    .size(1.dp, 14.dp)
                    .background(MyColorDarkBlue.copy(alpha = 0.5f))
            )
            Spacer(Modifier.width(5.dp))

            Text("특별과외 활동부", style = textStyle14(MyColorDarkBlue.copy(alpha = 0.5f)))
            Spacer(Modifier.weight(1f))

            Text("RANK", style = textStyle14(MyColorDarkBlue.copy(alpha = 0.5f)))
            Spacer(Modifier.width(3.dp))

            Text("2", style = textStyle30B(Color(0xFF0EF2E5)))
        }
        Spacer(Modifier.height(5.dp))

        RoundedSlider(
            value = progress,
            onValueChange = { progress = it },
            valueRange = 0..10,
            modifier = Modifier.height(42.dp).background(MyColorRed)
        )
    }
}

@Composable
fun RoundedSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    valueRange: IntRange = 1..10,
    trackHeight: Dp = 32.dp,
    modifier: Modifier = Modifier
) {
    val sliderPosition = remember(value) { value.toFloat() }

    Slider(
        value = sliderPosition,
        onValueChange = {
            val rounded = it.roundToInt().coerceIn(valueRange)
            if (rounded != value) {
                onValueChange(rounded)
            }
        },
        valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
        steps = valueRange.last - valueRange.first - 1,
        modifier = modifier
            .fillMaxWidth()
//            .height(trackHeight) // 최소 터치 영역 확보
            .drawBehind {
                // 트랙 배경 (전체)
                val trackY = size.height
                val trackStroke = trackHeight.toPx()

                // 전체 트랙 (회색)
                drawRoundRect(
                    color = MyColorDarkBlue.copy(0.3f),
                    topLeft = Offset(0f, trackY - trackStroke ),
                    size = Size(size.width, trackStroke),
                    cornerRadius = CornerRadius(trackStroke, trackStroke )
                )

                // 활성화된 트랙 (보라색)
                val activeWidth = (sliderPosition - valueRange.first) / (valueRange.last - valueRange.first).toFloat() * size.width

                drawRoundRect(
                    color = MyColorDarkBlue,
                    topLeft = Offset(0f, trackY - trackStroke ),
                    size = Size(activeWidth, trackStroke),
                    cornerRadius = CornerRadius(trackStroke , trackStroke )
                )
            },
        colors = SliderDefaults.colors(
            thumbColor = Color.Transparent,
            activeTrackColor = Color.Transparent, // 투명하게 처리
            inactiveTrackColor = Color.Transparent,
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent
        )
    )
}