package com.example.fitcorpus.feature.pt.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.usecase.auth.GetCurrentUserUseCase
import com.example.fitcorpus.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PTSettingsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PTSettingsUiState())
    val uiState: StateFlow<PTSettingsUiState> = _uiState.asStateFlow()
    
    init {
        loadUserProfile()
    }
    
    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            when (val result = getCurrentUserUseCase()) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        user = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoggingOut = true)
            
            when (val result = logoutUseCase()) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoggingOut = false,
                        isLoggedOut = true
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoggingOut = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class PTSettingsUiState(
    val isLoading: Boolean = false,
    val isLoggingOut: Boolean = false,
    val isLoggedOut: Boolean = false,
    val user: com.example.fitcorpus.domain.model.User? = null,
    val error: String? = null
)







