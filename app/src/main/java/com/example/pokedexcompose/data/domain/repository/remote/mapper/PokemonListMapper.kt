package com.example.pokedexcompose.data.domain.repository.remote.mapper

import com.example.pokedexcompose.data.domain.model.Pokemon
import com.example.pokedexcompose.data.domain.repository.remote.response.pokemon.GetListPokemonResponse

object PokemonListMapper {
    fun map(response: GetListPokemonResponse): List<Pokemon> {
        return response.results.mapIndexed { index, pokemonData ->
            mapPokemon(pokemonData, index + 1)
        }
    }

    private fun mapPokemon(pokemonData: Pokemon, id: Int): Pokemon {
        return Pokemon(
            name = pokemonData.name,
            url = pokemonData.url,
            id = id,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        )
    }
}
