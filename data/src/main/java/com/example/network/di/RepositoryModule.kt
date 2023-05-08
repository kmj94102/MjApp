package com.example.network.di

import com.example.network.repository.ExternalRepository
import com.example.network.repository.ExternalRepositoryIml
import com.example.network.repository.PokemonRepository
import com.example.network.repository.PokemonRepositoryImpl
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
}