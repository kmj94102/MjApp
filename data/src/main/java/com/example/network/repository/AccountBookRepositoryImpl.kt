package com.example.network.repository

import com.example.network.model.AccountBookItem
import com.example.network.model.DateConfiguration
import com.example.network.service.AccountBookClient
import javax.inject.Inject

class AccountBookRepositoryImpl @Inject constructor(
    private val client: AccountBookClient
) : AccountBookRepository {

    override suspend fun insertNewAccountBookItem(
        item: AccountBookItem,
        isIncome: Boolean
    ) = client.insertNewAccountBookItem(item.uploadFormat(isIncome))

    override suspend fun fetchSummaryThisMonth(dateConfiguration: DateConfiguration) =
        client.fetchSummaryThisMonth(dateConfiguration)

    override suspend fun fetchThisMonthDetail(dateConfiguration: DateConfiguration) =
        client.fetchThisMonthDetail(dateConfiguration).onSuccess { }

}