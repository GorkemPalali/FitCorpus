package com.example.fitcorpus.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitcorpus.R
import com.example.fitcorpus.domain.model.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    selectedRole: UserRole? = null,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit = {},
    onGoogleSignInClick: () -> Unit = {},
    onICloudSignInClick: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var city by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var genderExpanded by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf(selectedRole ?: UserRole.ATHLETE) }
    
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    
    // Responsive design
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isTablet = screenWidth >= 600.dp
    val horizontalPadding = if (isTablet) 32.dp else 16.dp
    val maxContentWidth = if (isTablet) 500.dp else screenWidth
    
    val genderOptions = listOf("Male", "Female", "Other")
    
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onRegisterSuccess()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
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
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.background_dark))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = horizontalPadding)
                    .widthIn(max = maxContentWidth)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Title
                Text(
                    text = "Create Your Account",
                    fontSize = if (isTablet) 36.sp else 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Name and Surname Row
                if (isTablet) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Name Field
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Name",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = colorResource(id = R.color.fitness_green),
                                    unfocusedBorderColor = colorResource(id = R.color.gray_700),
                                    focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    cursorColor = colorResource(id = R.color.fitness_green)
                                ),
                                shape = RoundedCornerShape(50.dp),
                                isError = uiState.error != null
                            )
                        }
                        
                        // Surname Field
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Surname",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = surname,
                                onValueChange = { surname = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = colorResource(id = R.color.fitness_green),
                                    unfocusedBorderColor = colorResource(id = R.color.gray_700),
                                    focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    cursorColor = colorResource(id = R.color.fitness_green)
                                ),
                                shape = RoundedCornerShape(50.dp),
                                isError = uiState.error != null
                            )
                        }
                    }
                } else {
                    // Mobile: Stack vertically
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Name Field
                        Column {
                            Text(
                                text = "Name",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = colorResource(id = R.color.fitness_green),
                                    unfocusedBorderColor = colorResource(id = R.color.gray_700),
                                    focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    cursorColor = colorResource(id = R.color.fitness_green)
                                ),
                                shape = RoundedCornerShape(50.dp),
                                isError = uiState.error != null
                            )
                        }
                        
                        // Surname Field
                        Column {
                            Text(
                                text = "Surname",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = surname,
                                onValueChange = { surname = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = colorResource(id = R.color.fitness_green),
                                    unfocusedBorderColor = colorResource(id = R.color.gray_700),
                                    focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    cursorColor = colorResource(id = R.color.fitness_green)
                                ),
                                shape = RoundedCornerShape(50.dp),
                                isError = uiState.error != null
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Email Field
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Email",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = colorResource(id = R.color.fitness_green),
                            unfocusedBorderColor = colorResource(id = R.color.gray_700),
                            focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                            unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                            cursorColor = colorResource(id = R.color.fitness_green)
                        ),
                        shape = RoundedCornerShape(50.dp),
                        isError = uiState.error != null
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Password Field
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Password",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = colorResource(id = R.color.fitness_green),
                            unfocusedBorderColor = colorResource(id = R.color.gray_700),
                            focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                            unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                            cursorColor = colorResource(id = R.color.fitness_green)
                        ),
                        shape = RoundedCornerShape(50.dp),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    tint = colorResource(id = R.color.gray_400)
                                )
                            }
                        },
                        isError = uiState.error != null
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Confirm Password Field
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Confirm Password",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = colorResource(id = R.color.fitness_green),
                            unfocusedBorderColor = colorResource(id = R.color.gray_700),
                            focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                            unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                            cursorColor = colorResource(id = R.color.fitness_green)
                        ),
                        shape = RoundedCornerShape(50.dp),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                                    tint = colorResource(id = R.color.gray_400)
                                )
                            }
                        },
                        isError = uiState.error != null || (confirmPassword.isNotEmpty() && password != confirmPassword)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // City and Gender Row
                if (isTablet) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // City Field
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "City",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = city,
                                onValueChange = { city = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = colorResource(id = R.color.fitness_green),
                                    unfocusedBorderColor = colorResource(id = R.color.gray_700),
                                    focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    cursorColor = colorResource(id = R.color.fitness_green)
                                ),
                                shape = RoundedCornerShape(50.dp),
                                isError = uiState.error != null
                            )
                        }
                        
                        // Gender Dropdown
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Gender",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            ExposedDropdownMenuBox(
                                expanded = genderExpanded,
                                onExpandedChange = { genderExpanded = !genderExpanded }
                            ) {
                                OutlinedTextField(
                                    value = gender,
                                    onValueChange = { },
                                    readOnly = true,
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth(),
                                    singleLine = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        focusedBorderColor = colorResource(id = R.color.fitness_green),
                                        unfocusedBorderColor = colorResource(id = R.color.gray_700),
                                        focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                        unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                        cursorColor = colorResource(id = R.color.fitness_green)
                                    ),
                                    shape = RoundedCornerShape(50.dp),
                                    isError = uiState.error != null
                                )
                                
                                ExposedDropdownMenu(
                                    expanded = genderExpanded,
                                    onDismissRequest = { genderExpanded = false },
                                    modifier = Modifier.background(colorResource(id = R.color.gray_800))
                                ) {
                                    genderOptions.forEach { option ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = option,
                                                    color = Color.White
                                                )
                                            },
                                            onClick = {
                                                gender = option
                                                genderExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Mobile: Stack vertically
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // City Field
                        Column {
                            Text(
                                text = "City",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = city,
                                onValueChange = { city = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = colorResource(id = R.color.fitness_green),
                                    unfocusedBorderColor = colorResource(id = R.color.gray_700),
                                    focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                    cursorColor = colorResource(id = R.color.fitness_green)
                                ),
                                shape = RoundedCornerShape(50.dp),
                                isError = uiState.error != null
                            )
                        }
                        
                        // Gender Dropdown
                        Column {
                            Text(
                                text = "Gender",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            ExposedDropdownMenuBox(
                                expanded = genderExpanded,
                                onExpandedChange = { genderExpanded = !genderExpanded }
                            ) {
                                OutlinedTextField(
                                    value = gender,
                                    onValueChange = { },
                                    readOnly = true,
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth(),
                                    singleLine = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        focusedBorderColor = colorResource(id = R.color.fitness_green),
                                        unfocusedBorderColor = colorResource(id = R.color.gray_700),
                                        focusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                        unfocusedContainerColor = colorResource(id = R.color.gray_800).copy(alpha = 0.5f),
                                        cursorColor = colorResource(id = R.color.fitness_green)
                                    ),
                                    shape = RoundedCornerShape(50.dp),
                                    isError = uiState.error != null
                                )
                                
                                ExposedDropdownMenu(
                                    expanded = genderExpanded,
                                    onDismissRequest = { genderExpanded = false },
                                    modifier = Modifier.background(colorResource(id = R.color.gray_800))
                                ) {
                                    genderOptions.forEach { option ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = option,
                                                    color = Color.White
                                                )
                                            },
                                            onClick = {
                                                gender = option
                                                genderExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                
                uiState.error?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Passwords do not match",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Sign Up Button
                Button(
                    onClick = {
                        if (password == confirmPassword) {
                            viewModel.register(name, surname, email, password, role, city, gender)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !uiState.isLoading && password == confirmPassword && password.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.fitness_green),
                        contentColor = colorResource(id = R.color.background_dark),
                        disabledContainerColor = colorResource(id = R.color.fitness_green).copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = colorResource(id = R.color.background_dark)
                        )
                    } else {
                        Text(
                            text = "Sign Up",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // OR Divider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = colorResource(id = R.color.gray_700),
                        thickness = 1.dp
                    )
                    Text(
                        text = "or sign up with",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.gray_500),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = colorResource(id = R.color.gray_700),
                        thickness = 1.dp
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Social Sign Up Buttons
                // Google Sign Up
                OutlinedButton(
                    onClick = onGoogleSignInClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        colorResource(id = R.color.gray_700)
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Google logo placeholder
                        Text(
                            text = "G",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Continue with Google",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // iCloud Sign Up
                OutlinedButton(
                    onClick = onICloudSignInClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        colorResource(id = R.color.gray_700)
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Apple logo placeholder
                        Text(
                            text = "🍎",
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Continue with iCloud",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Log In Link
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray_400)
                    )
                    Text(
                        text = "Log In",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.fitness_green),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { onNavigateToLogin() }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
