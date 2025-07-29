package com.ascr.airdropsfun

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ascr.airdropsfun.ui.theme.AirdropsFunTheme

@Composable
fun ForgotPasswordScreen(
    onResetPasswordRequest: (email: String) -> Unit
) {
    // 'remember' y 'mutableStateOf' se usan para guardar el estado del campo de texto.
    // Cada vez que el usuario escribe, este estado se actualiza y la UI se recompone.
    var email by remember { mutableStateOf("") }

    // Column organiza sus hijos en una secuencia vertical.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Restablecer Contraseña")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Introduce tu email para recibir un enlace de restablecimiento.")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { onResetPasswordRequest(email) }) {
            Text("Enviar Enlace")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    AirdropsFunTheme {
        ForgotPasswordScreen(onResetPasswordRequest = { email ->
            println("Password reset requested for: $email")
        })
    }
}