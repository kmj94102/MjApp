package com.example.network.repository

import com.example.network.database.dao.ElswordDao
import com.example.network.database.entity.ElswordCounterEntity
import com.example.network.model.ElswordCounter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ElswordRepositoryImpl @Inject constructor(
    private val dao: ElswordDao
): ElswordRepository {
    override suspend fun insertCounter(
        name: String,
        max: Int
    ) {
        dao.insertCounter(
            ElswordCounterEntity(
                name = name,
                max = max,
                complete = ""
            )
        )
    }

    override fun fetchCounterTitleList(): Flow<List<String>> =
        dao.fetchCounterTitleList()

    override fun fetchCounterList() =
        dao.fetchCounterList()
            .map { ElswordCounter.fromCounterEntityList(it) }

    override suspend fun fetchCounter(id: Int): ElswordCounter =
        ElswordCounter.fromCounterEntity(dao.fetchCounter(id))

    override suspend fun deleteCounter(id: Int) =
        dao.deleteCounter(id)

}