package com.ascr.airdropsfun

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun AdminAirdropScreen(
    onAdd: (
        titulo: String,
        descripcion: String,
        fecha: String,
        enlace: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) -> Unit
) {
    val context = LocalContext.current

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var enlace by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = enlace, onValueChange = { enlace = it }, label = { Text("Enlace") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onAdd(titulo, descripcion, fecha, enlace,
                  {
                    Toast.makeText(context, "Airdrop agregado", Toast.LENGTH_SHORT).show()
                    titulo = ""
                    descripcion = ""
                    fecha = ""
                    enlace = ""
                },
                 { mensaje ->
                    Toast.makeText(context, "Error: $mensaje", Toast.LENGTH_SHORT).show()
                }
            )
        }) {
            Text("Agregar Airdrop")
        }
    }
}
