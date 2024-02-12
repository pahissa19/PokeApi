package com.example.pokedexcompose.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexcompose.data.domain.model.Pokemon
import com.example.pokedexcompose.data.domain.repository.remote.PokemonRepository
import com.example.pokedexcompose.data.domain.repository.remote.response.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class PokemonListViewModel : ViewModel() {
    private val repository = PokemonRepository(RetrofitClient.instance)

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    fun getPokemonList(limit: Int, offset: Int) {
        viewModelScope.launch {
            try {
                val pokemonList = repository.getListPokemon(limit, offset)
                _pokemonList.emit(pokemonList)
            } catch (e: Exception) {
                // Manejar el error, por ejemplo, mostrar un mensaje de error en la interfaz de usuario
                Log.e("PokemonListViewModel", "Error getting Pokemon list", e)
            }
        }
    }
}

