package com.example.pokedexcompose.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexcompose.data.domain.model.PokemonDetail
import com.example.pokedexcompose.data.domain.repository.remote.PokemonRepository
import com.example.pokedexcompose.data.domain.repository.remote.response.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PokemonDetailViewModel : ViewModel() {
    private val repository = PokemonRepository(RetrofitClient.instance)

    private val _pokemonDetail = MutableStateFlow<PokemonDetail?>(null)
    val pokemonDetail: StateFlow<PokemonDetail?> = _pokemonDetail

    fun loadPokemonDetail(pokemonName: String) {
        viewModelScope.launch {
            try {
                Log.d("PokemonDetailViewModel", "Fetching Pokemon details for: $pokemonName")
                val detail = repository.getPokemonDetail(pokemonName)
                Log.d("PokemonDetailViewModel", "Pokemon detail loaded successfully: $detail")
                _pokemonDetail.value = detail
            } catch (e: Exception) {
                Log.e("PokemonDetailViewModel", "Error loading Pokemon detail", e)
                // Handle error
            }
        }
    }
}



