package com.example.fitcorpus.feature.pt.codes

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitcorpus.core.security.SecureScreen
import com.example.fitcorpus.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodesScreen(
    onNavigateBack: () -> Unit,
    viewModel: CodesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    SecureScreen {
        Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kodlarım") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Purchase Codes Section
            PurchaseCodesSection(
                onPurchase = { count ->
                    viewModel.purchaseCodes(count)
                },
                isPurchasing = uiState.isPurchasing
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Codes List
            when {
                uiState.isLoading -> LoadingView()
                uiState.error != null -> {
                    ErrorContent(
                        message = uiState.error ?: "Bir hata oluştu",
                        onRetry = { viewModel.loadCodes() }
                    )
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.codes) { code ->
                            CodeCard(code = code)
                        }
                        
                        if (uiState.codes.isEmpty()) {
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
}

@Composable
private fun PurchaseCodesSection(
    onPurchase: (Int) -> Unit,
    isPurchasing: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Kod Satın Al",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onPurchase(1) },
                    modifier = Modifier.weight(1f),
                    enabled = !isPurchasing
                ) {
                    Text("1 Kod")
                }
                OutlinedButton(
                    onClick = { onPurchase(5) },
                    modifier = Modifier.weight(1f),
                    enabled = !isPurchasing
                ) {
                    Text("5 Kod")
                }
                OutlinedButton(
                    onClick = { onPurchase(10) },
                    modifier = Modifier.weight(1f),
                    enabled = !isPurchasing
                ) {
                    Text("10 Kod")
                }
            }
        }
    }
}

@Composable
private fun CodeCard(code: com.example.fitcorpus.domain.model.OnboardingCode) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (code.status) {
                com.example.fitcorpus.domain.model.CodeStatus.ISSUED -> MaterialTheme.colorScheme.primaryContainer
                com.example.fitcorpus.domain.model.CodeStatus.REDEEMED -> MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = code.code,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = when (code.status) {
                    com.example.fitcorpus.domain.model.CodeStatus.ISSUED -> "Kullanılabilir"
                    com.example.fitcorpus.domain.model.CodeStatus.REDEEMED -> "Kullanıldı"
                },
                style = MaterialTheme.typography.bodySmall
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
            text = "Henüz kod bulunmuyor",
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

