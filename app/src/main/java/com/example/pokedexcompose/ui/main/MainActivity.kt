package com.example.pokedexcompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokedexcompose.R
import com.example.pokedexcompose.data.domain.model.Pokemon


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: PokemonListViewModel by viewModels()
        setContent {
            PokemonListScreen(viewModel)
        }
    }

    @Composable
    fun PokemonListScreen(viewModel: PokemonListViewModel) {
        val pokemonList by viewModel.pokemonList.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getPokemonList(limit = 30, offset = 0) // Ejemplo de límite y desplazamiento
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(pokemonList) { pokemon ->
                PokemonItem(pokemon = pokemon)
            }
        }
    }

    @Composable
    fun PokemonItem(pokemon: Pokemon) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { /* Acción cuando se hace clic en un Pokémon */ }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = pokemon.url,
                    builder = {
                        placeholder(R.drawable.ic_launcher_background)
                        error(R.drawable.ic_launcher_background)
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = pokemon.name,
                textAlign = TextAlign.Start,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

