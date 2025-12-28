package com.example.fitcorpus.ui.screens.pt.students

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitcorpus.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsScreen(
    onNavigateBack: () -> Unit,
    viewModel: StudentsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadStudents()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Öğrencilerim") }
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            uiState.error != null -> {
                ErrorContent(
                    message = uiState.error ?: "Bir hata oluştu",
                    onRetry = { viewModel.loadStudents() },
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
                    items(uiState.students) { student ->
                        StudentCard(
                            student = student,
                            onTrackClick = {
                                viewModel.loadStudentLogs(student.id)
                            },
                            onAssignPlanClick = {
                                // TODO: Open assign plan dialog
                            }
                        )
                    }
                    
                    if (uiState.students.isEmpty()) {
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
private fun StudentCard(
    student: com.example.fitcorpus.domain.model.Student,
    onTrackClick: () -> Unit,
    onAssignPlanClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${student.name} ${student.surname}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onTrackClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Takip Et")
                }
                OutlinedButton(
                    onClick = onAssignPlanClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Plan Ata")
                }
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
            text = "Henüz öğrenci bulunmuyor",
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

