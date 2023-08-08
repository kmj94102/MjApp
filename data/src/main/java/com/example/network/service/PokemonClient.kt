package com.example.network.service

import com.example.network.database.dao.PokemonDao
import com.example.network.database.entity.PokemonCounterEntity
import com.example.network.model.*
import javax.inject.Inject

class PokemonClient @Inject constructor(
    private val service: PokemonService,
    private val dao: PokemonDao
) {
    /** 포켓몬 번호로 정보 조회 **/
    suspend fun fetchPokemonWithNumber(number: String) = runCatching {
        service.fetchPokemonWithNumber(number)
    }

//    fun fetchPokemonList(name: String) = Pager(
//        config = PagingConfig(
//            pageSize = 100
//        ),
//        pagingSourceFactory = {
//            PokemonDexPagingSource(pokemonService = service, name = name)
//        }
//    ).flow
    /** 포켓몬 리스트 조회 **/
    suspend fun fetchPokemonList(
        name: String,
        skip: Int,
        limit: Int
    ) = runCatching {
        service.fetchPokemonList(
            name = name,
            skip = skip,
            limit = limit
        )
    }

    /** 포켓몬 상세 조회 **/
    suspend fun fetchPokemonDetailInfo(
        number: String
    ) = runCatching {
        service.fetchPokemonDetail(number)
    }

    /** 포켓몬 추가 **/
    suspend fun insertPokemon(
        pokemonInfo: PokemonInfo
    ) = service.insertPokemon(pokemonInfo)

    /** 포켓몬 잡은 상태 업데이트  **/
    suspend fun updatePokemonCatch(
        updatePokemonCatch: UpdatePokemonCatch
    ) = runCatching { service.updatePokemonCatch(updatePokemonCatch) }

    /** 특성 추가 **/
    suspend fun insertCharacteristic(
        characteristicInfo: CharacteristicInfo
    ) = service.insertCharacteristic(characteristicInfo)

    /** 포켓몬 간략한 조회 (진화 추가용) **/
    suspend fun fetchBriefPokemonList(
        search: String
    ) = runCatching { service.fetchBriefPokemonList(search = search) }

    /** 포켓몬 진화 추가 **/
    suspend fun insertPokemonEvolution(
        evolutions: List<PokemonEvolution>
    ) = service.insertPokemonEvolution(item = evolutions)

    /**
     * 포켓몬 기존 spotlight 조회
     * **/
    suspend fun fetchPokemonBeforeSpotlights() =
        service.fetchPokemonBeforeSpotlights()

    /**
     * 포켓몬 spotlight 업데이트
     * **/
    suspend fun updatePokemonSpotlight(
        item: PokemonSpotlightItem
    ) = service.updatePokemonSpotlight(item)

    /** 포켓몬 카운터 조회 **/
    fun fetchPokemonCounter() = dao.fetchPokemonCounter()

    suspend fun insertPokemonCounter(entity: PokemonCounterEntity) =
        dao.insertPokemonCounter(entity)

    suspend fun updateCounter(count: Int, number: String) =
        dao.updateCounter(count, number)

    suspend fun updateCustomIncrease(customIncrease: Int, number: String) =
        dao.updateCustomIncrease(customIncrease, number)

    suspend fun deletePokemonCounter(number: String) =
        dao.deleteCounter(number)

    suspend fun updateCatch(number: String) =
        dao.updateCatch(number)

}