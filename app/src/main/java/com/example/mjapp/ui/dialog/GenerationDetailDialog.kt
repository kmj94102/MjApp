package com.example.mjapp.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.ConditionImage
import com.example.mjapp.ui.dialog.viewmodel.PokemonDetailViewModel
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.toast
import kotlinx.coroutines.delay

/**
 * 포켓몬 상세 다이얼로그
 * @param number 포켓몬 번호
 * @param onDismiss 다이얼로그 종료 리스너
 * **/
@Composable
fun GenerationDetailDialog(
    isShow: Boolean,
    number: String,
    isCatch: Boolean,
    onDismiss: () -> Unit,
    updateCatch: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    if (isShow.not()) return
    val status by viewModel.status.collectAsStateWithLifecycle()
    val context = LocalContext.current

    PokemonDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        topIcon = {
            GenerationDetailTopIcon(
                isCatch = isCatch,
                updateCatch = updateCatch
            )
        },
        bodyContents = {
            val info = viewModel.info.value
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp)
            ) {
                when {
                    status.isLoading -> PokemonSearchLoading()
                    info == null -> PokemonDetailEmpty()
                    else -> PokemonDetailBody(
                        pokemonDetailInfo = info,
                        isShiny = viewModel.isShiny.value,
                        selectState = 2,
                        onItemClick = {}
                    )
                }
            }
        },
        bottomContents = {
            Spacer(modifier = Modifier.height(34.dp))
        }
    )

    LaunchedEffect(number) {
        viewModel.fetchPokemonDetail(number)
    }

    LaunchedEffect(status.message) {
        if (status.message.trim().isEmpty()) return@LaunchedEffect
        context.toast(status.message)
        delay(500)
        status.updateMessage("")
    }
}

@Composable
fun GenerationDetailTopIcon(
    isCatch: Boolean,
    updateCatch: () -> Unit
) {
    ConditionImage(
        value = isCatch,
        trueImageRes = R.drawable.img_monster_ballpng,
        falseImageRes = R.drawable.img_monster_ball_empty,
        modifier = Modifier
            .size(30.dp)
            .nonRippleClickable(updateCatch)
    )
}
