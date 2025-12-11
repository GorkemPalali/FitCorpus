package com.example.fitcorpus.feature.pt.plans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Plan
import com.example.fitcorpus.domain.model.PlanType
import com.example.fitcorpus.domain.usecase.plan.CreatePlanUseCase
import com.example.fitcorpus.domain.usecase.plan.DeletePlanUseCase
import com.example.fitcorpus.domain.usecase.plan.GetPlansUseCase
import com.example.fitcorpus.domain.usecase.plan.UpdatePlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor(
    private val getPlansUseCase: GetPlansUseCase,
    private val createPlanUseCase: CreatePlanUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PlansUiState())
    val uiState: StateFlow<PlansUiState> = _uiState.asStateFlow()
    
    fun loadPlans(type: PlanType? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (val result = getPlansUseCase(type)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        plans = result.data
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
    
    fun createPlan(plan: Plan) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCreating = true, error = null)
            
            when (val result = createPlanUseCase(plan)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isCreating = false,
                        isSuccess = true
                    )
                    // Reload plans
                    loadPlans()
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
    
    fun updatePlan(plan: Plan) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUpdating = true, error = null)
            
            when (val result = updatePlanUseCase(plan)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isUpdating = false,
                        isSuccess = true
                    )
                    // Reload plans
                    loadPlans()
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isUpdating = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun deletePlan(planId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isDeleting = true, error = null)
            
            when (val result = deletePlanUseCase(planId)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isDeleting = false,
                        isSuccess = true
                    )
                    // Reload plans
                    loadPlans()
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isDeleting = false,
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

data class PlansUiState(
    val isLoading: Boolean = false,
    val isCreating: Boolean = false,
    val isUpdating: Boolean = false,
    val isDeleting: Boolean = false,
    val isSuccess: Boolean = false,
    val plans: List<Plan> = emptyList(),
    val error: String? = null
)







