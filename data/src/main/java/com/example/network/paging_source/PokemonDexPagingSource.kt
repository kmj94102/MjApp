package com.example.network.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.model.PokemonSummary
import com.example.network.service.PokemonService

class PokemonDexPagingSource(
    private val pokemonService: PokemonService,
    private val name: String
): PagingSource<Int, PokemonSummary>() {
    private val limit = 100
    private val initPage = 0

    override fun getRefreshKey(state: PagingState<Int, PokemonSummary>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonSummary> {

        return try {
            val page = params.key ?: initPage
            val response = pokemonService.fetchPokemonList(name = name, skip = page * limit, limit = limit)
            LoadResult.Page(
                data = response.list.mapNotNull { it.toPokemonSummary() },
                prevKey = if (page == initPage) null else page - 1,
                nextKey = if (response.list.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}