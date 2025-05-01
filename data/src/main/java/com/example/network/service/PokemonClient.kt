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

    /** 포켓몬 리스트 조회 **/
    suspend fun fetchPokemonList(
        name: String,
        skip: Int,
        limit: Int,
        generations: String,
        types: String,
        isCatch: String
    ) = runCatching {
        service.fetchPokemonList(
            name = name,
            skip = skip,
            limit = limit,
            generations = generations,
            types = types,
            isCatch = isCatch
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
    ) = runCatching { service.insertPokemon(pokemonInfo) }

    /** 포켓몬 잡은 상태 업데이트  **/
    suspend fun updatePokemonCatch(
        updatePokemonCatch: UpdatePokemonCatch
    ) = runCatching { service.updatePokemonCatch(updatePokemonCatch) }

    /** 특성 추가 **/
    suspend fun insertCharacteristic(
        characteristicInfo: CharacteristicInfo
    ) = runCatching { service.insertCharacteristic(characteristicInfo) }

    /** 포켓몬 간략한 조회 (진화 추가용) **/
    suspend fun fetchBriefPokemonList(
        search: String
    ) = runCatching { service.fetchBriefPokemonList(search = search) }

    /** 포켓몬 진화 추가 **/
    suspend fun insertPokemonEvolution(
        evolutions: List<PokemonEvolution>
    ) = runCatching { service.insertPokemonEvolution(item = evolutions) }

    /**
     * 포켓몬 기존 spotlight 조회
     * **/
    suspend fun fetchPokemonBeforeSpotlights() =
        runCatching { service.fetchPokemonBeforeSpotlights() }

    /**
     * 포켓몬 spotlight 업데이트
     * **/
    suspend fun updatePokemonSpotlight(
        item: PokemonSpotlightItem
    ) = runCatching { service.updatePokemonSpotlight(item) }

    /** 포켓몬 카운터 조회 **/
    fun fetchPokemonCounter() = runCatching { dao.fetchPokemonCounter() }

    /** 카운터 등록 **/
    suspend fun insertPokemonCounter(entity: PokemonCounterEntity) =
        runCatching { dao.insertPokemonCounter(entity) }

    /** 카운터 업데이트 **/
    suspend fun updateCounter(count: Int, number: String) =
        runCatching { dao.updateCounter(count, number) }

    /** 포켓몬 카운터 히스트리 조회 **/
    fun fetchPokemonCounterHistory() = runCatching { dao.fetchPokemonCounterHistory() }

    /** 카운터 증가폭 업데이트 **/
    suspend fun updateCustomIncrease(customIncrease: Int, number: String) =
        runCatching { dao.updateCustomIncrease(customIncrease, number) }

    /** 카운터 삭제 **/
    suspend fun deletePokemonCounter(index: Int) = runCatching { dao.deleteCounter(index) }

    /** 잡기 상태 업데이트 **/
    suspend fun updateCatch(number: String) = runCatching { dao.updateCatch(number) }

    /** 카운터 복구 업데이트 **/
    suspend fun updateRestore(index: Int) = runCatching { dao.updateRestore(index) }

    /** 포케못 타이틀 도감 별 카운트 **/
    suspend fun fetchGenerationCountList() =
        runCatching { service.fetchGenerationCountList() }

    /** 선택한 타이틀 도감 포켓몬 리스트 **/
    suspend fun fetchGenerationList(index: Int) =
        runCatching { service.fetchGenerationList(index) }

    /** 잡은 상태 업데이트 **/
    suspend fun updateGenerationIsCatch(item: GenerationUpdateParam) =
        runCatching { service.updateGenerationIsCatch(item) }

}