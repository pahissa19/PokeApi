package com.example.pokedexcompose.data.domain.repository.remote.response.pokemon

data class GetPokemonDetailResponse(
    val name: String,
    val weight: Double,
    val height: Double,
    val abilities: List<AbilityResponse>,
    val sprites: Sprites
)