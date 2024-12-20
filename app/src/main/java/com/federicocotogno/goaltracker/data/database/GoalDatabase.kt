package com.federicocotogno.goaltracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.federicocotogno.goaltracker.data.models.Goal
import com.federicocotogno.goaltracker.logic.dao.GoalDao

@Database(entities = [Goal::class], version = 1, exportSchema = false)
abstract class GoalDatabase : RoomDatabase() {

    abstract fun habitDao(): GoalDao

    companion object {
        @Volatile
        private var INSTANCE: GoalDatabase? = null

        fun getDatabase(context: Context): GoalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GoalDatabase::class.java,
                    "habit_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}