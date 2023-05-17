package com.example.network.repository

import com.example.network.model.PokemonInfo
import com.example.network.model.mapper
import com.example.network.service.ExternalClient
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ExternalRepositoryIml @Inject constructor(
    private val client: ExternalClient
) : ExternalRepository {

    override fun pokemonInfo(index: Int) = flow {
        val detail = client.fetchPokemonDetail(index).mapper()
        val species = try {
            client.fetchPokemonSpecies(index).mapper()
        } catch (e: Exception) {
            null
        }

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

    override fun fetchHolidays(
        year: String,
        month: String
    ) = flow {
        val from = convertToRFC5545("$year.$month.01 00:00")
        val to = convertToRFC5545(getLastDayOfMonth(year, month))

        emit(
            client.fetchHolidays(from, to).map { it.toHolidayInfo() }
        )
    }

    private fun convertToRFC5545(dateTimeString: String, isAllDay: Boolean = false): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
            if (isAllDay) {
                inputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            }

            val outputFormat = SimpleDateFormat(
                if (isAllDay) "yyyy-MM-dd'T'00:00:00'Z'" else "yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.getDefault()
            )
            outputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val dateTime = inputFormat.parse(dateTimeString) ?: return ""

            return outputFormat.format(dateTime)
        } catch (e: Exception) {
            return ""
        }
    }

    private fun getLastDayOfMonth(year: String, month: String): String {
        val calendar = Calendar.getInstance()
        SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).parse("$year.$month.01")?.let {
            calendar.time = it
        }
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return "$year.$month.${lastDay.toString().padStart(2, '0')} 23:59"
    }
}