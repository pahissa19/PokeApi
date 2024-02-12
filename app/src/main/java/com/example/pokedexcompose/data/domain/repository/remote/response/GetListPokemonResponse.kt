package com.example.pokedexcompose.data.domain.repository.remote.response

import com.example.pokedexcompose.data.domain.model.Pokemon

data class GetListPokemonResponse(
    val count: Int,
    val results: List<Pokemon>
)
