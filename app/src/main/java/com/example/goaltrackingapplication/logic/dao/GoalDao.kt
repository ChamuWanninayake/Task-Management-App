package com.example.goaltrackingapplication.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.goaltrackingapplication.data.models.Goal

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query ("SELECT * FROM goal_table ORDER BY id DESC")
    fun getAllGoals(): LiveData<List<Goal>>

    @Query ("DELETE FROM goal_table")
    suspend fun deleteAll()
}