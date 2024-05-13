package com.federicocotogno.goaltracker.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.federicocotogno.goaltracker.data.models.Goal

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHabit(goal: Goal)

    @Update
    suspend fun updateHabit(goal: Goal)

    @Delete
    suspend fun deleteHabit(goal: Goal)

    @Query("SELECT * FROM habit_table ORDER BY id DESC")
    fun getAllHabits(): LiveData<List<Goal>>

    @Query("DELETE FROM habit_table")
    suspend fun deleteAll()
}