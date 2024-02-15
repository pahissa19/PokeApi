package com.example.pokedexcompose.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.pokedexcompose.data.domain.model.PokemonDetail


class PokemonDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemonName = intent.getStringExtra("pokemonName") ?: ""
        val pokemonId = intent.getIntExtra("pokemonId", 1)

        val viewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)

        viewModel.loadPokemonDetail(pokemonName, pokemonId)


        setContent {
            PokemonDetailScreen(viewModel)
        }
    }
}


@Composable
fun PokemonDetailScreen(viewModel: PokemonDetailViewModel) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    if (pokemonDetail != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val detail = pokemonDetail!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = detail.name.capitalize(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Mostrar la foto del Pokémon
                Image(
                    painter = rememberImagePainter(detail.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar el peso del Pokémon en Kg, Libras y Onzas
                Text(
                    text = "Peso: ${detail.weight} Kg (${convertKgToLbs(detail.weight)} Libras, ${convertKgToOz(detail.weight)} Onzas)",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Mostrar la altura del Pokémon en metros y pies
                Text(
                    text = "Altura: ${detail.height} metros (${convertMetersToFt(detail.height)} pies)",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                // Mostrar el listado de habilidades en inglés
                Text(
                    text = "Habilidades:",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                if (detail.abilities.isNotEmpty()) {
                    LazyColumn {
                        items(detail.abilities.size) { index ->
                            Text(
                                text = detail.abilities[index].name,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                } else {
                    Text(
                        text = "No hay habilidades disponibles.",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        } else {
            Text(
                text = "Cargando detalles...",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }



    // Funciones de utilidad para convertir unidades de peso y altura
    private fun convertKgToLbs(kg: Double): Double = kg * 2.20462
    private fun convertKgToOz(kg: Double): Double = kg * 35.274
    private fun convertMetersToFt(meters: Double): Double = meters * 3.28084
