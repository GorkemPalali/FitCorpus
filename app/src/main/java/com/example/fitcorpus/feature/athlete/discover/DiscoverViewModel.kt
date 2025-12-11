package com.example.fitcorpus.feature.athlete.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.usecase.purchase.RedeemCodeUseCase
import com.example.fitcorpus.domain.usecase.trainer.GetTrainerByIdUseCase
import com.example.fitcorpus.domain.usecase.trainer.GetTrainerPackagesUseCase
import com.example.fitcorpus.domain.usecase.trainer.GetTrainersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrainersUseCase: GetTrainersUseCase,
    private val getTrainerPackagesUseCase: GetTrainerPackagesUseCase,
    private val redeemCodeUseCase: RedeemCodeUseCase,
    private val getTrainerByIdUseCase: GetTrainerByIdUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DiscoverUiState())
    val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()
    
    init {
        loadTrainers()
    }
    
    fun loadTrainers(
        location: String? = null,
        priceMin: Int? = null,
        priceMax: Int? = null,
        gender: String? = null,
        mode: String? = null,
        sort: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = getTrainersUseCase(
                location, priceMin, priceMax, gender, mode, sort, 0, 20
            )) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        trainers = result.data
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
    
    fun applyFilters(filters: FilterState) {
        _uiState.value = _uiState.value.copy(filters = filters)
        loadTrainers(
            location = filters.location,
            priceMin = filters.priceMin,
            priceMax = filters.priceMax,
            gender = filters.gender,
            mode = filters.mode,
            sort = filters.sort
        )
    }
    
    fun clearFilters() {
        val emptyFilters = FilterState()
        _uiState.value = _uiState.value.copy(filters = emptyFilters)
        loadTrainers()
    }
    
    fun loadTrainerPackages(trainerId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingPackages = true)
            
            when (val result = getTrainerPackagesUseCase(trainerId)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingPackages = false,
                        selectedTrainerPackages = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingPackages = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun redeemCode(code: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRedeeming = true, error = null)
            
            when (val result = redeemCodeUseCase(code)) {
                is Result.Success -> {
                    // After redeeming code, load trainer info
                    val match = result.data
                    when (val trainerResult = getTrainerByIdUseCase(match.trainerId)) {
                        is Result.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isRedeeming = false,
                                isCodeRedeemed = true,
                                myTrainer = trainerResult.data
                            )
                        }
                        is Result.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isRedeeming = false,
                                error = trainerResult.exception.message
                            )
                        }
                        else -> {
                            _uiState.value = _uiState.value.copy(isRedeeming = false)
                        }
                    }
                    // Reload trainers to show "My Trainer" section
                    loadTrainers()
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isRedeeming = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun loadMyTrainer() {
        viewModelScope.launch {
            // TODO: Get active match for current user
            // For now, we'll check if there's a match and load trainer info
            // This should be implemented with a GetMyMatchUseCase or similar
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class DiscoverUiState(
    val isLoading: Boolean = false,
    val isLoadingPackages: Boolean = false,
    val isRedeeming: Boolean = false,
    val isCodeRedeemed: Boolean = false,
    val trainers: List<com.example.fitcorpus.domain.model.Trainer> = emptyList(),
    val selectedTrainerPackages: List<com.example.fitcorpus.domain.model.Package> = emptyList(),
    val myTrainer: com.example.fitcorpus.domain.model.Trainer? = null,
    val filters: FilterState = FilterState(),
    val error: String? = null
)

data class FilterState(
    val location: String? = null,
    val gender: String? = null,
    val priceMin: Int? = null,
    val priceMax: Int? = null,
    val mode: String? = null,
    val sort: String? = null
) {
    val hasActiveFilters: Boolean
        get() = location != null || gender != null || priceMin != null || 
                priceMax != null || mode != null || sort != null
}







