package com.example.fitcorpus.ui.screens.start

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitcorpus.R
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    onPTSelected: () -> Unit,
    onAthleteSelected: () -> Unit,
    onSignInClick: () -> Unit = {}
) {
    var selectedRole by remember { mutableStateOf<Role?>(null) }
    var hasNavigated by remember { mutableStateOf(false) }
    
    // Animation for swipe indicator
    val indicatorOffset by animateFloatAsState(
        targetValue = when (selectedRole) {
            Role.ATHLETE -> 0f
            Role.TRAINER -> 1f
            null -> 0f // Default to ATHLETE position
        },
        animationSpec = tween(durationMillis = 300),
        label = "indicator_animation"
    )
    
    // Register ekranına yumuşak geçiş için rol seçiminden sonra delay
    LaunchedEffect(selectedRole) {
        if (selectedRole != null && !hasNavigated) {
            delay(250)
            hasNavigated = true
            when (selectedRole) {
                Role.ATHLETE -> onAthleteSelected()
                Role.TRAINER -> onPTSelected()
                null -> { /* Do nothing */ }
            }
        }
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image with Blur Effect
        // Note: Replace with actual image URL or local resource
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background_dark))
        ) {
            // Placeholder gradient background - replace with AsyncImage when you have the image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0F1A14),
                                Color(0xFF1A2B1F),
                                Color(0xFF112117)
                            )
                        )
                    )
            )
        }
        
        // Dark overlay with blur effect simulation
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(id = R.color.background_dark).copy(alpha = 0.8f)
                )
        )
        
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top spacing and Logo
            Spacer(modifier = Modifier.height(48.dp))
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                // Fitness Center Icon (using emoji as fallback)
                Text(
                    text = "💪",
                    fontSize = 48.sp,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "FitCorpus",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Welcome Message
            Text(
                text = "Welcome to Your Fitness Journey",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp, top = 24.dp)
            )
            
            // Segmented Control for Role Selection
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
            ) {
                val containerWidth = maxWidth
                
                // Background container (rounded-full, black/30)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(9999.dp)
                        )
                        .padding(4.dp)
                ) {
                    // Animated indicator
                    val paddingDp = 4.dp
                    val availableWidth = containerWidth - paddingDp * 2
                    val indicatorWidth = availableWidth * 0.5f
                    val offsetX = indicatorOffset * indicatorWidth.value
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(indicatorWidth)
                            .offset(x = offsetX.dp)
                            .background(
                                color = colorResource(id = R.color.fitness_green),
                                shape = RoundedCornerShape(9999.dp)
                            )
                    )
                    
                    // Role selection buttons
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Athlete Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    selectedRole = Role.ATHLETE
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "I'm an Athlete",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (selectedRole == null || selectedRole == Role.ATHLETE) {
                                    Color.Black
                                } else {
                                    Color.White
                                }
                            )
                        }
                        
                        // Trainer Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    selectedRole = Role.TRAINER
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "I'm a Trainer",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (selectedRole == Role.TRAINER) {
                                    Color.Black
                                } else {
                                    Color.White
                                }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Sign In Link
            Row(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.sign_in_text),
                    lineHeight = 20.sp
                )
                Text(
                    text = "Sign In",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onSignInClick() }
                )
            }
        }
    }
}

private enum class Role {
    ATHLETE,
    TRAINER
}
