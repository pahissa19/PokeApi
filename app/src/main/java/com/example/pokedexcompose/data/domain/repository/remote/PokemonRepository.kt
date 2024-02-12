package com.example.pokedexcompose.data.domain.repository.remote

import com.example.pokedexcompose.data.domain.model.Ability
import com.example.pokedexcompose.data.domain.model.Pokemon
import com.example.pokedexcompose.data.domain.model.PokemonDetail
import com.example.pokedexcompose.data.domain.repository.remote.mapper.AbilityMapper
import com.example.pokedexcompose.data.domain.repository.remote.mapper.PokemonDetailMapper
import com.example.pokedexcompose.data.domain.repository.remote.mapper.PokemonListMapper
import com.example.pokedexcompose.data.domain.repository.remote.response.PokemonApiService

class PokemonRepository(private val apiService: PokemonApiService) {
    suspend fun getListPokemon(limit: Int, offset: Int): List<Pokemon> {
        val response = apiService.getListPokemon(limit, offset)
        if (response.isSuccessful) {
            return PokemonListMapper.map(response.body()!!)
        } else {
            throw Exception("Error al obtener la lista de Pokémon: ${response.message()}")
        }
    }

    suspend fun getPokemonDetail(name: String): PokemonDetail {
        val response = apiService.getPokemonDetail(name)
        if (response.isSuccessful) {
            return PokemonDetailMapper.map(response.body()!!)
        } else {
            throw Exception("Error al obtener el detalle del Pokémon: ${response.message()}")
        }
    }

    suspend fun getAbility(id: Int): Ability {
        val response = apiService.getAbility(id)
        if (response.isSuccessful) {
            return AbilityMapper.map(response.body()!!)
        } else {
            throw Exception("Error al obtener la habilidad: ${response.message()}")
        }
    }
}
