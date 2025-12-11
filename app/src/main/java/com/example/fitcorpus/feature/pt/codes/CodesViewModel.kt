package com.example.fitcorpus.feature.pt.codes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.usecase.code.GetCodesUseCase
import com.example.fitcorpus.domain.usecase.code.PurchaseCodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodesViewModel @Inject constructor(
    private val getCodesUseCase: GetCodesUseCase,
    private val purchaseCodesUseCase: PurchaseCodesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CodesUiState())
    val uiState: StateFlow<CodesUiState> = _uiState.asStateFlow()
    
    init {
        loadCodes()
    }
    
    fun loadCodes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = getCodesUseCase()) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        codes = result.data
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
    
    fun purchaseCodes(count: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isPurchasing = true, error = null)
            
            when (val result = purchaseCodesUseCase(count)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isPurchasing = false,
                        isSuccess = true
                    )
                    // Reload codes
                    loadCodes()
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isPurchasing = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null, isSuccess = false)
    }
}

data class CodesUiState(
    val isLoading: Boolean = false,
    val isPurchasing: Boolean = false,
    val isSuccess: Boolean = false,
    val codes: List<com.example.fitcorpus.domain.model.OnboardingCode> = emptyList(),
    val error: String? = null
)







