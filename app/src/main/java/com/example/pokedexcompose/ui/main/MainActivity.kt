package com.example.pokedexcompose.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokedexcompose.data.domain.model.Pokemon
import com.example.pokedexcompose.ui.detail.PokemonDetailActivity
import com.example.pokedexcompose.ui.list.PokemonListViewModel


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
        val context = LocalContext.current
        val pokemonList by viewModel.pokemonList.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getPokemonList(limit = 30, offset = 0)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(pokemonList) { pokemon ->
                PokemonItem(pokemon = pokemon) {
                    context.startActivity(Intent(context, PokemonDetailActivity::class.java).apply {
                        putExtra("pokemonName", pokemon.name)
                        putExtra("pokemonId", pokemon.id)
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }


    @Composable
    fun PokemonItem(pokemon: Pokemon, onClick: () -> Unit) {
        Surface (
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            elevation = 2.dp,
            shape = MaterialTheme.shapes.medium
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Box(
                    modifier = Modifier.size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = pokemon.imageUrl),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = pokemon.name.uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ), fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 8.dp),
                )
            }
        }

    }
}