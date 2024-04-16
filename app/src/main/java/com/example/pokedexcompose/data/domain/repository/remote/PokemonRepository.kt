package com.example.pokedexcompose.data.domain.repository.remote

import com.example.pokedexcompose.data.domain.model.Ability
import com.example.pokedexcompose.data.domain.model.Pokemon
import com.example.pokedexcompose.data.domain.model.PokemonDetail
import com.example.pokedexcompose.data.domain.repository.remote.mapper.PokemonDetailMapper
import com.example.pokedexcompose.data.domain.repository.remote.mapper.PokemonListMapper
import com.example.pokedexcompose.data.domain.repository.remote.response.PokemonApiService

class PokemonRepository(private val apiService: PokemonApiService) {
    suspend fun getListPokemon(limit: Int, offset: Int): List<Pokemon> {
        val response = apiService.getListPokemon(limit, offset)
        return if (response.isSuccessful) {
            PokemonListMapper.map(response.body()!!)
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

    suspend fun getPokemonAbilities(pokemonName: String): List<Ability> {
        val response = apiService.getPokemonDetail(pokemonName)
        if (response.isSuccessful) {
            return response.body()?.abilities?.map { Ability(it.ability.name ?: "Unknown") } ?: emptyList()
        } else {
            throw Exception("Error al obtener las habilidades del Pokémon: ${response.message()}")
        }
    }
}
