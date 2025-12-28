package com.example.fitcorpus.ui.screens.pt.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Plan
import com.example.fitcorpus.domain.usecase.plan.GetPlansUseCase
import com.example.fitcorpus.domain.usecase.student.AssignPlanUseCase
import com.example.fitcorpus.domain.usecase.student.GetStudentLogsUseCase
import com.example.fitcorpus.domain.usecase.student.GetStudentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getStudentLogsUseCase: GetStudentLogsUseCase,
    private val assignPlanUseCase: AssignPlanUseCase,
    private val getPlansUseCase: GetPlansUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StudentsUiState())
    val uiState: StateFlow<StudentsUiState> = _uiState.asStateFlow()
    
    fun loadStudents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = getStudentsUseCase()) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        students = result.data
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
    
    fun loadStudentLogs(athleteId: String, from: LocalDate? = null, to: LocalDate? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingLogs = true, error = null)
            
            when (val result = getStudentLogsUseCase(athleteId, from, to)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingLogs = false,
                        selectedStudentLogs = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingLogs = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun loadPlansForAssignment(planType: com.example.fitcorpus.domain.model.PlanType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingPlans = true)
            
            when (val result = getPlansUseCase(planType)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingPlans = false,
                        availablePlans = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingPlans = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun assignPlan(athleteId: String, planId: String, date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAssigning = true, error = null)
            
            when (val result = assignPlanUseCase(athleteId, planId, date)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isAssigning = false,
                        isSuccess = true
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isAssigning = false,
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

data class StudentsUiState(
    val isLoading: Boolean = false,
    val isLoadingLogs: Boolean = false,
    val isLoadingPlans: Boolean = false,
    val isAssigning: Boolean = false,
    val isSuccess: Boolean = false,
    val students: List<com.example.fitcorpus.domain.model.Student> = emptyList(),
    val selectedStudentLogs: com.example.fitcorpus.domain.repository.StudentLogs? = null,
    val availablePlans: List<Plan> = emptyList(),
    val error: String? = null
)







