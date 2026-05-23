package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AltusDao {
    // Water logs
    @Query("SELECT * FROM water_log WHERE date = :date")
    fun getWaterLog(date: String): Flow<WaterLog?>

    @Query("SELECT * FROM water_log WHERE date = :date")
    suspend fun getWaterLogOnce(date: String): WaterLog?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterLog(waterLog: WaterLog)

    // Weight logs
    @Query("SELECT * FROM weight_log ORDER BY date ASC")
    fun getAllWeightLogs(): Flow<List<WeightLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeightLog(weightLog: WeightLog)

    @Query("DELETE FROM weight_log WHERE id = :id")
    suspend fun deleteWeightLogById(id: Long)

    // Meal items
    @Query("SELECT * FROM meal_item WHERE dayIndex = :dayIndex ORDER BY id ASC")
    fun getMealItemsForDay(dayIndex: Int): Flow<List<MealItem>>

    @Query("SELECT * FROM meal_item ORDER BY dayIndex ASC, id ASC")
    fun getAllMealItems(): Flow<List<MealItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealItems(mealItems: List<MealItem>)

    @Query("UPDATE meal_item SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateMealItemCompletion(id: Long, isCompleted: Boolean)

    @Query("DELETE FROM meal_item")
    suspend fun clearAllMealItems()
}
