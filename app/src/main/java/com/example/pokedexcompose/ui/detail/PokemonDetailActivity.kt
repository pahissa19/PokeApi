package com.example.pokedexcompose.ui.detail

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt


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
        val detail = pokemonDetail!!

        val dominantColor = extractDominantColorFromImageUrl(LocalContext.current, detail.imageUrl)
        val textColor = if (dominantColor == Color.Black || dominantColor.luminance() < 0.18) {
            Color.White
        } else {
            Color.Black
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = detail.name.capitalize(),
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Image(
                    painter = rememberImagePainter(detail.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Text(
                        text = "Peso:",
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "${detail.weight} Kg " +
                                "(${convertKgToLbs(detail.weight)} Libras, " +
                                "${convertKgToOz(detail.weight)} Onzas)",
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Column {
                    Text(
                        text = "Altura:",
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "${detail.height} metros " +
                                "(${convertMetersToFt(detail.height)} pies)",
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = dominantColor, shape = MaterialTheme.shapes.medium)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)

                    ) {
                        Text(
                            text = "Habilidades:",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = textColor
                        )

                        if (detail.abilities.isNotEmpty()) {
                            LazyColumn {
                                items(detail.abilities) { ability ->
                                    Text(
                                        text = ability.name,
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(bottom = 4.dp),
                                        color = textColor
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "No hay habilidades disponibles.",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
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

fun extractDominantColor(bitmap: Bitmap): Color {
    val palette = Palette.from(bitmap).generate()
    val dominantSwatch = palette.dominantSwatch
    return if (dominantSwatch != null) {
        Color(dominantSwatch.rgb)
    } else {
        Color.White // Si no se encuentra ningún color predominante, se devuelve blanco
    }
}


@Composable
fun extractDominantColorFromImageUrl(context: Context, imageUrl: String): Color {
    val imageLoader = ImageLoader(LocalContext.current)
    val dominantColor = remember { mutableStateOf(Color.White) }

    LaunchedEffect(imageUrl) {
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .build()
        val result = withContext(Dispatchers.IO) {
            imageLoader.execute(request)
        }
        if (result is SuccessResult) {
            val bitmap = result.drawable.toBitmap()
            dominantColor.value = extractDominantColor(bitmap)
        }
    }

    return dominantColor.value
}

// Funciones de utilidad para convertir unidades de peso y altura
private fun convertKgToLbs(kg: Double): Double = (kg * 2.20462 * 10).roundToInt() / 10.0
private fun convertKgToOz(kg: Double): Double = (kg * 35.274 * 10).roundToInt() / 10.0
private fun convertMetersToFt(meters: Double): Double = (meters * 3.28084 * 10).roundToInt() / 10.0

