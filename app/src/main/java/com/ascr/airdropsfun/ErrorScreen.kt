package com.ascr.airdropsfun

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ascr.airdropsfun.ui.theme.AirdropsFunTheme

/**
 * A generic screen to display when an error occurs.
 *
 * @param errorMessage An optional message describing the error.
 * @param onRetryClick A callback to be invoked when the user clicks the retry button.
 */
@Composable
fun ErrorScreen(
    errorMessage: String?,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Error Icon",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Oops! Algo salió mal",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRetryClick) {
            Text("Reintentar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AirdropsFunTheme {
        ErrorScreen(
            errorMessage = "No se pudo conectar al servidor.",
            onRetryClick = {}
        )
    }
}