package com.example.mjapp.ui.dialog

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.DoubleCardText
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.network.model.InternetFavorite

@Composable
fun InsertFavoriteDeleteDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    favorite: InternetFavorite,
    onDelete: (InternetFavorite) -> Unit
) {
    BaseDialog(
        isShow = isShow,
        title = "사이트 삭제",
        onDismiss = onDismiss,
        bodyContents = {
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "'${favorite.name}'",
                style = textStyle16B(
                    color = MyColorPurple,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "해당 사이트를 삭제하시겠습니까?",
                style = textStyle16(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(25.dp))
        },
        bottomContents = {
            DoubleCardText(
                onClick = {
                    onDelete(favorite)
                    onDismiss()
                },
                text = "삭제하기",
                topCardColor = MyColorRed,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}