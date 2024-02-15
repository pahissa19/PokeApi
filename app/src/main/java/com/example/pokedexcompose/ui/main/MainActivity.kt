package com.example.pokedexcompose.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pokedexcompose.data.domain.model.Pokemon
import com.example.pokedexcompose.ui.detail.PokemonDetailActivity
import com.example.pokedexcompose.ui.list.PokemonListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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
        val dominantColor =
            com.example.pokedexcompose.ui.detail.extractDominantColorFromImageUrl(
                LocalContext.current,
                pokemon.imageUrl
            )
        val textColor = if (dominantColor == Color.Black || dominantColor.luminance() < 0.18) {
            Color.White
        } else {
            Color.Black
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            color = dominantColor,
            elevation = 2.dp,
            shape = MaterialTheme.shapes.medium
        ) {
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
                        modifier = Modifier.size(64.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = pokemon.name.uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    ),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 8.dp),
                )
            }
        }

    }
}