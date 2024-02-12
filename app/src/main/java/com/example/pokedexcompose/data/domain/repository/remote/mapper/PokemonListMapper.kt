package com.example.pokedexcompose.data.domain.repository.remote.mapper

import com.example.pokedexcompose.data.domain.model.Pokemon
import com.example.pokedexcompose.data.domain.repository.remote.response.pokemon.GetListPokemonResponse

object PokemonListMapper {
    fun map(response: GetListPokemonResponse): List<Pokemon> {
        return response.results.mapIndexed { index, pokemonData ->
            mapPokemon(pokemonData, index + 1) // Añadir 1 al índice para obtener el ID del Pokémon
        }
    }

    private fun mapPokemon(pokemonData: Pokemon, id: Int): Pokemon {
        return Pokemon(
            name = pokemonData.name,
            url = pokemonData.url,
            id = id, // Asignar el ID del Pokémon
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png" // Construir la URL de la imagen basada en el ID
        )
    }
}
