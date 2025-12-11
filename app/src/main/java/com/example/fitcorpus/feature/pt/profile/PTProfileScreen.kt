package com.example.fitcorpus.feature.pt.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitcorpus.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PTProfileScreen(
    onNavigateToPackages: () -> Unit,
    viewModel: PTProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profilim") },
                actions = {
                    IconButton(onClick = { /* TODO: Edit profile */ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Düzenle"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    uiState.user?.let { user ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "${user.name} ${user.surname}",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Text(
                                    text = user.email,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(
                            onClick = onNavigateToPackages,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Paketlerimi Yönet")
                        }
                    }
                }
            }
        }
    }
}

