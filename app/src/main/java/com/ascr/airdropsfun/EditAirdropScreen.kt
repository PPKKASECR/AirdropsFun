package com.ascr.airdropsfun

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ascr.airdropsfun.ui.theme.AirdropsFunTheme

/**
 * Displays a form to edit an existing airdrop.
 *
 * @param airdrop The initial airdrop data to populate the form.
 * @param onSaveClick Callback invoked with the updated airdrop data when the user saves.
 * @param onNavigateBack Callback to navigate back.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAirdropScreen(
    airdrop: AirdropDetails,
    onSaveClick: (updatedAirdrop: AirdropDetails) -> Unit,
    onNavigateBack: () -> Unit
) {
    // State for each text field, initialized with the existing airdrop data.
    var titulo by remember { mutableStateOf(airdrop.titulo) }
    var descripcion by remember { mutableStateOf(airdrop.descripcion) }
    var fecha by remember { mutableStateOf(airdrop.fecha) }
    var enlace by remember { mutableStateOf(airdrop.enlace) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Airdrop") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Navigate back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5
            )
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = enlace,
                onValueChange = { enlace = it },
                label = { Text("Enlace") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val updatedAirdrop = airdrop.copy(
                        titulo = titulo,
                        descripcion = descripcion,
                        fecha = fecha,
                        enlace = enlace
                    )
                    onSaveClick(updatedAirdrop)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditAirdropScreenPreview() {
    val fakeAirdrop = AirdropDetails(
        id = "1",
        titulo = "Airdrop de Júpiter",
        descripcion = "Descripción existente.",
        fecha = "31/10/2023",
        enlace = "https://jup.ag/"
    )
    AirdropsFunTheme {
        EditAirdropScreen(
            airdrop = fakeAirdrop,
            onSaveClick = {},
            onNavigateBack = {}
        )
    }
}