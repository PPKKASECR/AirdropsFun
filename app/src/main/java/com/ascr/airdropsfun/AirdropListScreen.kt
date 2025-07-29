package com.ascr.airdropsfun

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
// CORRECCIÓN 1: Cambiar el import a la versión AutoMirrored recomendada.
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ascr.airdropsfun.ui.theme.AirdropsFunTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirdropListScreen(
    state: AirdropListState,
    onAirdropClick: (String) -> Unit,
    onAddAirdropClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.airdrops_list_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            // CORRECCIÓN 2: Usar el icono desde la ruta AutoMirrored.
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAirdropClick) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_airdrop_content_description)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is AirdropListState.Loading -> {
                    CircularProgressIndicator()
                }
                is AirdropListState.Success -> {
                    val airdrops = state.airdrops
                    if (airdrops.isEmpty()) {
                        Text(stringResource(id = R.string.no_airdrops_available))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(airdrops) { airdrop ->
                                AirdropItem(
                                    airdrop = airdrop,
                                    onItemClick = { onAirdropClick(airdrop.id) }
                                )
                            }
                        }
                    }
                }
                is AirdropListState.Error -> {
                    Text(
                        text = stringResource(id = R.string.error_message_prefix, state.message),
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AirdropItem(airdrop: Airdrop, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = airdrop.titulo, style = MaterialTheme.typography.titleLarge)
            Text(
                text = stringResource(id = R.string.airdrop_date_prefix, airdrop.fecha),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirdropListScreenPreview() {
    AirdropsFunTheme {
        AirdropListScreen(
            state = AirdropListState.Success(
                listOf(
                    Airdrop(id = "1", titulo = "Sample Airdrop 1", fecha = "Jan 1, 2024"),
                    Airdrop(id = "2", titulo = "Sample Airdrop 2", fecha = "Feb 1, 2024")
                )
            ),
            onAirdropClick = {},
            onAddAirdropClick = {},
            onLogoutClick = {}
        )
    }
}