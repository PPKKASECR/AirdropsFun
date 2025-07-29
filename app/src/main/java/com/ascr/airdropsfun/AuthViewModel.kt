package com.ascr.airdropsfun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun registerUser(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("El correo y la contraseña no pueden estar vacíos.")
            return
        }
        if (pass.length < 6) {
            _authState.value = AuthState.Error("La contraseña debe tener al menos 6 caracteres.")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.createUserWithEmailAndPassword(email, pass).await()
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseAuthUserCollisionException -> "El correo electrónico ya está en uso por otra cuenta."
                    is FirebaseAuthInvalidCredentialsException -> "El formato del correo electrónico no es válido."
                    else -> e.message ?: "Error desconocido durante el registro."
                }
                _authState.value = AuthState.Error(errorMessage)
            }
        }
    }

    fun loginUser(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("El correo y la contraseña no pueden estar vacíos.")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.signInWithEmailAndPassword(email, pass).await()
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseAuthInvalidCredentialsException -> "Credenciales incorrectas. Por favor, inténtalo de nuevo."
                    else -> "No se pudo iniciar sesión. Comprueba tu conexión."
                }
                _authState.value = AuthState.Error(errorMessage)
            }
        }
    }

    // CORRECCIÓN: Añadir la función logoutUser que faltaba.
    /**
     * Signs out the current user from Firebase.
     */
    fun logoutUser() {
        auth.signOut()
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}