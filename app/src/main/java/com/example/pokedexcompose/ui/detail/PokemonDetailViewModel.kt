package com.example.pokedexcompose.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexcompose.data.domain.model.Ability
import com.example.pokedexcompose.data.domain.model.PokemonDetail
import com.example.pokedexcompose.data.domain.repository.remote.PokemonRepository
import com.example.pokedexcompose.data.domain.repository.remote.response.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class PokemonDetailViewModel : ViewModel() {
    private val repository = PokemonRepository(RetrofitClient.instance)

    private val _pokemonDetail = MutableStateFlow<PokemonDetail?>(null)
    val pokemonDetail: StateFlow<PokemonDetail?> = _pokemonDetail

    fun loadPokemonDetail(pokemonName: String, pokemonId: Int) {
        viewModelScope.launch {
            try {
                Log.d("PokemonDetailViewModel", "Fetching Pokemon details for: $pokemonName")
                val detail = repository.getPokemonDetail(pokemonName)
                val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png"
                detail.imageUrl = imageUrl
                Log.d("PokemonDetailViewModel", "Pokemon detail loaded successfully: $detail")
                _pokemonDetail.value = detail
            } catch (e: Exception) {
                Log.e("PokemonDetailViewModel", "Error loading Pokemon detail", e)
                // Handle error
            }
        }
    }

    // Funciones de utilidad para convertir unidades de peso y altura
    fun convertKgToLbs(kg: Double): Double = (kg * 2.20462 * 10).roundToInt() / 10.0
    fun convertKgToOz(kg: Double): Double = (kg * 35.274 * 10).roundToInt() / 10.0
    fun convertMetersToFt(meters: Double): Double = (meters * 3.28084 * 10).roundToInt() / 10.0
}




