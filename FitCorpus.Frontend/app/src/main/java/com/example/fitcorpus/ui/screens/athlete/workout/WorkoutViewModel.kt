package com.example.fitcorpus.ui.screens.athlete.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.WorkoutEntry
import com.example.fitcorpus.domain.usecase.workout.CreateWorkoutUseCase
import com.example.fitcorpus.domain.usecase.workout.GetWorkoutsUseCase
import com.example.fitcorpus.domain.usecase.workout.SyncWorkoutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val createWorkoutUseCase: CreateWorkoutUseCase,
    private val syncWorkoutsUseCase: SyncWorkoutsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()
    
    fun loadWorkouts(selectedDate: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                selectedDate = selectedDate
            )
            
            when (val result = getWorkoutsUseCase(selectedDate, selectedDate)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        workouts = result.data
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
    
    fun createWorkout(workout: WorkoutEntry) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCreating = true, error = null)
            
            when (val result = createWorkoutUseCase(workout)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isCreating = false,
                        isSuccess = true
                    )
                    // Reload workouts
                    loadWorkouts(_uiState.value.selectedDate)
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
    
    fun syncWorkouts() {
        viewModelScope.launch {
            syncWorkoutsUseCase()
            loadWorkouts(_uiState.value.selectedDate)
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null, isSuccess = false)
    }
}

data class WorkoutUiState(
    val isLoading: Boolean = false,
    val isCreating: Boolean = false,
    val isSuccess: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
    val workouts: List<WorkoutEntry> = emptyList(),
    val error: String? = null
)







