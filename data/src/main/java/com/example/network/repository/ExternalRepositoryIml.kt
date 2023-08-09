package com.example.network.repository

import com.example.network.model.PokemonInfo
import com.example.network.model.mapper
import com.example.network.service.ExternalClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExternalRepositoryIml @Inject constructor(
    private val client: ExternalClient
) : ExternalRepository {

    /** 포켓몬 정보 조회 **/
    override fun pokemonInfo(index: Int) = flow {
        val detail = client.fetchPokemonDetail(index).getOrThrow().mapper()
        val species = client.fetchPokemonSpecies(index).getOrNull()?.mapper()

        emit(
            PokemonInfo(
                number = index.toString().padStart(4, '0'),
                name = species?.name ?: "",
                status = detail.status,
                classification = species?.classification ?: "",
                characteristic = detail.abilities.reduce { acc, s -> "$acc,$s" },
                attribute = detail.typeList.reduce { acc, s -> "$acc,$s" },
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$index.png",
                shinyImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/$index.png",
                spotlight = "https://serious-cook-e42.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fdc9870fc-788a-4dde-a8a4-9dae7330aa9b%2F0.png?id=9a816e7a-29e7-4fe4-8278-d7ac1ce410ce&table=block&spaceId=0bee4841-fd70-4125-83b9-2f0458435e3f&width=260&userId=&cache=v2",
                description = species?.description ?: "",
                generation = 0
            )
        )
    }

}