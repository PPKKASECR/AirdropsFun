package com.ascr.airdropsfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ascr.airdropsfun.ui.theme.AirdropsFunTheme
import com.google.firebase.firestore.FirebaseFirestore

class AdminAirdropActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AirdropsFunTheme {
                // Call the AdminAirdropScreen, which should be defined in its own file.
                AdminAirdropScreen(
                    onAdd = { titulo, descripcion, fecha, enlace, onSuccess, onError ->
                        val firestore = FirebaseFirestore.getInstance()
                        val airdrop = hashMapOf(
                            "titulo" to titulo,
                            "descripcion" to descripcion,
                            "fecha" to fecha,
                            "enlace" to enlace
                        )

                        firestore.collection("airdrops")
                            .add(airdrop)
                            // 1. CORRECTED: Call the 'onSuccess' lambda parameter directly.
                            .addOnSuccessListener { onSuccess() }
                            .addOnFailureListener { e: Exception ->
                                onError(e.message ?: "Error desconocido")
                            }
                    }
                )
            }
        }
    }
}

// 2. REMOVED: The duplicate, placeholder definition of AdminAirdropScreen has been removed
//    to resolve the "Conflicting overloads" error. The real implementation should
//    exist in its own file (e.g., AdminAirdropScreen.kt).


// The Greeting composables below are for preview purposes and can be kept or removed.
@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    AirdropsFunTheme {
        Greeting2("Android")
    }
}