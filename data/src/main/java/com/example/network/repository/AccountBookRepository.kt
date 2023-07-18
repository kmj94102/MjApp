package com.example.network.repository

import com.example.network.model.AccountBookDetailInfo
import com.example.network.model.AccountBookItem
import com.example.network.model.DateConfiguration
import com.example.network.model.SummaryAccountBookThisMonthInfo

interface AccountBookRepository {

    suspend fun insertNewAccountBookItem(
        item: AccountBookItem,
        isIncome: Boolean
    ): Result<String>

    suspend fun fetchSummaryThisMonth(
        dateConfiguration: DateConfiguration
    ): Result<SummaryAccountBookThisMonthInfo>

    suspend fun fetchThisMonthDetail(
        dateConfiguration: DateConfiguration
    ): Result<AccountBookDetailInfo>

}