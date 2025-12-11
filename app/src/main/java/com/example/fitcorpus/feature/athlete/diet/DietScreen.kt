package com.example.fitcorpus.feature.athlete.diet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitcorpus.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietScreen(
    onNavigateBack: () -> Unit,
    viewModel: DietViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadDietEntries()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Beslenme Takibi") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Geri"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Open create diet entry dialog */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Öğün Ekle")
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            uiState.error != null -> {
                ErrorContent(
                    message = uiState.error ?: "Bir hata oluştu",
                    onRetry = { viewModel.loadDietEntries() },
                    modifier = Modifier.padding(padding)
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    // Calories Summary Card
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Günlük Kalori",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = {
                                    if (uiState.targetCalories > 0) {
                                        (uiState.consumedCalories.toFloat() / uiState.targetCalories.toFloat()).coerceIn(0f, 1f)
                                    } else 0f
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${uiState.consumedCalories} / ${uiState.targetCalories} kcal",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.dietEntries) { entry ->
                            DietEntryCard(entry = entry)
                        }
                        
                        if (uiState.dietEntries.isEmpty()) {
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
private fun DietEntryCard(entry: com.example.fitcorpus.domain.model.DietEntry) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = entry.mealName ?: entry.meal?.name ?: "Öğün",
                    style = MaterialTheme.typography.titleMedium
                )
                entry.portion?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Text(
                text = "${entry.calories} kcal",
                style = MaterialTheme.typography.titleMedium
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
            text = "Henüz öğün eklenmemiş",
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

