package com.example.network.repository

import com.example.network.model.*

interface AccountBookRepository {

    suspend fun insertNewAccountBookItem(
        item: AccountBookInsertItem,
        isIncome: Boolean
    ): Result<String>

    suspend fun fetchSummaryThisMonth(
        dateConfiguration: DateConfiguration
    ): Result<SummaryAccountBookThisMonthInfo>

    suspend fun fetchThisMonthDetail(
        dateConfiguration: DateConfiguration
    ): Result<AccountBookDetailInfo>

}