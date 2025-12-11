package com.example.fitcorpus.feature.trainer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitcorpus.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainerProfileScreen(
    trainerId: String,
    onNavigateBack: () -> Unit,
    onPackageClick: (String) -> Unit
) {
    var trainer by remember { mutableStateOf<com.example.fitcorpus.domain.model.Trainer?>(null) }
    var packages by remember { mutableStateOf<List<com.example.fitcorpus.domain.model.Package>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(trainerId) {
        // TODO: Load trainer and packages using use cases
        isLoading = false
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Antrenör Profili") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Geri"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            isLoading -> LoadingView(Modifier.padding(padding))
            error != null -> {
                ErrorContent(
                    message = error ?: "Bir hata oluştu",
                    onRetry = { /* TODO: Retry */ },
                    modifier = Modifier.padding(padding)
                )
            }
            trainer != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        TrainerInfoCard(trainer = trainer!!)
                    }
                    
                    item {
                        Text(
                            text = "Paketler",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    
                    items(packages) { pkg ->
                        PackageCard(
                            packageItem = pkg,
                            onClick = { onPackageClick(pkg.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrainerInfoCard(trainer: com.example.fitcorpus.domain.model.Trainer) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${trainer.name} ${trainer.surname}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "⭐ ${trainer.ratingAvg}",
                style = MaterialTheme.typography.bodyMedium
            )
            trainer.bio?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun PackageCard(
    packageItem: com.example.fitcorpus.domain.model.Package,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
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

