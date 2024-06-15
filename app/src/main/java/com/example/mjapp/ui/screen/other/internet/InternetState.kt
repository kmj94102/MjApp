package com.example.mjapp.ui.screen.other.internet

import com.example.network.model.InternetFavorite

data class InternetState(
    private val homeUrl: String = "https://m.naver.com/",
    val list: List<InternetFavorite> = listOf(),
    val selectIndex: Int = 0
) {
    fun getSelectItem(): InternetFavorite? = runCatching {
        list[selectIndex]
    }.getOrElse { null }

    fun getSelectAddress() = getSelectItem()?.address ?: homeUrl

}

data class InternetUiState(
    val isInsertDialogShow: Boolean = false,
    val isDeleteDialogShow: Boolean = false,
    val address: String = "",
    val selectItem: InternetFavorite = InternetFavorite.crate()
)