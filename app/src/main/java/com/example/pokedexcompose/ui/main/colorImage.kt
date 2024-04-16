package com.example.pokedexcompose.ui.main

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class colorImage {
    private fun extractDominantColor(bitmap: Bitmap): Color {
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
                val bitmap = result.drawable.toBitmap().copy(Bitmap.Config.ARGB_8888, true)
                dominantColor.value = extractDominantColor(bitmap)
            }
        }

        return dominantColor.value
    }
}