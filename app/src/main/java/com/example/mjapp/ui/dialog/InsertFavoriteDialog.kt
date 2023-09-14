package com.example.mjapp.ui.dialog

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.DoubleCardText
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.network.model.InternetFavorite

@Composable
fun InsertFavoriteDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onInsert: (InternetFavorite) -> Unit
) {
    var favorite by remember { mutableStateOf(InternetFavorite.crate()) }

    BaseDialog(
        isShow = isShow,
        title = "사이트 등록",
        onDismiss = onDismiss,
        bodyContents = {
            Spacer(modifier = Modifier.height(15.dp))
            DoubleCardTextField(
                value = favorite.name,
                onTextChange = {
                    favorite = favorite.copy(name = it)
                },
                hint = "이름",
                bottomCardColor = MyColorBeige,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 17.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            DoubleCardTextField(
                value = favorite.address,
                onTextChange = {
                    favorite = favorite.copy(address = it)
                },
                hint = "주소",
                bottomCardColor = MyColorBeige,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 17.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
        },
        bottomButtonContents = {
            DoubleCardText(
                onClick = {
                    onInsert(favorite)
                    onDismiss()
                },
                text = "등록하기",
                topCardColor = MyColorBeige,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}