package com.example.pokedexcompose.data.domain.repository.remote.response

import com.example.pokedexcompose.data.domain.model.Ability

data class GetPokemonDetailResponse(
    val name: String,
    val weight: Double,
    val height: Double,
    val abilities: List<AbilityResponse>,
    val sprites: Sprites
)