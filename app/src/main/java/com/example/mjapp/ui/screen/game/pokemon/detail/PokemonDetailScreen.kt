package com.example.mjapp.ui.screen.game.pokemon.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.pokemonBackground
import com.example.mjapp.util.pokemonGrayBackground
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle18B
import com.example.mjapp.util.textStyle20B
import com.example.mjapp.util.textStyle24B
import com.example.network.model.EvolutionInfo
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.PokemonInfo
import com.example.network.model.TypeInfo

@Composable
fun PokemonDetailScreen(
    navHostController: NavHostController? = null,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val detailInfo by viewModel.pokemon.collectAsStateWithLifecycle()

    HeaderBodyBottomContainer(
        status = status,
        heightContent = {
            CommonGnb(
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                }, endButton = {
                    PokemonDetailTopButtons(
                        isShiny = viewModel.isShiny.value,
                        isCatch = detailInfo.pokemonInfo.isCatch,
                        updateIsShiny = viewModel::updateIsShiny,
                        updateIsCatch = viewModel::updateIsCatch
                    )
                }, title = detailInfo.pokemonInfo.number
            )
        },
        bodyContent = {
            PokemonDetailBody(
                detailInfo = detailInfo,
                isShiny = viewModel.isShiny.value,
                onUpdateNumber = { it?.let(viewModel::updateNumber) }
            )
        },
        bottomContent = {
            PokemonDetailBottom(viewModel::insertPokemonCounter)
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(
            if (detailInfo.pokemonInfo.isCatch) pokemonBackground()
            else pokemonGrayBackground()
        )
    )
}

@Composable
fun PokemonDetailTopButtons(
    isShiny: Boolean = false,
    isCatch: Boolean = false,
    updateIsShiny: () -> Unit = {},
    updateIsCatch: () -> Unit = {}
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Image(
            painter = painterResource(
                if (isCatch) R.drawable.ic_monser_ball_fill
                else R.drawable.ic_monser_ball
            ),
            contentDescription = null,
            modifier = Modifier.nonRippleClickable(updateIsCatch))
        Icon(
            painter = painterResource(R.drawable.ic_shiny),
            contentDescription = null,
            tint = if(isShiny) Color(0xFFEFCC18) else MyColorWhite,
            modifier = Modifier.nonRippleClickable(updateIsShiny))
    }
}

@Composable
fun PokemonDetailBody(
    detailInfo: PokemonDetailInfo,
    isShiny: Boolean = false,
    onUpdateNumber: (String?) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        item {
            PokemonDetailItem(
                isPrev = detailInfo.beforeInfo != null,
                isNext = detailInfo.nextInfo != null,
                isShiny = isShiny,
                pokemon = detailInfo.pokemonInfo,
                onPrevClick = { onUpdateNumber(detailInfo.beforeInfo?.number) },
                onNextClick = { onUpdateNumber(detailInfo.nextInfo?.number) }
            )
        }

        item {
            PokemonEvolution(
                isShiny = isShiny,
                list = detailInfo.evolutionInfo,
                onUpdateClick = onUpdateNumber
            )
        }

        item { PokemonStatusAndWeaknesses(detailInfo.pokemonInfo) }
    }
}

@Composable
fun PokemonDetailItem(
    isPrev: Boolean = false,
    isNext: Boolean = false,
    isShiny: Boolean = false,
    pokemon: PokemonInfo,
    onPrevClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_big_prev),
                contentDescription = null,
                tint = if (isPrev) MyColorWhite else MyColorWhite.copy(alpha = 0.3f),
                modifier = Modifier.nonRippleClickable(onPrevClick)
            )
            AsyncImage(
                model = if (isShiny) pokemon.shinyImage else pokemon.image,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img_egg),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(R.drawable.ic_big_next),
                contentDescription = null,
                tint = if (isNext) MyColorWhite else MyColorWhite.copy(alpha = 0.3f),
                modifier = Modifier.nonRippleClickable(onNextClick)
            )
        }
        Spacer(modifier = Modifier.height(25.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            pokemon.getTyeInfoList().forEach {
                PokemonTypeChip(it)
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            pokemon.name,
            style = textStyle20B()
                .copy(color = MyColorWhite, fontSize = 30.sp, textAlign = TextAlign.Center)
        )
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .background(MyColorWhite.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(horizontal = 15.dp, vertical = 20.dp)
        ) {
            Text(pokemon.classification, style = textStyle18B(color = MyColorWhite))
            Spacer(modifier = Modifier.height(5.dp))
            Text(pokemon.description, style = textStyle16(color = MyColorWhite))
        }
    }
}

