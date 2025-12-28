package com.example.fitcorpus.ui.screens.pt.packages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitcorpus.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackagesScreen(
    trainerId: String,
    onNavigateBack: () -> Unit,
    viewModel: PackagesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(trainerId) {
        viewModel.loadPackages(trainerId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paketlerim") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Open create package dialog */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Paket Ekle")
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            uiState.error != null -> {
                ErrorContent(
                    message = uiState.error ?: "Bir hata oluştu",
                    onRetry = { viewModel.refreshPackages() },
                    modifier = Modifier.padding(padding)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.packages) { pkg ->
                        PackageCard(packageItem = pkg)
                    }
                    
                    if (uiState.packages.isEmpty()) {
                        item {
                            EmptyStateContent()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PackageCard(packageItem: com.example.fitcorpus.domain.model.Package) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = packageItem.title,
                style = MaterialTheme.typography.titleMedium
            )
            packageItem.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = "${packageItem.price} TL - ${packageItem.durationWeeks} hafta",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun EmptyStateContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Henüz paket eklenmemiş",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Tekrar Dene")
        }
    }
}

