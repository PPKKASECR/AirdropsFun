// D:/AirdropsFun/app/src/main/java/com/ascr/airdropsfun/RegisterScreen.kt

package com.ascr.airdropsfun

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ascr.airdropsfun.ui.theme.AirdropsFunTheme
// import com.ascr.airdropsfun.AuthState

/**
 * A Composable that displays the registration UI.
 * This screen is "dumb" and does not contain any business logic.
 * It communicates user actions via lambda functions and reacts to the
 * provided state.
 *
 * @param authState The current state of the authentication process (e.g., Idle,
 *                  Loading, Error).
 * @param onRegisterClick A callback invoked when the user clicks the register button,
 *                        providing the entered email and password.
 * @param onNavigateToLogin A callback invoked when the user clicks the link to go
 *                          to the login screen.
 */
@Composable
fun RegisterScreen(
    authState: AuthState,
    onRegisterClick: (email: String, pass: String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isLoading = authState is AuthState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            enabled = !isLoading
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
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            enabled = !isLoading,
            isError = confirmPassword.isNotBlank() && password != confirmPassword
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { onRegisterClick(email, password) },
                modifier = Modifier.fillMaxWidth(),
                // --- CORREGIDO ---
                // Se elimina la condición redundante `&& !isLoading`
                enabled = password.isNotBlank() && password.length >= 6 && password == confirmPassword
            ) {
                Text("Registrarse")
            }
        }

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

        TextButton(
            onClick = onNavigateToLogin,
            enabled = !isLoading
        ) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}


// --- PREVIEWS ---
// (No changes needed here)

@Preview(showBackground = true, name = "Idle State")
@Composable
fun RegisterScreenPreview() {
    AirdropsFunTheme {
        RegisterScreen(
            authState = AuthState.Idle,
            onRegisterClick = { _, _ -> },
            onNavigateToLogin = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun RegisterScreenLoadingPreview() {
    AirdropsFunTheme {
        RegisterScreen(
            authState = AuthState.Loading,
            onRegisterClick = { _, _ -> },
            onNavigateToLogin = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun RegisterScreenErrorPreview() {
    AirdropsFunTheme {
        RegisterScreen(
            authState = AuthState.Error("La contraseña es demasiado corta."),
            onRegisterClick = { _, _ -> },
            onNavigateToLogin = {}
        )
    }
}
