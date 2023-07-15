package com.example.network.service

import com.example.network.model.AccountBookItem
import javax.inject.Inject

class AccountBookClient @Inject constructor(
    private val service: AccountBookService
) {

    suspend fun insertNewAccountBookItem(
        item: AccountBookItem
    ) = runCatching { service.insertAccountBookItem(item) }

}