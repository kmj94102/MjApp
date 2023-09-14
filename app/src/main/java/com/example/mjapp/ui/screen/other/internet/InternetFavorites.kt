package com.example.mjapp.ui.screen.other.internet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCardText
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.WebViewHolder
import com.example.mjapp.ui.dialog.InsertFavoriteDialog
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorSkyBlue
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B

@Composable
fun InternetFavoritesScreen(
    onBackClick: () -> Unit,
    viewModel: InternetFavoritesViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isShow by remember { mutableStateOf(false) }

    HighMediumLowContainer(
        status = status,
        heightContent = {
            InternetFavoritesHeight(
                onBackClick = onBackClick,
                addFavorite = {
                    isShow = true
                }
            )
        },
        mediumContent = { InternetFavoritesMedium(viewModel) },
        lowContent = { InternetFavoritesLow(viewModel) },
        paddingValues = PaddingValues(top = 22.dp, start = 0.dp, end = 0.dp, bottom = 10.dp),
        reload = viewModel::fetchFavorites
    )

    InsertFavoriteDialog(
        isShow = isShow,
        onDismiss = {
            isShow = false
        },
        onInsert = {
            viewModel.insertFavorite(it)
        }
    )
}

@Composable
fun InternetFavoritesHeight(
    onBackClick: () -> Unit,
    addFavorite: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        IconBox(
            boxColor = MyColorBeige,
            onClick = onBackClick
        )

        Text(
            text = "인터넷 즐겨찾기",
            style = textStyle16B().copy(textAlign = TextAlign.Center),
            modifier = Modifier.weight(1f)
        )

        IconBox(
            boxColor = MyColorSkyBlue,
            iconRes = R.drawable.ic_plus,
            onClick = addFavorite
        )
    }
}

@Composable
fun InternetFavoritesMedium(
    viewModel: InternetFavoritesViewModel
) {
    var address = runCatching { viewModel.list[0].address }.getOrElse { "https://www.naver.com" }
    val context = LocalContext.current
    val webView = WebViewHolder(context).apply {
        loadUrl(address)
    }.webView

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(horizontal = 20.dp))
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, MyColorBlack),
            modifier = Modifier
                .padding(top = 20.dp)
                .weight(1f)
        ) {
            AndroidView(
                modifier = Modifier.weight(1f),
                factory = { webView }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 15.dp)
        ) {
            Text(
                text = "앱에서 열기",
                style = textStyle16B(),
                modifier = Modifier.nonRippleClickable { })
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "새로고침",
                style = textStyle16B(),
                modifier = Modifier.nonRippleClickable { webView.reload() })
        }

        val selectIndex = viewModel.selectIndex.value
        LaunchedEffect(selectIndex) {
            runCatching {
                val newAddress = viewModel.list[selectIndex].address
                if (address != newAddress) {
                    address = newAddress
                    webView.loadUrl(address)
                }
            }
        }
    }
}

@Composable
fun InternetFavoritesLow(
    viewModel: InternetFavoritesViewModel
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        viewModel.list.forEachIndexed { index, internetFavorite ->
            item {
                DoubleCardText(
                    onClick = { viewModel.updateSelectIndex(index) },
                    text = internetFavorite.name,
                    topCardColor = if (index == viewModel.selectIndex.value) MyColorBeige else MyColorWhite,
                    bottomCardColor = if (index == viewModel.selectIndex.value) MyColorWhite else MyColorBeige,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
    }
}