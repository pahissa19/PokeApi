package com.example.pokedexcompose.data.domain.repository.remote.response.pokemon

import com.example.pokedexcompose.data.domain.model.Ability

data class AbilityResponse(
    val ability: Ability,
    val url: String
)
