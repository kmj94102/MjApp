package com.example.network.repository

import com.example.network.model.AccountBookInsertItem
import com.example.network.model.DateConfiguration
import com.example.network.model.getFailureThrow
import com.example.network.service.AccountBookClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountBookRepositoryImpl @Inject constructor(
    private val client: AccountBookClient
) : AccountBookRepository {

    override suspend fun insertNewAccountBookItem(
        item: AccountBookInsertItem,
        isIncome: Boolean
    ) = client.insertNewAccountBookItem(item.uploadFormat(isIncome))

    override fun fetchAccountBookInfo(dateConfiguration: DateConfiguration) = flow {
        client.fetchAccountBookInfo(dateConfiguration)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    override suspend fun fetchThisMonthDetail(dateConfiguration: DateConfiguration) =
        client.fetchThisMonthDetail(dateConfiguration).onSuccess { }

}