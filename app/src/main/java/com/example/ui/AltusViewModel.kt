package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AltusRepository
import com.example.data.MealItem
import com.example.data.WaterLog
import com.example.data.WeightLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AltusViewModel(private val repository: AltusRepository) : ViewModel() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val todayDateString: String = dateFormat.format(Date())

    // UI Date Label
    val currentDateLabel: String = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())

    // Tracks which day's meal plan is currently viewed (0 to 6)
    val todayIndex: Int = getTodayIndexValue()
    private val _selectedDayIndex = MutableStateFlow(todayIndex)
    val selectedDayIndex: StateFlow<Int> = _selectedDayIndex.asStateFlow()

    // Language preference: "en" or "ur" (Urdu/Hindi)
    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    // Plan duration selection: 3, 5, or 7 days
    private val _planDurationDays = MutableStateFlow(7)
    val planDurationDays: StateFlow<Int> = _planDurationDays.asStateFlow()

    // Profile parameters
    private val _userGender = MutableStateFlow("Female")
    val userGender: StateFlow<String> = _userGender.asStateFlow()

    private val _userWeight = MutableStateFlow(64.2)
    val userWeight: StateFlow<Double> = _userWeight.asStateFlow()

    private val _userHeight = MutableStateFlow(165)
    val userHeight: StateFlow<Int> = _userHeight.asStateFlow()

    private val _userBodyLook = MutableStateFlow("Average")
    val userBodyLook: StateFlow<String> = _userBodyLook.asStateFlow()

    private val _userClothSize = MutableStateFlow("M")
    val userClothSize: StateFlow<String> = _userClothSize.asStateFlow()

    private val _weightReductionGoalKg = MutableStateFlow(5.0)
    val weightReductionGoalKg: StateFlow<Double> = _weightReductionGoalKg.asStateFlow()

    fun updateLanguage(lang: String) {
        _currentLanguage.value = lang
    }

    fun updatePlanDuration(days: Int) {
        if (days in listOf(3, 5, 7)) {
            _planDurationDays.value = days
            if (_selectedDayIndex.value >= days) {
                _selectedDayIndex.value = 0
            }
        }
    }

    fun updateProfile(
        gender: String,
        weight: Double,
        height: Int,
        bodyLook: String,
        clothSize: String,
        goal: Double
    ) {
        _userGender.value = gender
        _userWeight.value = weight
        _userHeight.value = height
        _userBodyLook.value = bodyLook
        _userClothSize.value = clothSize
        _weightReductionGoalKg.value = goal
    }

    fun substituteMealVegetable(meal: MealItem, replacementVeg: String) {
        viewModelScope.launch {
            val targetVegs = listOf("Spinach", "Broccoli", "Cabbage", "Zucchini", "Cucumber", "Celery", "Lettuce", "Cauliflower", "Kale", "Peas", "Fennel")
            var newName = meal.name
            var newDetails = meal.details
            
            for (veg in targetVegs) {
                if (meal.name.contains(veg, ignoreCase = true)) {
                    newName = meal.name.replace(veg, replacementVeg, ignoreCase = true)
                }
                if (meal.details.contains(veg, ignoreCase = true)) {
                    newDetails = meal.details.replace(veg, replacementVeg, ignoreCase = true)
                }
            }
            
            if (newName == meal.name) {
                newName = "${meal.name} (Substituted with $replacementVeg)"
            }
            
            val updated = meal.copy(name = newName, details = newDetails)
            repository.insertMealItems(listOf(updated))
        }
    }

    // Observable states
    val waterToday: StateFlow<WaterLog?> = repository.getWaterLog(todayDateString)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val weightHistory: StateFlow<List<WeightLog>> = repository.allWeightLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allMealItems: StateFlow<List<MealItem>> = repository.allMealItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Celebration indicator
    private val _showWaterCelebration = MutableStateFlow(false)
    val showWaterCelebration: StateFlow<Boolean> = _showWaterCelebration.asStateFlow()

    init {
        // Seed standard meals on application launch if database starting fresh
        viewModelScope.launch {
            repository.seedMealsIfEmpty()
            // Add a placeholder weight log if none exists to populate the starting graph nicely
            repository.allWeightLogs.collect { logs ->
                if (logs.isEmpty()) {
                    val baseTime = System.currentTimeMillis()
                    // Create some default historic entries for visual richness
                    val oneDay = 24 * 60 * 60 * 1000L
                    repository.addWeightLog(72.5, baseTime - 4 * oneDay)
                    repository.addWeightLog(71.8, baseTime - 3 * oneDay)
                    repository.addWeightLog(71.2, baseTime - 2 * oneDay)
                    repository.addWeightLog(70.9, baseTime - 1 * oneDay)
                    repository.addWeightLog(70.5, baseTime)
                }
            }
        }
    }

    fun selectDay(index: Int) {
        _selectedDayIndex.value = index
    }

    fun incrementWater() {
        viewModelScope.launch {
            val currentAmount = waterToday.value?.glasses ?: 0
            val targetAmount = currentAmount + 1
            repository.logWater(todayDateString, targetAmount)
            
            // Daily goal celebration at exactly 8 glasses
            if (targetAmount >= 8 && currentAmount < 8) {
                _showWaterCelebration.value = true
            }
        }
    }

    fun decrementWater() {
        viewModelScope.launch {
            val currentAmount = waterToday.value?.glasses ?: 0
            if (currentAmount > 0) {
                repository.logWater(todayDateString, currentAmount - 1)
            }
        }
    }

    fun dismissCelebration() {
        _showWaterCelebration.value = false
    }

    fun toggleMealCompletion(itemId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateMealItemCompletion(itemId, isCompleted)
        }
    }

    fun addWeightEntry(weight: Double) {
        if (weight <= 0) return
        viewModelScope.launch {
            repository.addWeightLog(weight, System.currentTimeMillis())
        }
    }

    fun removeWeightEntry(id: Long) {
        viewModelScope.launch {
            repository.deleteWeightLog(id)
        }
    }

    fun resetMeals() {
        viewModelScope.launch {
            repository.resetAllMeals()
        }
    }

    private fun getTodayIndexValue(): Int {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return when (dayOfWeek) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }
    }
}
