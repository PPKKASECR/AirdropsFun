package com.ascr.airdropsfun

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ascr.airdropsfun.ui.theme.AirdropsFunTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // LaunchedEffect es un composable especial para ejecutar efectos secundarios
    // (como una cuenta atrás) de forma segura dentro del ciclo de vida de un composable.
    // El bloque de código se ejecutará solo una vez cuando SplashScreen aparezca.
    LaunchedEffect(Unit) {
        // Espera 2 segundos (2000 milisegundos).
        delay(2000)
        // Llama a la función onTimeout para notificar que el tiempo ha terminado.
        onTimeout()
    }

    // Box es un contenedor que permite apilar elementos.
    // Es ideal para centrar un único elemento en la pantalla.
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Aquí iría el logo de tu aplicación.
        // Por ahora, usamos un icono de estrella como marcador de posición.
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "App Logo"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    AirdropsFunTheme {
        // Para la vista previa, pasamos una función vacía.
        SplashScreen(onTimeout = {})
    }
}