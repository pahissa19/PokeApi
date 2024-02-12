package com.example.pokedexcompose.data.domain.model

data class PokemonDetail(
    val name: String,
    val weight: Double, // En kilogramos
    val height: Double, // En metros
    val abilities: List<Ability>,
    val imageUrl: String
)
