package com.example.network.repository

import com.example.network.model.*
import kotlinx.coroutines.flow.Flow

interface AccountBookRepository {
    /** 가계부 조회 **/
    fun fetchAccountBookInfo(
        dateConfiguration: DateConfiguration
    ): Flow<AccountBookMainInfo>

    /** 가계부 등록 **/
    suspend fun insertNewAccountBookItem(
        item: AccountBookInsertItem
    ): Result<String>

    /** 이번달 상세 조회 **/
    fun fetchThisMonthDetail(
        dateConfiguration: DateConfiguration
    ): Flow<AccountBookDetailInfo>

    /** 고정 내역 추가 **/
    suspend fun insertFixedAccountBookItem(
        item: FixedAccountBook
    ): Result<String>

    /** 고정 내역 삭제 **/
    suspend fun deleteAccountBookItem(id: Int): Result<Unit>

    /** 고정 내역 조회 **/
    fun fetchFixedAccountBook(): Flow<List<FixedAccountBook>>

    /** 즐겨 찾기 조회 **/
    fun fetchFrequentlyAccountBookItems(): Flow<List<FixedItem>>

    /** 즐겨 찾기 삭제 **/
    suspend fun deleteFrequentlyAccountBookItem(id: Int): Result<Unit>

}