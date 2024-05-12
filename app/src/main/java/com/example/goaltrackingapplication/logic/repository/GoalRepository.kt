package com.example.goaltrackingapplication.logic.repository

import androidx.lifecycle.LiveData
import com.example.goaltrackingapplication.data.models.Goal
import com.example.goaltrackingapplication.logic.dao.GoalDao

class GoalRepository (private val goalDao: GoalDao) {

    val getAllGoals: LiveData<List<Goal>> = goalDao.getAllGoals()


    suspend fun addGoal(goal: Goal) {
        goalDao.addGoal(goal)
    }
    suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal)
    }
    suspend fun deleteGoal(goal: Goal) {
        goalDao.addGoal(goal)
    }
    suspend fun deleteAllGoals() {
        goalDao.deleteAll()
    }
}