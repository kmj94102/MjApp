package com.example.network.service

import com.example.network.model.AccountBookInsertItem
import com.example.network.model.DateConfiguration
import com.example.network.model.FixedAccountBook
import javax.inject.Inject

class AccountBookClient @Inject constructor(
    private val service: AccountBookService
) {

    suspend fun insertNewAccountBookItem(item: AccountBookInsertItem) =
        runCatching { service.insertAccountBookItem(item) }

    suspend fun deleteAccountBookItem(id: Int) =
        runCatching { service.deleteFixedAccountBookItem(id) }

    suspend fun fetchAccountBookInfo(dateConfiguration: DateConfiguration) =
        runCatching { service.fetchAccountBookInfo(dateConfiguration) }

    suspend fun fetchThisMonthDetail(dateConfiguration: DateConfiguration) =
        runCatching { service.fetchThisMonthDetail(dateConfiguration) }

    suspend fun insertFixedAccountBookItem(item: FixedAccountBook) =
        runCatching { service.insertFixedAccountBookItem(item) }

    suspend fun fetchFixedAccountBook() = runCatching { service.fetchFixedAccountBookItem() }

}