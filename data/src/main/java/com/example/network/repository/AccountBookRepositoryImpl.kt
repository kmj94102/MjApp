package com.example.network.repository

import com.example.network.model.AccountBookInsertItem
import com.example.network.model.DateConfiguration
import com.example.network.model.FixedAccountBook
import com.example.network.model.getFailureThrow
import com.example.network.model.printStackTrace
import com.example.network.service.AccountBookClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountBookRepositoryImpl @Inject constructor(
    private val client: AccountBookClient
) : AccountBookRepository {
    /** 가계부 조회 **/
    override fun fetchAccountBookInfo(dateConfiguration: DateConfiguration) = flow {
        client.fetchAccountBookInfo(dateConfiguration)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 가계부 등록 **/
    override suspend fun insertNewAccountBookItem(
        item: AccountBookInsertItem,
        isIncome: Boolean
    ) = client.insertNewAccountBookItem(item.uploadFormat(isIncome)).printStackTrace()

    /** 이번달 상세 조회 **/
    override fun fetchThisMonthDetail(dateConfiguration: DateConfiguration) = flow {
        client
            .fetchThisMonthDetail(dateConfiguration)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 고정 내역 등록 **/
    override suspend fun insertFixedAccountBookItem(item: FixedAccountBook) = runCatching {
        client.insertFixedAccountBookItem(item.checkValidity()).getOrThrow()
    }

    /** 고정 내역 삭제 **/
    override suspend fun deleteAccountBookItem(id: Int) =
        client.deleteAccountBookItem(id).printStackTrace()

    /** 거정 내역 조회 **/
    override fun fetchFixedAccountBook() = flow {
        client
            .fetchFixedAccountBook()
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 즐겨 찾기 아이템 조회 **/
    override fun fetchFrequentlyAccountBookItems() = flow {
        client
            .fetchFrequentlyAccountBookItems()
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 즐겨 찾기 삭제 **/
    override suspend fun deleteFrequentlyAccountBookItem(id: Int) =
        client.deleteFrequentlyAccountBookItem(id).printStackTrace()

}