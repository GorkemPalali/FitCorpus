package com.example.fitcorpus.ui.screens.athlete.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitcorpus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filters: FilterState,
    onDismiss: () -> Unit,
    onApplyFilters: (FilterState) -> Unit,
    onClearFilters: () -> Unit
) {
    // Local state for filter selections
    var selectedGender by remember(filters) { 
        mutableStateOf(filters.gender ?: "Tümü") 
    }
    var selectedCity by remember(filters) { 
        mutableStateOf(filters.location ?: "Tüm Şehirler") 
    }
    var selectedMode by remember(filters) { 
        mutableStateOf(filters.mode ?: "Tümü") 
    }
    var selectedPriceSort by remember(filters) { 
        mutableStateOf(
            when (filters.sort) {
                "price_asc" -> "Düşükten Yükseğe"
                "price_desc" -> "Yüksekten Düşüğe"
                else -> null
            }
        )
    }
    var selectedRatingSort by remember(filters) { 
        mutableStateOf(
            when (filters.sort) {
                "rating_asc" -> "Düşükten Yükseğe"
                "rating_desc" -> "Yüksekten Düşüğe"
                else -> null
            }
        )
    }
    
    var cityExpanded by remember { mutableStateOf(false) }
    
    val cities = listOf("Tüm Şehirler", "İstanbul", "Ankara", "İzmir", "Bursa", "Antalya", "Adana")
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = colorResource(id = R.color.background_dark),
        dragHandle = {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.Gray.copy(alpha = 0.5f))
            )
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            // Header with close button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Filtrele & Sırala",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
                
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Kapat",
                        tint = Color.Gray.copy(alpha = 0.7f)
                    )
                }
            }
            
            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Gender Filter
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Cinsiyet",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        GenderButton(
                            text = "Tümü",
                            isSelected = selectedGender == "Tümü",
                            onClick = { selectedGender = "Tümü" },
                            modifier = Modifier.weight(1f)
                        )
                        GenderButton(
                            text = "Kadın",
                            isSelected = selectedGender == "Kadın",
                            onClick = { selectedGender = "Kadın" },
                            modifier = Modifier.weight(1f)
                        )
                        GenderButton(
                            text = "Erkek",
                            isSelected = selectedGender == "Erkek",
                            onClick = { selectedGender = "Erkek" },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // City Filter
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Şehir",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    
                    ExposedDropdownMenuBox(
                        expanded = cityExpanded,
                        onExpandedChange = { cityExpanded = !cityExpanded }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(9999.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                .clickable { cityExpanded = !cityExpanded }
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedCity,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                                Icon(
                                    imageVector = Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = Color.Gray.copy(alpha = 0.7f)
                                )
                            }
                        }
                        
                        ExposedDropdownMenu(
                            expanded = cityExpanded,
                            onDismissRequest = { cityExpanded = false },
                            modifier = Modifier.background(colorResource(id = R.color.background_dark))
                        ) {
                            cities.forEach { city ->
                                DropdownMenuItem(
                                    text = { 
                                        Text(
                                            text = city,
                                            color = Color.White
                                        ) 
                                    },
                                    onClick = {
                                        selectedCity = city
                                        cityExpanded = false
                                    },
                                    colors = MenuDefaults.itemColors(
                                        textColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
                
                // Mode Filter (Face-to-Face / Online)
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Yüz Yüze / Online",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    
                    ModeSegmentedControl(
                        selectedMode = selectedMode,
                        onModeSelected = { selectedMode = it }
                    )
                }
                
                // Divider
                HorizontalDivider(
                    color = Color.Gray.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
                
                // Price Sort
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Fiyata Göre Sırala",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SortButton(
                            text = "Düşükten Yükseğe",
                            isSelected = selectedPriceSort == "Düşükten Yükseğe",
                            onClick = { 
                                selectedPriceSort = "Düşükten Yükseğe"
                                selectedRatingSort = null
                            },
                            modifier = Modifier.weight(1f)
                        )
                        SortButton(
                            text = "Yüksekten Düşüğe",
                            isSelected = selectedPriceSort == "Yüksekten Düşüğe",
                            onClick = { 
                                selectedPriceSort = "Yüksekten Düşüğe"
                                selectedRatingSort = null
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Rating Sort
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Puanına Göre Sırala",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SortButton(
                            text = "Düşükten Yükseğe",
                            isSelected = selectedRatingSort == "Düşükten Yükseğe",
                            onClick = { 
                                selectedRatingSort = "Düşükten Yükseğe"
                                selectedPriceSort = null
                            },
                            modifier = Modifier.weight(1f)
                        )
                        SortButton(
                            text = "Yüksekten Düşüğe",
                            isSelected = selectedRatingSort == "Yüksekten Düşüğe",
                            onClick = { 
                                selectedRatingSort = "Yüksekten Düşüğe"
                                selectedPriceSort = null
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            // Footer Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        selectedGender = "Tümü"
                        selectedCity = "Tüm Şehirler"
                        selectedMode = "Tümü"
                        selectedPriceSort = null
                        selectedRatingSort = null
                        onClearFilters()
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                ) {
                    Text(
                        text = "Temizle",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Button(
                    onClick = {
                        val newFilters = FilterState(
                            gender = if (selectedGender == "Tümü") null else selectedGender,
                            location = if (selectedCity == "Tüm Şehirler") null else selectedCity,
                            mode = if (selectedMode == "Tümü") null else selectedMode,
                            sort = when {
                                selectedPriceSort == "Düşükten Yükseğe" -> "price_asc"
                                selectedPriceSort == "Yüksekten Düşüğe" -> "price_desc"
                                selectedRatingSort == "Düşükten Yükseğe" -> "rating_asc"
                                selectedRatingSort == "Yüksekten Düşüğe" -> "rating_desc"
                                else -> null
                            }
                        )
                        onApplyFilters(newFilters)
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.fitness_green)
                    )
                ) {
                    Text(
                        text = "Uygula",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(9999.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                colorResource(id = R.color.fitness_green)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            }
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SortButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(9999.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                colorResource(id = R.color.fitness_green)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            }
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ModeSegmentedControl(
    selectedMode: String,
    onModeSelected: (String) -> Unit
) {
    val modes = listOf("Tümü", "Yüz Yüze", "Online")
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(9999.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            modes.forEach { mode ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(9999.dp))
                        .background(
                            if (selectedMode == mode) {
                                Color.White
                            } else {
                                Color.Transparent
                            }
                        )
                        .clickable { onModeSelected(mode) }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mode,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedMode == mode) {
                            Color.Black
                        } else {
                            Color.Gray.copy(alpha = 0.7f)
                        }
                    )
                }
            }
        }
    }
}
