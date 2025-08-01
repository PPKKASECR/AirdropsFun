package com.ascr.airdropsfun

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// --- DATA CLASSES ---
data class Airdrop(
    @DocumentId val id: String = "",
    val titulo: String = "",
    val fecha: String = ""
)

data class AirdropStep(
    val description: String = "",
    val imageUrl: String = ""
)

data class AirdropDetails(
    @DocumentId val id: String = "",
    val titulo: String = "",
    val fecha: String = "",
    val descripcion: String = "",
    val enlace: String = "",
    val steps: List<AirdropStep> = emptyList(),
    val ownerId: String = ""
)

// --- STATE CLASSES (Sin cambios) ---
sealed class AirdropListState {
    data object Loading : AirdropListState()
    data class Success(val airdrops: List<Airdrop>) : AirdropListState()
    data class Error(val message: String) : AirdropListState()
}

sealed class AirdropDetailState {
    data object Loading : AirdropDetailState()
    data class Success(val airdrop: AirdropDetails) : AirdropDetailState()
    data class Error(val message: String) : AirdropDetailState()
}

class AirdropViewModel : ViewModel() {

    private val firestore: FirebaseFirestore = Firebase.firestore

    private val _airdropsState = MutableStateFlow<AirdropListState>(AirdropListState.Loading)
    val airdropsState = _airdropsState.asStateFlow()

    private val _airdropDetailState = MutableStateFlow<AirdropDetailState>(AirdropDetailState.Loading)
    val airdropDetailState = _airdropDetailState.asStateFlow()

    init {
        listenForAirdrops()
    }

    private fun listenForAirdrops() {
        firestore.collection("airdrops")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    _airdropsState.value = AirdropListState.Error(e.message ?: "Error desconocido")
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val airdropDetailsList = snapshot.toObjects(AirdropDetails::class.java)
                    val airdropsList = airdropDetailsList.map { detail ->
                        Airdrop(
                            id = detail.id,
                            titulo = detail.titulo,
                            fecha = detail.fecha
                        )
                    }
                    _airdropsState.value = AirdropListState.Success(airdropsList)
                } else {
                    _airdropsState.value = AirdropListState.Error("No se recibieron datos.")
                }
            }
    }

    fun saveAirdrop(titulo: String, fecha: String, descripcion: String, enlace: String) {
        val userId = Firebase.auth.currentUser?.uid
        if (userId == null) {
            Log.e("AirdropViewModel", "Error: Intento de guardar un airdrop sin un usuario logueado.")
            return
        }

        viewModelScope.launch {
            try {
                val newAirdrop = AirdropDetails(
                    titulo = titulo,
                    fecha = fecha,
                    descripcion = descripcion,
                    enlace = enlace,
                    steps = emptyList(),
                    ownerId = userId
                )
                firestore.collection("airdrops").add(newAirdrop).await()
            } catch (e: Exception) {
                Log.e("AirdropViewModel", "Error saving airdrop", e)
            }
        }
    }

    fun fetchAirdropById(airdropId: String) {
        viewModelScope.launch {
            _airdropDetailState.value = AirdropDetailState.Loading
            try {
                val document = firestore.collection("airdrops").document(airdropId).get().await()
                val airdropDetails = document.toObject(AirdropDetails::class.java)

                if (airdropDetails != null) {
                    _airdropDetailState.value = AirdropDetailState.Success(airdropDetails)
                } else {
                    _airdropDetailState.value = AirdropDetailState.Error("Airdrop no found.")
                }
            } catch (e: Exception) {
                _airdropDetailState.value = AirdropDetailState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }

    /**
     * Deletes an airdrop document from Firestore using its ID.
     * This also checks if the user attempting the deletion is the owner.
     */
    fun deleteAirdrop(airdropId: String) {
        val userId = Firebase.auth.currentUser?.uid
        if (userId == null) {
            Log.e("AirdropViewModel", "Cannot delete: User not logged in.")
            return
        }

        viewModelScope.launch {
            try {
                val airdropRef = firestore.collection("airdrops").document(airdropId)
                val document = airdropRef.get().await()
                val ownerId = document.getString("ownerId")

                // Security check: Only the owner can delete the airdrop
                if (ownerId == userId) {
                    airdropRef.delete().await()
                    // The SnapshotListener in listenForAirdrops will automatically update the list.
                } else {
                    Log.w("AirdropViewModel", "SECURITY: User $userId tried to delete airdrop owned by $ownerId")
                }
            } catch (e: Exception) {
                Log.e("AirdropViewModel", "Error deleting airdrop", e)
            }
        }
    }
}