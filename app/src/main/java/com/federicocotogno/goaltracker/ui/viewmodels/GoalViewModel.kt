package com.federicocotogno.goaltracker.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.federicocotogno.goaltracker.data.models.Goal
import com.federicocotogno.goaltracker.data.database.GoalDatabase
import com.federicocotogno.goaltracker.logic.repository.GoalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GoalRepository
    val getAllHabits: LiveData<List<Goal>>


    init {
        val habitDao= GoalDatabase.getDatabase(application).habitDao()
        repository = GoalRepository(habitDao)

        getAllHabits = repository.getAllHabits
    }

    fun addGoal(goal: Goal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(goal)
        }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHabit(goal)
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHabit(goal)
        }
    }

    fun deleteAllGoals() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHabits()
        }
    }


}