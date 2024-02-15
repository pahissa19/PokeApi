package com.example.pokedexcompose.data.domain.repository.remote.mapper

import com.example.pokedexcompose.data.domain.model.Ability
import com.example.pokedexcompose.data.domain.model.PokemonDetail
import com.example.pokedexcompose.data.domain.repository.remote.response.pokemon.GetPokemonDetailResponse

object PokemonDetailMapper {
    fun map(response: GetPokemonDetailResponse): PokemonDetail {
        val imageUrl = response.sprites.frontDefault ?: "default_image_url"
        return PokemonDetail(
            name = response.name,
            weight = response.weight / 10.0,
            height = response.height / 10.0,
            abilities = response.abilities.map { AbilityMapper.map(it) },
            imageUrl = imageUrl
        )
    }
}



