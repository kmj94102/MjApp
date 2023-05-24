package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGsonConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named("external")
    fun provideExternalRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideExternalService(
        @Named("external") retrofit: Retrofit
    ): ExternalService =
        retrofit.create(ExternalService::class.java)

    @Provides
    @Singleton
    fun provideExternalClient(
        externalService: ExternalService
    ): ExternalClient = ExternalClient(externalService)

    @Provides
    @Singleton
    @Named("internal")
    fun provideInternalRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://port-0-mj-api-e9btb72blgnd5rgr.sel3.cloudtype.app/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun providePokemonService(
        @Named("internal") retrofit: Retrofit
    ): PokemonService =
        retrofit.create(PokemonService::class.java)

    @Provides
    @Singleton
    fun providePokemonClient(
        pokemonService: PokemonService
    ): PokemonClient = PokemonClient(pokemonService)

    @Provides
    @Singleton
    fun provideCalendarService(
        @Named("internal") retrofit: Retrofit
    ): CalendarService =
        retrofit.create(CalendarService::class.java)

    @Provides
    @Singleton
    fun provideCalendarClient(
        calendarService: CalendarService
    ): CalendarClient = CalendarClient(calendarService)

    @Provides
    @Singleton
    fun provideElswordService(
        @Named("internal") retrofit: Retrofit
    ): ElswordService =
        retrofit.create(ElswordService::class.java)

    @Provides
    @Singleton
    fun provideElswordClient(
        elswordService: ElswordService
    ): ElswordClient = ElswordClient(elswordService)
}