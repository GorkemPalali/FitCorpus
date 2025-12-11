package com.example.fitcorpus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.fitcorpus.core.auth.TokenManager
import com.example.fitcorpus.ui.designsystem.theme.FitCorpusTheme
import com.example.fitcorpus.domain.model.UserRole
import com.example.fitcorpus.navigation.AthleteNavGraph
import com.example.fitcorpus.navigation.AuthNavGraph
import com.example.fitcorpus.navigation.TrainerNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var tokenManager: TokenManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitCorpusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FitCorpusApp(tokenManager = tokenManager)
                }
            }
        }
    }
}

@Composable
fun FitCorpusApp(tokenManager: TokenManager) {
    val role by tokenManager.getRole().collectAsStateWithLifecycle(initialValue = null)
    val scope = rememberCoroutineScope()
    
    val authNavController = rememberNavController()
    val athleteNavController = rememberNavController()
    val trainerNavController = rememberNavController()
    
    when (role) {
        null -> {
            // User not logged in - show auth screens
            AuthNavGraph(
                navController = authNavController,
                onLoginSuccess = {
                    // Navigation will be handled automatically when role state updates
                },
                onRegisterSuccess = {
                    // Navigation will be handled automatically when role state updates
                }
            )
        }
        UserRole.ATHLETE -> {
            AthleteNavGraph(
                navController = athleteNavController,
                onLogout = {
                    scope.launch {
                        tokenManager.clearTokens()
                    }
                }
            )
        }
        UserRole.TRAINER -> {
            TrainerNavGraph(
                navController = trainerNavController,
                onLogout = {
                    scope.launch {
                        tokenManager.clearTokens()
                    }
                }
            )
        }
        UserRole.ADMIN -> {
            // For now show athlete screen
            AthleteNavGraph(
                navController = athleteNavController,
                onLogout = {
                    scope.launch {
                        tokenManager.clearTokens()
                    }
                }
            )
        }
    }
}