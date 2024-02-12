package com.example.pokedexcompose.data.domain.repository.remote.mapper

import com.example.pokedexcompose.data.domain.model.Ability
import com.example.pokedexcompose.data.domain.model.PokemonDetail
import com.example.pokedexcompose.data.domain.repository.remote.response.GetPokemonDetailResponse

object PokemonDetailMapper {
    fun map(response: GetPokemonDetailResponse): PokemonDetail {
        return PokemonDetail(
            name = response.name,
            weight = response.weight / 10.0, // Convertir de hectogramos a kilogramos
            height = response.height / 10.0, // Convertir de decímetros a metros
            abilities = response.abilities.map { Ability(it.name) },
            imageUrl = response.sprites.frontDefault
        )
    }
}