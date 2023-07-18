package com.example.network.service

import com.example.network.model.AccountBookItem
import com.example.network.model.DateConfiguration
import javax.inject.Inject

class AccountBookClient @Inject constructor(
    private val service: AccountBookService
) {

    suspend fun insertNewAccountBookItem(
        item: AccountBookItem
    ) = runCatching { service.insertAccountBookItem(item) }

    suspend fun fetchSummaryThisMonth(
        dateConfiguration: DateConfiguration
    ) = runCatching { service.fetchSummaryThisMonth(dateConfiguration) }

    suspend fun fetchThisMonthDetail(
        dateConfiguration: DateConfiguration
    ) = runCatching { service.fetchThisMonthDetail(dateConfiguration) }

}