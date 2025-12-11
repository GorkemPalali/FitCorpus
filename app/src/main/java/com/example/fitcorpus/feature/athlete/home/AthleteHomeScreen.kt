package com.example.fitcorpus.feature.athlete.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.fitcorpus.R
import com.example.fitcorpus.ui.component.LoadingView
import com.example.fitcorpus.domain.model.Exercise
import com.example.fitcorpus.domain.model.ExerciseType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AthleteHomeScreen(
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToDiet: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToExerciseDetail: (String) -> Unit = {},
    onNavigateToAllExercises: (ExerciseType) -> Unit = {},
    viewModel: AthleteHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        containerColor = colorResource(id = R.color.background_dark)
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .background(colorResource(id = R.color.background_dark))
                ) {
                    // Top Bar: Profile, Greeting, Notifications
                    TopBarSection(
                        userName = uiState.userName,
                        userPhotoUrl = uiState.userPhotoUrl,
                        onProfileClick = onNavigateToProfile
                    )
                    
                    // Calories Circular Progress
                    CaloriesProgressSection(
                        caloriesLeft = uiState.caloriesLeft,
                        caloriesProgress = uiState.caloriesProgress
                    )
                    
                    // Workout Exercises Section
                    ExercisesSection(
                        title = "Workout Exercises",
                        exercises = uiState.workoutExercises,
                        onExerciseClick = onNavigateToExerciseDetail,
                        onViewAllClick = { onNavigateToAllExercises(ExerciseType.WORKOUT) }
                    )
                    
                    // Warm-up Exercises Section
                    ExercisesSection(
                        title = "Warm-up Exercises",
                        exercises = uiState.warmupExercises,
                        onExerciseClick = onNavigateToExerciseDetail,
                        onViewAllClick = { onNavigateToAllExercises(ExerciseType.WARMUP) }
                    )
                    
                    // Bottom spacing for navbar
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
private fun TopBarSection(
    userName: String,
    userPhotoUrl: String?,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Picture
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(onClick = onProfileClick)
        ) {
            if (userPhotoUrl != null) {
                AsyncImage(
                    model = userPhotoUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userName.take(1).uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
            }
        }
        
        // Greeting
        Text(
            text = "Ready to train, $userName?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        )
        
        // Notifications Icon
        IconButton(
            onClick = { /* TODO: Handle notifications */ }
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun CaloriesProgressSection(
    caloriesLeft: Int,
    caloriesProgress: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Circular Progress Indicator
        CircularProgressIndicator(
            progress = { caloriesProgress },
            modifier = Modifier.size(192.dp),
            color = colorResource(id = R.color.fitness_green),
            strokeWidth = 12.dp,
            trackColor = Color.Gray.copy(alpha = 0.3f)
        )
        
        // Calories Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$caloriesLeft",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 48.sp
            )
            Text(
                text = "Calories Left",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun ExercisesSection(
    title: String,
    exercises: List<Exercise>,
    onExerciseClick: (String) -> Unit,
    onViewAllClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 22.sp
            )
            
            TextButton(
                onClick = onViewAllClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorResource(id = R.color.fitness_green)
                )
            ) {
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
        
        // Exercises Horizontal Scroll
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(exercises) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    onClick = { onExerciseClick(exercise.id) }
                )
            }
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(240.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Exercise Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (exercise.imageUrl != null) {
                    AsyncImage(
                        model = exercise.imageUrl,
                        contentDescription = exercise.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = exercise.name.take(2).uppercase(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                    }
                }
            }
            
            // Exercise Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = exercise.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
