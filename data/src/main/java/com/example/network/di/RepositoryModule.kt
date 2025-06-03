package com.example.network.di

import com.example.network.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindExternalRepository(
        externalRepositoryIml: ExternalRepositoryIml
    ): ExternalRepository

    @Binds
    fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    fun bindPersonaRepository(
        personaRepositoryImpl: PersonaRepositoryImpl
    ): PersonaRepository

    @Binds
    fun bindElswordRepository(
        elswordRepositoryImpl: ElswordRepositoryImpl
    ): ElswordRepository

    @Binds
    fun bindCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository

    @Binds
    fun bindAccountBookRepository(
        accountBookRepositoryImpl: AccountBookRepositoryImpl
    ): AccountBookRepository

    @Binds
    fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    fun bindInternetRepository(
        internetRepositoryImpl: InternetRepositoryImpl
    ): InternetRepository

    @Binds
    fun bindVocabularyRepository(
        vocabularyRepositoryImpl: VocabularyRepositoryImpl
    ): VocabularyRepository

}