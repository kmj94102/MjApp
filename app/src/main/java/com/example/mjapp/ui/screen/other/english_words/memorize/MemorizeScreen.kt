package com.example.mjapp.ui.screen.other.english_words.memorize

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonProgressBar
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.screen.other.english_words.EnglishEmpty
import com.example.mjapp.ui.screen.other.english_words.EnglishWordsHeader
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorBlue
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle18
import com.example.mjapp.util.textStyle18B
import com.example.network.model.VocabularyListResult
import kotlinx.coroutines.delay
import java.lang.Integer.max
import java.lang.Integer.min

@Composable
fun MemorizeScreen(
    onBackClick: () -> Unit,
    viewModel: MemorizeViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HighMediumLowContainer(
        status = status,
        heightContent = {
            EnglishWordsHeader(
                day = viewModel.day.intValue,
                onBackClick = onBackClick,
                onDaySelect = viewModel::updateDay
            )
        },
        mediumContent = {
            if (viewModel.list.isNotEmpty()) {
                MemorizeBody(viewModel = viewModel)
            } else {
                EnglishEmpty(message = "단어장을 준비 중입니다.")
            }
        },
        lowContent = {
            MemorizePlayer(viewModel = viewModel)
        }
    )
}

@Composable
fun MemorizeBody(
    viewModel: MemorizeViewModel
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 30.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.list.forEach {
            item {
                MemorizeItem(item = it)
            }
        }
    }
}

@Composable
fun MemorizeItem(
    item: VocabularyListResult
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        DoubleCard(
            topCardColor = MyColorBeige,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(item.wordGroup.name)
                        }
                        append(", ${item.wordGroup.modify}")
                    },
                    style = textStyle18(),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = item.wordGroup.meaning,
                    style = textStyle12().copy(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item.list.forEach {
            DoubleCard(
                bottomCardColor = MyColorBeige,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = it.word,
                        style = textStyle18B(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MyColorBeige)
                            .padding(horizontal = 15.dp, vertical = 5.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MyColorBlack)
                    )
                    Text(
                        text = it.meaning, style = textStyle16B().copy(color = MyColorBlue),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .padding(top = 10.dp)
                    )
                    Text(
                        text = it.hint.replace("\\\\\\\\\\\\", "\n"),
                        style = textStyle12().copy(fontSize = 14.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MemorizePlayer(
    viewModel: MemorizeViewModel
) {
    val player = remember { MediaPlayer() }
    var isReady by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf("00:00 / 00:00") }
    var currentPosition by remember { mutableIntStateOf(0) }
    val address by viewModel.address.collectAsStateWithLifecycle()

    runCatching {
        player.setDataSource(address)
        player.prepareAsync()

        player.setOnPreparedListener {
            isReady = true
            time = getProgressTime(player.currentPosition, player.duration)
        }
        player.setOnCompletionListener {
            player.stop()
            player.reset()
            player.setDataSource(address)
            player.prepareAsync()
            currentPosition = 0
            isPlaying = false
        }
    }

    DoubleCard(
        bottomCardColor = MyColorBeige,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            CommonProgressBar(
                modifier = Modifier.fillMaxWidth(),
                max = runCatching { player.duration }.getOrElse { 0 },
                currentPosition = currentPosition,
                onValueChanged = {
                    runCatching {
                        val position = (it * player.duration).toInt()
                        player.seekTo(position)
                        currentPosition = position

                        time = getProgressTime(player.currentPosition, player.duration)
                    }
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_backword),
                    contentDescription = null,
                    tint = MyColorBlack,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(24.dp)
                        .nonRippleClickable {
                            runCatching { player.seekTo(max(player.currentPosition - 3000, 0)) }
                        }
                )
                Icon(
                    painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                    contentDescription = null,
                    tint = MyColorBlack,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(24.dp)
                        .nonRippleClickable {
                            runCatching {
                                if (isReady.not()) return@nonRippleClickable
                                isPlaying = if (isPlaying) {
                                    player.pause()
                                    false
                                } else {
                                    player.start()
                                    true
                                }
                            }
                        }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_forward),
                    contentDescription = null,
                    tint = MyColorBlack,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(24.dp)
                        .nonRippleClickable {
                            runCatching {
                                player.seekTo(
                                    min(
                                        player.currentPosition + 3000,
                                        player.duration
                                    )
                                )
                            }
                        }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_replay),
                    contentDescription = null,
                    tint = MyColorBlack,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(24.dp)
                        .nonRippleClickable {
                            runCatching {
                                player.stop()
                                player.reset()
                                player.setDataSource(address)
                                player.prepareAsync()
                                player.start()
                                currentPosition = 0
                                isPlaying = true
                            }
                        }
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(text = time, style = textStyle12B())
            }
        }
    }

    LaunchedEffect(address) {
        player.stop()
        player.reset()
        player.setDataSource(address)
        player.prepareAsync()
        currentPosition = 0
        isPlaying = false
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(1000)
            runCatching {
                time = getProgressTime(player.currentPosition, player.duration)
                currentPosition = player.currentPosition
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            runCatching {
                player.stop()
                player.reset()
            }
        }
    }
}

private fun getProgressTime(progress: Int, duration: Int): String {
    val progressMinutes = (progress / 60_000).toString().padStart(2, '0')
    val progressSeconds = (progress % 60_000 / 1_000).toString().padStart(2, '0')
    val durationMinutes = (duration / 60_000).toString().padStart(2, '0')
    val durationSeconds = (duration % 60_000 / 1_000).toString().padStart(2, '0')

    return "$progressMinutes:$progressSeconds / $durationMinutes:$durationSeconds"
}