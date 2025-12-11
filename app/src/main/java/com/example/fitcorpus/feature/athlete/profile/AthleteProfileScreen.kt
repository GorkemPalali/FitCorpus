package com.example.fitcorpus.feature.athlete.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitcorpus.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AthleteProfileScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit,
    viewModel: AthleteProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) {
            onLogout()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil") }
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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.user?.let { user ->
                        // Profile Info
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
                                user.height?.let {
                                    Text(
                                        text = "Boy: $it cm",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                user.weight?.let {
                                    Text(
                                        text = "Kilo: $it kg",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Logout Button
                        Button(
                            onClick = { viewModel.logout() },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoggingOut
                        ) {
                            if (uiState.isLoggingOut) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text("Çıkış Yap")
                            }
                        }
                    }
                }
            }
        }
    }
}

