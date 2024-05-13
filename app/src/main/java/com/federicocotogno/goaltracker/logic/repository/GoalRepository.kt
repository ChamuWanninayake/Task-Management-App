package com.federicocotogno.goaltracker.logic.repository

import androidx.lifecycle.LiveData
import com.federicocotogno.goaltracker.data.models.Goal
import com.federicocotogno.goaltracker.logic.dao.GoalDao

class GoalRepository (private val goalDao: GoalDao) {
    val getAllHabits: LiveData<List<Goal>> = goalDao.getAllHabits()

    suspend fun addHabit(goal: Goal) {
        goalDao.addHabit(goal)
    }

    suspend fun updateHabit(goal: Goal) {
        goalDao.updateHabit(goal)
    }

    suspend fun deleteHabit(goal: Goal) {
        goalDao.deleteHabit(goal)
    }

    suspend fun deleteAllHabits() {
        goalDao.deleteAll()
    }


}