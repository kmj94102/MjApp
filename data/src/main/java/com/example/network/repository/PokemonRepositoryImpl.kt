package com.example.network.repository

import com.example.network.model.BriefPokemonItem
import com.example.network.model.CharacteristicInfo
import com.example.network.model.GenerationUpdateParam
import com.example.network.model.PokemonCounter
import com.example.network.model.PokemonDetailInfo
import com.example.network.model.PokemonEvolution
import com.example.network.model.PokemonInfo
import com.example.network.model.PokemonSpotlightItem
import com.example.network.model.UpdatePokemonCatch
import com.example.network.model.getFailureThrow
import com.example.network.model.printStackTrace
import com.example.network.service.ExternalClient
import com.example.network.service.PokemonClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val client: PokemonClient,
    private val externalClient: ExternalClient,
) : PokemonRepository {
    /** 포켓몬 추가 **/
    override suspend fun insertPokemon(pokemonInfo: PokemonInfo): String {
        val originCharacteristics = pokemonInfo.characteristic.split(",")
        val characteristics = mutableListOf<CharacteristicInfo>()
        val result = ""

        originCharacteristics.forEach {
            externalClient.fetchAbility(it).getOrNull()?.mapper()?.let { info ->
                characteristics.add(info)
            }
        }

        characteristics.forEach {
            result.plus(client.insertCharacteristic(it)).plus("\n")
        }
        result.plus(
            client.insertPokemon(
                pokemonInfo.copy(
                    characteristic = characteristics.map { it.name }.reduce { acc, s -> "$acc,$s" }
                )
            )
        )
        return result
    }

    /** 포켓몬 도감 조회 **/
    override fun fetchPokemonList(
        name: String,
        skip: Int,
        limit: Int,
        generations: String,
        types: String,
        isCatch: String
    ) = flow {
        client.fetchPokemonList(name, skip * limit, limit, generations, types, isCatch)
            .onSuccess {
                val isMoreData = it.getIsMoreData(skip * limit)
                val list = it.getMappingList()
                emit(Pair(isMoreData, list))
            }
            .getFailureThrow()
    }

    /** 포켓몬 상세 조회 **/
    override fun fetchPokemonDetailInfo(number: String) = flow {
        client.fetchPokemonDetailInfo(number)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 포켓몬 잡은 상태 업데이트 **/
    override fun updatePokemonCatch(
        pokemonCatch: UpdatePokemonCatch
    ) = flow {
        client.updatePokemonCatch(pokemonCatch)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 포켓몬 카운터 추가 **/
    override fun insertPokemonCounter(pokemonDetailInfo: PokemonDetailInfo) = flow {
        client
            .insertPokemonCounter(pokemonDetailInfo.toPokemonCounterEntity())
            .onSuccess { emit(it) }
            .getFailureThrow()
    }


    override fun insertPokemonCounter(number: String) = flow {
        client
            .fetchPokemonWithNumber(number)
            .onSuccess {
                client.insertPokemonCounter(it.toPokemonCounterEntity())
                    .onSuccess { emit(it) }
                    .getOrNull()
            }
            .getFailureThrow()
    }

    /** 포켓몬 카운터 조회 **/
    override fun fetchPokemonCounter(): Flow<List<PokemonCounter>> =
        client
            .fetchPokemonCounter()
            .getOrThrow()

    /** 포켓몬 카운터 삭제 **/
    override suspend fun deletePokemonCounter(number: Int) {
        client.deletePokemonCounter(number).printStackTrace()
    }

    /** 포켓몬 카운터 히스토리 조회 **/
    override fun fetchPokemonCounterHistory(): Flow<List<PokemonCounter>> =
        client
            .fetchPokemonCounterHistory()
            .getOrThrow()

    /** 카운터 업데이트 **/
    override suspend fun updateCounter(count: Int, number: String) {
        client.updateCounter(count, number).printStackTrace()
    }

    /** 증가 폭 업데이트 **/
    override suspend fun updateCustomIncrease(customIncrease: Int, number: String) {
        client.updateCustomIncrease(customIncrease, number).printStackTrace()
    }

    /** 카운터 복구 업데이트 **/
    override suspend fun updateRestore(index: Int, number: String) {
        client.updateRestore(index).printStackTrace()
        client.updatePokemonCatch(UpdatePokemonCatch(number, false)).printStackTrace()
    }

    /** 잡기 상태 업데이트 **/
    override suspend fun updateCatch(number: String) {
        client.updateCatch(number).printStackTrace()
    }

    /** 포켓몬 간략한 조회 (진화 추가용) **/
    override fun fetchBriefPokemonList(search: String): Flow<List<BriefPokemonItem>> = flow {
        client
            .fetchBriefPokemonList(search)
            .onSuccess { list ->
                emit(list.mapNotNull { it.toBriefPokemonItem() })
            }
            .getFailureThrow()
    }

    /** 포켓몬 진화 추가 **/
    override suspend fun insertPokemonEvolution(evolutions: List<PokemonEvolution>) =
        client.insertPokemonEvolution(evolutions).getFailureThrow()

    /** 포켓몬 기존 spotlight 조회 **/
    override fun fetchPokemonBeforeSpotlights() = flow {
        client
            .fetchPokemonBeforeSpotlights()
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 포켓몬 spotlight 업데이트 **/
    override suspend fun updatePokemonSpotlight(item: PokemonSpotlightItem) =
        client
            .updatePokemonSpotlight(item)
            .printStackTrace()
            .getOrElse { "업데이트 실패" }

    override fun fetchGenerationCountList() = flow {
        client
            .fetchGenerationCountList()
            .onSuccess { emit(it) }
            .getFailureThrow()
    }


    override fun fetchGenerationList(index: Int) = flow {
        client
            .fetchGenerationList(index)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    override fun updateGenerationIsCatch(item: GenerationUpdateParam) = flow {
        client
            .updateGenerationIsCatch(item)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }
}