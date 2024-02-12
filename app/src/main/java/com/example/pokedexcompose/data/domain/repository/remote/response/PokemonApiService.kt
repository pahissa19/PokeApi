package com.example.pokedexcompose.data.domain.repository.remote.response

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<GetListPokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): Response<GetPokemonDetailResponse>

    @GET("ability/{id}")
    suspend fun getAbility(@Path("id") id: Int): Response<AbilityResponse>
}