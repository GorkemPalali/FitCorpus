package com.example.fitcorpus.ui.screens.athlete.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitcorpus.domain.model.Exercise
import com.example.fitcorpus.domain.model.ExerciseType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AthleteHomeViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(AthleteHomeUiState())
    val uiState: StateFlow<AthleteHomeUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // TODO: Load actual data from repository
            // For now, using mock data
            val workoutExercises = getMockWorkoutExercises()
            val warmupExercises = getMockWarmupExercises()
            
            // Calculate calories
            val targetCalories = 2000 // TODO: Get from user settings
            val consumedCalories = 1350 // TODO: Get from today's diet entries
            val caloriesLeft = (targetCalories - consumedCalories).coerceAtLeast(0)
            val caloriesProgress = if (targetCalories > 0) {
                (consumedCalories.toFloat() / targetCalories.toFloat()).coerceIn(0f, 1f)
            } else 0f
            
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                workoutExercises = workoutExercises,
                warmupExercises = warmupExercises,
                targetCalories = targetCalories,
                consumedCalories = consumedCalories,
                caloriesLeft = caloriesLeft,
                caloriesProgress = caloriesProgress,
                userName = "Alex" // TODO: Get from user profile
            )
        }
    }
    
    private fun getMockWorkoutExercises(): List<Exercise> {
        return listOf(
            Exercise(
                id = "1",
                name = "Barbell Squat",
                category = "Legs",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDLCRPnxEvASX7ypODB1GJyyr-77Ftpk6NUF_VSONyy92sR6yuP_DsqA3VE3HbhTCvYNDVFvfXICDUhxN8BW0BAzYRyOrA60Ke7X18J_OHYdTBwyK9pMqoiUla49Dp30V6NGKzOVGnnpairbVfHmoVVFkILkMaofuZqeU1TTqe4EFoPxKGxN7FMttKaNDvPIr24qbusvPaTTZ73GM59uaDs_h1qaYQK2Mt-Yqk75zpu9JqLNzfUADi1Di5-pHgBFReUegCY_gKkZ_0",
                type = ExerciseType.WORKOUT
            ),
            Exercise(
                id = "2",
                name = "Bench Press",
                category = "Chest",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBWXvOs8_UWkACZsJF8pJTGf4YeG6JPqjWm88yrXahuKB8JivZ3-ArqRtww1PTafi5NysCdByn0dIe2Sth-yk9XO-QLlzrYjIeLUwsSYOGE_pNlLCQM8ZDwYFeJrwDYZYhJZj47f9l1tHB6kPqmVoBXkfoD8fYTTsZUOxQPamAflsx1oEfXGz7U-_kaAbweJD_M5qlauQEXtSowUhbdRj6HmGBF2xnpai6iC9Ra-pqT6sOAOrVGYu-uwYtp6ieqWexqpKH6xkpvIbk",
                type = ExerciseType.WORKOUT
            ),
            Exercise(
                id = "3",
                name = "Deadlift",
                category = "Back",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCd9QQO5bwDRTB0Aljo9p42ANf0hG4Ln1lMj0G_JJ_13Yt0EBAxrAbXmGcfsrOL9ARpZsUVsmViqvjo0EJxSIWHAJBMbOHdvZgAOqWLXJz3vytSk9g3ZfQTJyyzH5_9jF0EFhy3v00F7olgurgmKYVA3DIwWy9g4MAOfT7bFIXel_Lk1MRAGE4kutv9kzkspQ8fYO0aG1RQxNN4PHHtYnJyavfMKDTWFA4j4PEPT1daeAeTNjQMVchHjpw23CLgKmQl7RnJm8TeU7A",
                type = ExerciseType.WORKOUT
            )
        )
    }
    
    private fun getMockWarmupExercises(): List<Exercise> {
        return listOf(
            Exercise(
                id = "4",
                name = "Jumping Jacks",
                category = "Full Body",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDDJ4Q5uh8_V0yf8Gl8M5eLIyqAUBMdOG1s1j06aifZm2sHG8WfhL3es33GviP5GVPaSugf_kkX-uA9DzBX3kMwmDXntbQhT7XNiWHPhMxtbaC_Cx6pK-Qan0ZfhgNsypRomhxEzz3GrVSEorte24ElVZpaO8SDPetiIHb7zs8t-6bvNtzHbNtMZclVWGF1hiLyD2EeZ8TJbkyA7zf1A3QcpTw59oHiRWi7uEldKGVkLF-sQMuabihhgFt4CLLBbSqyEhCm5fzmMeI",
                type = ExerciseType.WARMUP
            ),
            Exercise(
                id = "5",
                name = "Arm Circles",
                category = "Shoulders",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCVfS5hE3U6caAPp2SswU2ebfsbK7o5pbhvmCSGXtOmPbdWSpbZ8SVBg8PC-wzLh7VhWANOWiUYUjOASiJlwPHkagdU1uUgfKsn2RK6PbW9ATVcBjyuP2238RF2NbsovZavpflhXmAVooHHDUPLiIn5hiJKiYCA8DcOX26-xlY3eqbySrJp0tZ_WRXnMqvY6SCxXajNBgut9s0sWvSybAd42_eH9nTJAr4MAkogJQcvkwWtqyffCEANn1eTf-p9Sx8xF4af1rlUeXc",
                type = ExerciseType.WARMUP
            ),
            Exercise(
                id = "6",
                name = "Leg Swings",
                category = "Hips & Legs",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDol9OUnBtMfqQrdbnysW5jCm8tl9QGz6gfHRllQUtzXnW3wPx9YoEchd64TuzlJNZ8WfVNbki7SsV3W7oI1PfG95H1uuLQxy_J8XY_17kjWjnOdh_drns6sV1rXOO7B9tEJB8sQ5nUb9SwlKpo5CzSLpHUdlAhxLdCClEzUrjzAoaEHbcRh4GMOcB8tQJB5_AIFPh32HvEJbdHU6Lry8yhc1OqWKlvxxPzyrHi1wK384EYll5KZdyHiE-QYAgKRJzaEPFiIHZPit0",
                type = ExerciseType.WARMUP
            )
        )
    }
    
    fun refresh() {
        loadData()
    }
}

data class AthleteHomeUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userPhotoUrl: String? = null,
    val targetCalories: Int = 2000,
    val consumedCalories: Int = 0,
    val caloriesLeft: Int = 2000,
    val caloriesProgress: Float = 0f,
    val workoutExercises: List<Exercise> = emptyList(),
    val warmupExercises: List<Exercise> = emptyList()
)
