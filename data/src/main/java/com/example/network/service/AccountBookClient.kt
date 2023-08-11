package com.example.network.service

import com.example.network.model.AccountBookInsertItem
import com.example.network.model.DateConfiguration
import com.example.network.model.FixedAccountBook
import javax.inject.Inject

class AccountBookClient @Inject constructor(
    private val service: AccountBookService
) {
    /** 가계부 조회 **/
    suspend fun fetchAccountBookInfo(dateConfiguration: DateConfiguration) =
        runCatching { service.fetchAccountBookInfo(dateConfiguration) }

    /** 가계부 등록 **/
    suspend fun insertNewAccountBookItem(item: AccountBookInsertItem) =
        runCatching { service.insertAccountBookItem(item) }

    /** 이번달 상세 조회 **/
    suspend fun fetchThisMonthDetail(dateConfiguration: DateConfiguration) =
        runCatching { service.fetchThisMonthDetail(dateConfiguration) }

    /** 고정 내역 추가 **/
    suspend fun insertFixedAccountBookItem(item: FixedAccountBook) =
        runCatching { service.insertFixedAccountBookItem(item) }

    /** 고정 내역 삭제 **/
    suspend fun deleteAccountBookItem(id: Int) =
        runCatching { service.deleteFixedAccountBookItem(id) }

    /** 고정 내역 조회 **/
    suspend fun fetchFixedAccountBook() = runCatching { service.fetchFixedAccountBookItem() }

    /** 즐겨 찾기 조회 **/
    suspend fun fetchFrequentlyAccountBookItems() =
        runCatching { service.fetchFrequentlyAccountBookItems() }

    /** 즐겨 찾기 삭제 **/
    suspend fun deleteFrequentlyAccountBookItem(id: Int) =
        runCatching { service.deleteFrequentlyAccountBookItem(id)}

}