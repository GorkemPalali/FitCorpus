package com.example.fitcorpus.feature.pt.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Package
import com.example.fitcorpus.domain.usecase.trainer.GetTrainerPackagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackagesViewModel @Inject constructor(
    private val getTrainerPackagesUseCase: GetTrainerPackagesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PackagesUiState())
    val uiState: StateFlow<PackagesUiState> = _uiState.asStateFlow()
    
    private var trainerId: String? = null
    
    fun loadPackages(trainerId: String) {
        this.trainerId = trainerId
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = getTrainerPackagesUseCase(trainerId)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        packages = result.data
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
    
    fun refreshPackages() {
        trainerId?.let { loadPackages(it) }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class PackagesUiState(
    val isLoading: Boolean = false,
    val packages: List<Package> = emptyList(),
    val error: String? = null
)







