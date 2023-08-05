package com.example.network.repository

import com.example.network.model.*
import kotlinx.coroutines.flow.Flow

interface AccountBookRepository {

    suspend fun insertNewAccountBookItem(
        item: AccountBookInsertItem,
        isIncome: Boolean
    ): Result<String>

    suspend fun deleteAccountBookItem(id: Int): Result<Unit>

    fun fetchAccountBookInfo(
        dateConfiguration: DateConfiguration
    ): Flow<AccountBookMainInfo>

    suspend fun fetchThisMonthDetail(
        dateConfiguration: DateConfiguration
    ): Result<AccountBookDetailInfo>

    suspend fun insertFixedAccountBookItem(
        item: FixedAccountBook
    ): Result<String>

    fun fetchFixedAccountBook(): Flow<List<FixedAccountBook>>

}