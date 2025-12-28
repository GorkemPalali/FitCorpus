package com.example.fitcorpus.ui.screens.pt.plans

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
import com.example.fitcorpus.domain.model.PlanType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlansScreen(
    onNavigateBack: () -> Unit,
    viewModel: PlansViewModel = hiltViewModel()
) {
    var selectedType by remember { mutableStateOf<PlanType?>(null) }
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(selectedType) {
        viewModel.loadPlans(selectedType)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planlarım") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Open create plan dialog */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Plan Ekle")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedType == null,
                    onClick = { selectedType = null },
                    label = { Text("Tümü") }
                )
                FilterChip(
                    selected = selectedType == PlanType.WORKOUT,
                    onClick = { selectedType = PlanType.WORKOUT },
                    label = { Text("Antrenman") }
                )
                FilterChip(
                    selected = selectedType == PlanType.DIET,
                    onClick = { selectedType = PlanType.DIET },
                    label = { Text("Beslenme") }
                )
            }
            
            when {
                uiState.isLoading -> LoadingView()
                uiState.error != null -> {
                    ErrorContent(
                        message = uiState.error ?: "Bir hata oluştu",
                        onRetry = { viewModel.loadPlans(selectedType) }
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
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
            text = "Henüz plan eklenmemiş",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
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

