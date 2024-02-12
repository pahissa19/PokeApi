package com.example.pokedexcompose.data.domain.model

data class PokemonDetail(
    val name: String,
    val weight: Double,
    val height: Double,
    val abilities: List<Ability>,
    var imageUrl: String
)
