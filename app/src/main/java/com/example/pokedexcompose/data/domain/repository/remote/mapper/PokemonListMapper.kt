package com.example.pokedexcompose.data.domain.repository.remote.mapper

import com.example.pokedexcompose.data.domain.model.Pokemon
import com.example.pokedexcompose.data.domain.repository.remote.response.GetListPokemonResponse

object PokemonListMapper {
    fun map(response: GetListPokemonResponse): List<Pokemon> {
        return response.results.map { mapPokemon(it) }
    }

    private fun mapPokemon(pokemonData: Pokemon): Pokemon {
        return Pokemon(
            name = pokemonData.name,
            url = pokemonData.url
        )
    }
}