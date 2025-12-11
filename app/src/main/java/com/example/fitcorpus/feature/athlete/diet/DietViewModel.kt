package com.example.fitcorpus.feature.athlete.diet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.DietEntry
import com.example.fitcorpus.domain.usecase.diet.CreateDietEntryUseCase
import com.example.fitcorpus.domain.usecase.diet.GetDietEntriesUseCase
import com.example.fitcorpus.domain.usecase.diet.SyncDietEntriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val getDietEntriesUseCase: GetDietEntriesUseCase,
    private val createDietEntryUseCase: CreateDietEntryUseCase,
    private val syncDietEntriesUseCase: SyncDietEntriesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DietUiState())
    val uiState: StateFlow<DietUiState> = _uiState.asStateFlow()
    
    fun loadDietEntries(selectedDate: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                selectedDate = selectedDate
            )
            
            when (val result = getDietEntriesUseCase(selectedDate, selectedDate)) {
                is Result.Success -> {
                    val entries = result.data
                    val totalCalories = entries.sumOf { it.calories }
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        dietEntries = entries,
                        consumedCalories = totalCalories,
                        targetCalories = 2000 // TODO: Get from user settings
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
    
    fun createDietEntry(entry: DietEntry) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCreating = true, error = null)
            
            when (val result = createDietEntryUseCase(entry)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isCreating = false,
                        isSuccess = true
                    )
                    // Reload entries
                    loadDietEntries(_uiState.value.selectedDate)
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isCreating = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun syncDietEntries() {
        viewModelScope.launch {
            syncDietEntriesUseCase()
            loadDietEntries(_uiState.value.selectedDate)
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null, isSuccess = false)
    }
}

data class DietUiState(
    val isLoading: Boolean = false,
    val isCreating: Boolean = false,
    val isSuccess: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
    val dietEntries: List<DietEntry> = emptyList(),
    val consumedCalories: Int = 0,
    val targetCalories: Int = 2000,
    val error: String? = null
)







