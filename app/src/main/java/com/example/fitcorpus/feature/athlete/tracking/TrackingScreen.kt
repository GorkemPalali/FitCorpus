package com.example.fitcorpus.feature.athlete.tracking

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fitcorpus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(
    onNavigateToWorkout: () -> Unit,
    onNavigateToDiet: () -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.background_dark)
                )
            )
        },
        containerColor = colorResource(id = R.color.background_dark)
    ) { padding ->
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Workout Tracking Card
                TrackingCard(
                    title = "Antrenman Takibi",
                    description = "Egzersizlerini kaydet ve ilerle",
                    onClick = onNavigateToWorkout,
                    gradientColors = listOf(
                        Color(0xFF8B6F47), // Warm golden-brown
                        Color(0xFF6B5238)
                    ),
                    imageModel = R.drawable.workoutlog,
                    screenWidth = screenWidth
                )
                
                // Diet Tracking Card
                TrackingCard(
                    title = "Diyet Takibi",
                    description = "Besinlerini takip et ve hedeflerine ulaş",
                    onClick = onNavigateToDiet,
                    gradientColors = listOf(
                        Color(0xFFF5F1E8), // Light beige
                        Color(0xFFE8E0D0)
                    ),
                    imageModel = R.drawable.dietlog,
                    screenWidth = screenWidth
                )
            }
        }
    }
}

@Composable
private fun TrackingCard(
    title: String,
    description: String,
    onClick: () -> Unit,
    gradientColors: List<Color>,
    imageModel: Any,
    screenWidth: androidx.compose.ui.unit.Dp
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "card_scale"
    )
    
    // Responsive card dimensions - square cards with max width constraint
    val cardMaxWidth = when {
        screenWidth > 600.dp -> 320.dp  // Tablet: smaller max width for elegant look
        screenWidth > 400.dp -> screenWidth * 0.75f  // Large phones: 75% of screen
        else -> screenWidth * 0.85f  // Small phones: 85% of screen
    }
    
    Box(
        modifier = Modifier
            .widthIn(max = cardMaxWidth)
            .aspectRatio(1f)  // Square (1:1 aspect ratio)
            .scale(scale)
            .clip(RoundedCornerShape(20.dp))  // Slightly more rounded for elegant look
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    isPressed = true
                    onClick()
                }
            )
    ) {
        // Background Image with fallback
        Box(modifier = Modifier.fillMaxSize()) {
            // Fallback gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = gradientColors
                        )
                    )
            )
            
            // Image overlay
            AsyncImage(
                model = imageModel,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0f)
                        ),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f
                    )
                )
        )
        
        // Content at bottom
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
