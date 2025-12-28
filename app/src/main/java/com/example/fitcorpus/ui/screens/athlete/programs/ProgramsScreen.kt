package com.example.fitcorpus.ui.screens.athlete.programs

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
fun ProgramsScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProgramsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadPlans()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Programlarım") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Open create program dialog */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Program Ekle")
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            uiState.error != null -> {
                ErrorContent(
                    message = uiState.error ?: "Bir hata oluştu",
                    onRetry = { viewModel.loadPlans() },
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
                    items(uiState.plans) { plan ->
                        PlanCard(plan = plan)
                    }
                    
                    if (uiState.plans.isEmpty()) {
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
private fun PlanCard(plan: com.example.fitcorpus.domain.model.Plan) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = plan.name,
                style = MaterialTheme.typography.titleMedium
            )
            plan.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall
                )
            }
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
            text = "Henüz program eklenmemiş",
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

