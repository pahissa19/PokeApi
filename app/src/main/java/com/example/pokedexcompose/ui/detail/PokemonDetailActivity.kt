package com.example.pokedexcompose.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.example.pokedexcompose.R
import com.example.pokedexcompose.ui.main.MainActivity
import com.example.pokedexcompose.ui.main.colorImage


class PokemonDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemonName = intent.getStringExtra("pokemonName") ?: ""
        val pokemonId = intent.getIntExtra("pokemonId", 1)
        val viewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)

        viewModel.loadPokemonDetail(pokemonName, pokemonId)

        setContent {
            DetailScreen(viewModel)
        }
    }
}
@Composable
fun DetailScreen(viewModel: PokemonDetailViewModel){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar()
            Spacer(modifier = Modifier.weight(1f))
            PokemonDetail(viewModel = viewModel)
        }
    }
}
@Composable
fun TopAppBar() {
    val context = LocalContext.current

    androidx.compose.material.TopAppBar(
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                context.startActivity(Intent(context, MainActivity::class.java).apply {
                })
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_back),
                    contentDescription = "Back",
                )
            }
        }
    }
}

@Composable
fun PokemonDetail(viewModel: PokemonDetailViewModel) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val colorImage = colorImage()

    if (pokemonDetail != null) {
        val detail = pokemonDetail!!

        val dominantColor = colorImage.extractDominantColorFromImageUrl(
            LocalContext.current,
            detail.imageUrl
        )
        val textColor =
            if (dominantColor == Color.Black || dominantColor.luminance() < 0.18) {
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
                    text = detail.name.uppercase(),
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
                                "(${viewModel.convertKgToLbs(detail.weight)} Libras, " +
                                "${viewModel.convertKgToOz(detail.weight)} Onzas)",
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
                                "(${viewModel.convertMetersToFt(detail.height)} pies)",
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            color = dominantColor,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)

                    ) {
                        Text(
                            text = "Habilidades:",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
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





