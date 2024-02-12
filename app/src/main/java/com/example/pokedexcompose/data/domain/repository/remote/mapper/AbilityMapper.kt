package com.example.pokedexcompose.data.domain.repository.remote.mapper

import com.example.pokedexcompose.data.domain.model.Ability
import com.example.pokedexcompose.data.domain.repository.remote.response.AbilityResponse

object AbilityMapper {
    fun map(response: AbilityResponse): Ability {
        return Ability(
            name = response.name
        )
    }
}