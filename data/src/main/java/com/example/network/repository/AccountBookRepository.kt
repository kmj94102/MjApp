package com.example.network.repository

import com.example.network.model.AccountBookItem

interface AccountBookRepository {

    suspend fun insertNewAccountBookItem(item: AccountBookItem, isIncome: Boolean): Result<String>

}