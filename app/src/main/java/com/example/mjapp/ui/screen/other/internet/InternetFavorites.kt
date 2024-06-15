package com.example.mjapp.ui.screen.other.internet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.rememberWebView
import com.example.mjapp.ui.dialog.InsertFavoriteDeleteDialog
import com.example.mjapp.ui.dialog.InsertFavoriteDialog
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B
import com.example.network.model.InternetFavorite
import kotlinx.coroutines.flow.collectLatest

@Composable
fun InternetFavoritesScreen(
    onBackClick: () -> Unit,
    viewModel: InternetFavoritesViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var uiState by remember { mutableStateOf(InternetUiState()) }

    HighMediumLowContainer(
        status = status,
        heightContent = {
            InternetFavoritesHeight(
                onBackClick = onBackClick,
                addFavorite = {
                    uiState = uiState.copy(isInsertDialogShow = true)
                }
            )
        },
        mediumContent = {
            InternetFavoritesMedium(
                viewModel = viewModel,
                setInitAddress = {
                    uiState = uiState.copy(isInsertDialogShow = true, address = it)
                },
                onDelete = {
                    uiState = uiState.copy(isDeleteDialogShow = true, selectItem = it)
                }
            )
        },
        lowContent = {
            InternetFavoritesLow(
                viewModel = viewModel,
                onDelete = {
                    uiState = uiState.copy(isDeleteDialogShow = true, selectItem = it)
                }
            )
        },
        paddingValues = PaddingValues(top = 22.dp, start = 0.dp, end = 0.dp, bottom = 10.dp),
        reload = viewModel::fetchFavorites
    )

    InsertFavoriteDialog(
        isShow = uiState.isInsertDialogShow,
        onDismiss = {
            uiState = uiState.copy(isInsertDialogShow = false, address = "")
        },
        address = uiState.address,
        onInsert = viewModel::insertFavorite,
        updateAddress = { uiState = uiState.copy(address = it) }
    )

    InsertFavoriteDeleteDialog(
        isShow = uiState.isDeleteDialogShow,
        favorite = uiState.selectItem,
        onDismiss = { uiState = uiState.copy(isDeleteDialogShow = false) },
        onDelete = {
            viewModel.deleteItem(uiState.selectItem.id)
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
            boxColor = MyColorPurple,
            iconRes = R.drawable.ic_plus,
            onClick = addFavorite
        )
    }
}

@Composable
fun InternetFavoritesMedium(
    viewModel: InternetFavoritesViewModel,
    setInitAddress: (String) -> Unit,
    onDelete: (InternetFavorite) -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }
    val webView = rememberWebView {
        viewModel.updateUrl(it)
        isFavorite = viewModel.isFavorite()
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MyColorBlack),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        Column {
            AndroidView(
                modifier = Modifier.weight(1f),
                factory = { webView },
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MyColorBlack)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColorBeige)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .nonRippleClickable {
                            if (webView.canGoBack()) {
                                webView.goBack()
                            }
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = null,
                    modifier = Modifier.nonRippleClickable {
                        if (webView.canGoForward()) {
                            webView.goForward()
                        }
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = null,
                    modifier = Modifier.nonRippleClickable(viewModel::goToHome)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_reload),
                    contentDescription = null,
                    modifier = Modifier.nonRippleClickable { webView.reload() }
                )
                Image(
                    painter = painterResource(
                        id = if (isFavorite) {
                            R.drawable.ic_star_fill
                        } else {
                            R.drawable.ic_star
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.nonRippleClickable {
                        if (isFavorite) {
                            viewModel.state.value.getSelectItem()?.let(onDelete)
                        } else {
                            webView.url?.let(setInitAddress)
                        }
                    }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadState.collectLatest {
            if (webView.url != it) {
                webView.loadUrl(it)
            }
        }
    }
}

@Composable
fun InternetFavoritesLow(
    viewModel: InternetFavoritesViewModel,
    onDelete: (InternetFavorite) -> Unit
) {
    var isShow by remember { mutableStateOf(false) }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(viewModel.state.value.list) { index, internetFavorite ->
            val topCardColor =
                if (index == viewModel.state.value.selectIndex) MyColorBeige else MyColorWhite
            val bottomCardColor =
                if (index == viewModel.state.value.selectIndex) MyColorWhite else MyColorBeige

            Box(
                modifier = Modifier
                    .nonRippleClickable { viewModel.updateSelectIndex(index) }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                viewModel.updateSelectIndex(index)
                            },
                            onLongPress = {
                                onDelete(internetFavorite)
                                isShow = true
                            }
                        )
                    }
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = bottomCardColor,
                    ),
                    border = BorderStroke(1.dp, MyColorBlack),
                    modifier = Modifier.padding(top = 3.dp, start = 3.dp)
                ) {
                    Text(
                        text = internetFavorite.name,
                        style = textStyle16B().copy(fontSize = 14.sp),
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = topCardColor
                    ),
                    border = BorderStroke(1.dp, MyColorBlack),
                    modifier = Modifier.padding(end = 3.dp, bottom = 3.dp)
                ) {
                    Text(
                        text = internetFavorite.name,
                        style = textStyle16B().copy(fontSize = 14.sp),
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}