@Composable
fun PokemonTypeChip(info: TypeInfo) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color(info.color), shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Image(
            painter = painterResource(info.imageRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(info.koreanName, style = textStyle14(color = MyColorWhite))
    }
}

@Composable
fun PokemonEvolution(
    isShiny: Boolean = false,
    list: List<EvolutionInfo> = emptyList(),
    onUpdateClick: (String) -> Unit = {}
) {
    if (list.isEmpty()) return

    Text(
        text = "진화",
        style = textStyle20B(color = MyColorWhite),
        modifier = Modifier.padding(top = 30.dp)
    )
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        list.forEach {
            PokemonEvolutionItem(isShiny, it, onUpdateClick)
        }
    }
}

@Composable
fun PokemonEvolutionItem(
    isShiny: Boolean,
    info: EvolutionInfo,
    onUpdateClick: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = if (isShiny) info.beforeShinyDot else info.beforeDot,
            contentDescription = null,
            placeholder = painterResource(R.drawable.img_egg),
            modifier = Modifier.size(70.dp).nonRippleClickable {
                onUpdateClick(info.beforeNumber)
            }
        )
        Image(
            painter = painterResource(R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
        AsyncImage(
            model = if (isShiny) info.afterShinyDot else info.afterDot,
            contentDescription = null,
            placeholder = painterResource(R.drawable.img_egg),
            modifier = Modifier.size(70.dp).nonRippleClickable {
                onUpdateClick(info.afterNumber)
            }
        )

        Text(info.evolutionCondition, style = textStyle16(color = MyColorWhite))
    }
}

@Composable
fun PokemonStatusAndWeaknesses(
    pokemon: PokemonInfo
) {
    val weakList = pokemon.getWeakList()

    Text(
        text = "종족값",
        style = textStyle20B(color = MyColorWhite),
        modifier = Modifier.padding(top = 30.dp)
    )
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .background(MyColorWhite.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(vertical = 15.dp)
    ) {
        pokemon.getStatusInfo().forEach { (title, value) ->
            PokemonStatusItem(
                title = title,
                value = value,
                modifier = Modifier.weight(1f)
            )
        }
    }

    Text(
        text = "타입 방어 상성",
        style = textStyle20B(color = MyColorWhite),
        modifier = Modifier.padding(top = 30.dp)
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(top = 15.dp)
    ) {
        val goodEffect = weakList.filter { it.first >= 2f }.map { it.second }
        val notGoodEffect = weakList.filter { it.first < 1 }.map { it.second }
        val normalEffect = weakList.filter { it.first == 1f }.map { it.second }
        val ineffective = weakList.filter { it.first == 0f }.map { it.second }

        PokemonWeakItem("효과가 좋다", goodEffect)
        PokemonWeakItem("효과가 별로다", notGoodEffect)
        PokemonWeakItem("보통", normalEffect)
        if (ineffective.isNotEmpty()) {
            PokemonWeakItem("효과가 없다", ineffective)
        }
    }
}

@Composable
fun PokemonStatusItem(
    title: String,
    value: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = textStyle14(color = MyColorGray)
        )
        Text(
            text = value,
            style = textStyle24B(color = MyColorWhite),
        )
    }
}

@Composable
fun PokemonWeakItem(
    title: String,
    imageResList: List<Int>
) {
    Column(
        modifier = Modifier
            .background(MyColorWhite.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(top = 10.dp, start = 15.dp, bottom = 15.dp, end = 15.dp)
    ) {
        Text(title, style = textStyle14(color = MyColorWhite))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            imageResList.forEach {
                Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

@Composable
fun PokemonDetailBottom(
    onCounterRegisterClick: () -> Unit = {},
) {
    TextButton(
        text = "카운터 등록",
        onClick = onCounterRegisterClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 24.dp)
    )
}