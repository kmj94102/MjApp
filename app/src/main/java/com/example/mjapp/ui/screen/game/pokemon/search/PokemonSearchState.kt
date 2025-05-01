package com.example.mjapp.ui.screen.game.pokemon.search

import android.os.Parcelable
import androidx.core.text.isDigitsOnly
import com.example.mjapp.ui.screen.navigation.NavScreen2
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonSearchItem(
    val name: String = "",
    val type: List<String> = emptyList(),
    val generation: List<String> = emptyList(),
    val registrationStatus: String = NavScreen2.PokemonSearch.ALL
) : Parcelable {
    fun getFilterItems(): List<String> {
        val list = mutableListOf<String>()
        if (name.isNotEmpty()) list.add("%$name%")

        list.addAll(type)
        list.addAll(generationMapping())

        if (registrationStatus != NavScreen2.PokemonSearch.ALL) {
            list.add("안 잡은 포켓몬 만")
        }

        return list
    }

    fun generationMapping() =
        generation.map {
            if (it.isDigitsOnly() && it.toInt() < 20) {
                "${it}세대"
            } else if (it == "99") {
                "레전드 아르세우스"
            } else it
        }

}

fun NavScreen2.PokemonSearch.toPokemonSearchItem() = PokemonSearchItem(
    name = this.name,
    type = this.types,
    generation = this.generations,
    registrationStatus = this.registrations
)