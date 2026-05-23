package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WaterLog::class, WeightLog::class, MealItem::class], version = 1, exportSchema = false)
abstract class AltusDatabase : RoomDatabase() {
    abstract fun altusDao(): AltusDao

    companion object {
        @Volatile
        private var INSTANCE: AltusDatabase? = null

        fun getDatabase(context: Context): AltusDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AltusDatabase::class.java,
                    "altus_diet_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
