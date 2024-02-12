package com.example.pokedexcompose.data.domain.model

data class Pokemon(
    val name: String,
    val url: String,
    val id: Int, // Nuevo campo para el ID del Pokémon
    val imageUrl: String // Nueva URL de imagen
)
