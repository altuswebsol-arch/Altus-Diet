package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_log")
data class WaterLog(
    @PrimaryKey val date: String, // format "yyyy-MM-dd"
    val glasses: Int
)

@Entity(tableName = "weight_log")
data class WeightLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Long, // Epoch timestamp in milliseconds
    val weight: Double
)

@Entity(tableName = "meal_item")
data class MealItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dayIndex: Int, // 0 to 6 (Monday to Sunday)
    val mealType: String, // "Breakfast", "Lunch", "Dinner", "Snack"
    val name: String,
    val costEst: String,
    val details: String,
    val isCompleted: Boolean = false
)
