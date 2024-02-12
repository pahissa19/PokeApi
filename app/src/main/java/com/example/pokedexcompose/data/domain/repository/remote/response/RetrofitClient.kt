package com.example.pokedexcompose.data.domain.repository.remote.response

import com.example.pokedexcompose.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: PokemonApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(PokemonApiService::class.java)
    }
}