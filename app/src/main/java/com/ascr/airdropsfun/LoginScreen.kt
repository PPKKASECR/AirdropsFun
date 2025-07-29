package com.ascr.airdropsfun

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ascr.airdropsfun.ui.theme.AirdropsFunTheme

/**
 * Displays the login UI and delegates user actions to callbacks.
 *
 * @param authState The current state of the authentication process.
 * @param onLoginClick Callback invoked when the user clicks the login button.
 * @param onNavigateToRegister Callback invoked when the user clicks the link to register.
 * @param onForgotPasswordClick Callback invoked for password recovery.
 */
@Composable
fun LoginScreen(
    authState: AuthState,
    onLoginClick: (email: String, pass: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // The UI is in a loading state if authState is AuthState.Loading
    val isLoading = authState is AuthState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            enabled = !isLoading // Disable field when loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            enabled = !isLoading // Disable field when loading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Show a progress indicator or the login button
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { onLoginClick(email, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Entrar")
            }
        }

        // Show an error message if the authState is an error
        if (authState is AuthState.Error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = authState.message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToRegister, enabled = !isLoading) {
            Text("¿No tienes cuenta? Regístrate")
        }

        TextButton(onClick = onForgotPasswordClick, enabled = !isLoading) {
            Text("¿Olvidaste tu contraseña?")
        }
    }
}

// --- PREVIEWS ---

@Preview(showBackground = true, name = "Idle State")
@Composable
fun LoginScreenPreview() {
    AirdropsFunTheme {
        LoginScreen(
            authState = AuthState.Idle,
            onLoginClick = { _, _ -> },
            onNavigateToRegister = {},
            onForgotPasswordClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun LoginScreenLoadingPreview() {
    AirdropsFunTheme {
        LoginScreen(
            authState = AuthState.Loading,
            onLoginClick = { _, _ -> },
            onNavigateToRegister = {},
            onForgotPasswordClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun LoginScreenErrorPreview() {
    AirdropsFunTheme {
        LoginScreen(
            authState = AuthState.Error("Invalid credentials. Please try again."),
            onLoginClick = { _, _ -> },
            onNavigateToRegister = {},
            onForgotPasswordClick = {}
        )
    }
}