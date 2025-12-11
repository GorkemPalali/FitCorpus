package com.example.fitcorpus.feature.athlete.discover

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.fitcorpus.R
import com.example.fitcorpus.core.security.SecureScreen
import com.example.fitcorpus.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToTrainerProfile: (String) -> Unit,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    
    LaunchedEffect(Unit) {
        viewModel.loadMyTrainer()
    }
    
    SecureScreen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            "Find a Trainer",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.background_dark)
                    )
                )
            },
            containerColor = colorResource(id = R.color.background_dark)
        ) { padding ->
            when {
                uiState.isLoading -> {
                    LoadingView(Modifier.padding(padding))
                }
                uiState.error != null -> {
                    ErrorContent(
                        message = uiState.error ?: "Bir hata oluştu",
                        onRetry = { 
                            viewModel.loadTrainers()
                            viewModel.loadMyTrainer()
                        },
                        modifier = Modifier.padding(padding)
                    )
                }
                else -> {
                    val listState = rememberLazyListState()
                    var showFilterBottomSheet by remember { mutableStateOf(false) }
                    
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colorResource(id = R.color.background_dark)),
                            contentPadding = padding
                        ) {
                            // Code Entry Section moved to top
                            item(key = "code_entry") {
                                CodeEntrySection(
                                    onRedeem = { code ->
                                        viewModel.redeemCode(code)
                                    },
                                    isRedeeming = uiState.isRedeeming,
                                    screenWidth = screenWidth
                                )
                            }

                            // Your Trainer Section
                            item(key = "your_trainer") {
                                YourTrainerSection(
                                    myTrainer = uiState.myTrainer,
                                    onViewProfile = { trainerId ->
                                        onNavigateToTrainerProfile(trainerId)
                                    },
                                    screenWidth = screenWidth
                                )
                            }
                            
                            // Discover Trainers Header (non-sticky, will be replaced by sticky one)
                            item(key = "discover_header") {
                                DiscoverTrainersHeader(
                                    onFilterClick = { showFilterBottomSheet = true },
                                    screenWidth = screenWidth
                                )
                            }
                            
                            // Trainers Grid
                            item(key = "trainers_grid") {
                                TrainersGridSection(
                                    trainers = uiState.trainers,
                                    onTrainerClick = { trainerId ->
                                        onNavigateToTrainerProfile(trainerId)
                                    },
                                    screenWidth = screenWidth
                                )
                            }
                            
                            // Bottom padding for navbar
                            item(key = "bottom_padding") {
                                Spacer(modifier = Modifier.height(80.dp))
                            }
                        }
                        
                        // Sticky Header (overlay)
                        StickyHeader(
                            listState = listState,
                            screenWidth = screenWidth,
                            topPadding = padding.calculateTopPadding(),
                            onFilterClick = { showFilterBottomSheet = true }
                        )
                    }
                    
                    // Filter Bottom Sheet
                    if (showFilterBottomSheet) {
                        FilterBottomSheet(
                            filters = uiState.filters,
                            onDismiss = { showFilterBottomSheet = false },
                            onApplyFilters = { newFilters ->
                                viewModel.applyFilters(newFilters)
                            },
                            onClearFilters = {
                                viewModel.clearFilters()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun YourTrainerSection(
    myTrainer: com.example.fitcorpus.domain.model.Trainer?,
    onViewProfile: (String) -> Unit,
    screenWidth: androidx.compose.ui.unit.Dp
) {
    val horizontalPadding = if (screenWidth > 600.dp) 24.dp else 16.dp
    val verticalPadding = if (screenWidth > 600.dp) 16.dp else 12.dp
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        Text(
            text = "Your Trainer",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        if (myTrainer != null) {
            YourTrainerCard(
                trainer = myTrainer,
                onViewProfile = { onViewProfile(myTrainer.id) },
                screenWidth = screenWidth
            )
        } else {
            NoTrainerCard(screenWidth = screenWidth)
        }
    }
}

@Composable
private fun YourTrainerCard(
    trainer: com.example.fitcorpus.domain.model.Trainer,
    onViewProfile: () -> Unit,
    screenWidth: androidx.compose.ui.unit.Dp
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "card_scale"
    )
    
    val cardPadding = if (screenWidth > 600.dp) 24.dp else 16.dp
    val photoSize = if (screenWidth > 600.dp) 140.dp else 120.dp
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { /* Card click handled by button */ }
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(cardPadding),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "${trainer.name} ${trainer.surname}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    trainer.bio?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray.copy(alpha = 0.8f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                Button(
                    onClick = onViewProfile,
                    modifier = Modifier
                        .widthIn(min = 120.dp, max = 200.dp)
                        .height(44.dp),
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.fitness_green)
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "View Profile",
                        color = Color.Black,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            
            // Trainer Photo with gradient overlay
            Box(
                modifier = Modifier
                    .size(photoSize)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                if (trainer.photoUrl != null) {
                    AsyncImage(
                        model = trainer.photoUrl,
                        contentDescription = "Trainer photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        colorResource(id = R.color.fitness_green).copy(alpha = 0.3f),
                                        colorResource(id = R.color.fitness_green).copy(alpha = 0.1f)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${trainer.name.first()}${trainer.surname.first()}",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NoTrainerCard(screenWidth: androidx.compose.ui.unit.Dp) {
    val cardPadding = if (screenWidth > 600.dp) 24.dp else 16.dp
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(cardPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Henüz bir PT ile eşleşmediniz. Aşağıdaki antrenörleri keşfedin veya kod ile bağlanın.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray.copy(alpha = 0.8f),
                modifier = Modifier.padding(vertical = 16.dp),
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
private fun DiscoverTrainersHeader(
    onFilterClick: () -> Unit,
    screenWidth: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    val horizontalPadding = if (screenWidth > 600.dp) 24.dp else 16.dp
    val verticalPadding = if (screenWidth > 600.dp) 12.dp else 8.dp
    
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colorResource(id = R.color.background_dark),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = verticalPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Discover Trainers",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            FilterButton(onClick = onFilterClick)
        }
    }
}

@Composable
private fun TrainersGridSection(
    trainers: List<com.example.fitcorpus.domain.model.Trainer>,
    onTrainerClick: (String) -> Unit,
    screenWidth: androidx.compose.ui.unit.Dp
) {
    val horizontalPadding = if (screenWidth > 600.dp) 24.dp else 16.dp
    val gridColumns = if (screenWidth > 600.dp) 3 else 2
    val gridSpacing = if (screenWidth > 600.dp) 16.dp else 12.dp
    
    if (trainers.isEmpty()) {
        EmptyTrainersState(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = 32.dp)
        )
    } else {
        // Convert trainers list into rows of gridColumns
        val trainerRows = trainers.chunked(gridColumns)
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(gridSpacing)
        ) {
            trainerRows.forEach { rowTrainers ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                ) {
                    rowTrainers.forEach { trainer ->
                        TrainerCard(
                            trainer = trainer,
                            onClick = { onTrainerClick(trainer.id) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill remaining space if row is not full
                    repeat(gridColumns - rowTrainers.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterButton(onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(100),
        label = "filter_scale"
    )
    
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .scale(scale)
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                shape = CircleShape
            ),
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color.White
        )
    ) {
        Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = "Filter",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun TrainerCard(
    trainer: com.example.fitcorpus.domain.model.Trainer,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150),
        label = "trainer_card_scale"
    )
    
    Column(
        modifier = modifier
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    isPressed = true
                    onClick()
                }
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Trainer Photo with overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            if (trainer.photoUrl != null) {
                AsyncImage(
                    model = trainer.photoUrl,
                    contentDescription = "Trainer photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.fitness_green).copy(alpha = 0.4f),
                                    colorResource(id = R.color.fitness_green).copy(alpha = 0.2f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${trainer.name.first()}${trainer.surname.first()}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
        
        // Trainer Info
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "${trainer.name} ${trainer.surname.first()}.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(16.dp),
                    tint = colorResource(id = R.color.fitness_green)
                )
                Text(
                    text = String.format("%.1f/5", trainer.ratingAvg),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Medium
                )
            }
            
            Text(
                text = "From ${trainer.priceFrom} TL/mo",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun CodeEntrySection(
    onRedeem: (String) -> Unit,
    isRedeeming: Boolean,
    screenWidth: androidx.compose.ui.unit.Dp
) {
    var code by remember { mutableStateOf("") }
    val horizontalPadding = if (screenWidth > 600.dp) 24.dp else 16.dp
    val cardPadding = if (screenWidth > 600.dp) 28.dp else 24.dp
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(cardPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Have a Trainer Code?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Enter the code provided by your trainer to connect directly.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray.copy(alpha = 0.8f),
                    lineHeight = 20.sp
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    placeholder = { 
                        Text(
                            "Enter trainer code",
                            color = Color.Gray.copy(alpha = 0.6f)
                        ) 
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(9999.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.Gray.copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = Color.Gray.copy(alpha = 0.6f),
                        focusedBorderColor = colorResource(id = R.color.fitness_green),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
                
                Button(
                    onClick = { 
                        if (code.isNotBlank()) {
                            onRedeem(code)
                            code = ""
                        }
                    },
                    enabled = code.isNotBlank() && !isRedeeming,
                    modifier = Modifier
                        .height(56.dp)
                        .widthIn(min = 140.dp),
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.fitness_green),
                        disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    if (isRedeeming) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.Black,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Connect",
                            color = Color.Black,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyTrainersState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No trainers found",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Try adjusting your filters or check back later",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray.copy(alpha = 0.5f)
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
            text = "Oops!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(9999.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.fitness_green)
            ),
            modifier = Modifier
                .height(48.dp)
                .widthIn(min = 120.dp)
        ) {
            Text(
                text = "Try Again",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun StickyHeader(
    listState: LazyListState,
    screenWidth: androidx.compose.ui.unit.Dp,
    topPadding: androidx.compose.ui.unit.Dp,
    onFilterClick: () -> Unit
) {
    val firstVisibleItemIndex = remember { 
        derivedStateOf { 
            listState.firstVisibleItemIndex 
        } 
    }.value
    
    val firstVisibleItemScrollOffset = remember { 
        derivedStateOf { 
            listState.firstVisibleItemScrollOffset 
        } 
    }.value
    
    // Show sticky header when "discover_header" item (index 2) is scrolled past
    val shouldShowSticky = firstVisibleItemIndex > 2 || 
        (firstVisibleItemIndex == 2 && firstVisibleItemScrollOffset > 0)
    
    if (shouldShowSticky) {
        Box(
            modifier = Modifier
                .zIndex(10f)
                .fillMaxWidth()
                .padding(top = topPadding)
        ) {
            DiscoverTrainersHeader(
                onFilterClick = onFilterClick,
                screenWidth = screenWidth
            )
        }
    }
}

