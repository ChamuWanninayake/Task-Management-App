package com.example.goaltrackingapplication.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.goaltrackingapplication.data.database.GoalDatabase
import com.example.goaltrackingapplication.data.models.Goal
import com.example.goaltrackingapplication.logic.dao.GoalDao
import com.example.goaltrackingapplication.logic.repository.GoalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : GoalRepository
    val getAllGoals : LiveData<List<Goal>>

    init {
        val goalDao = GoalDatabase.getDatabase(application).goalDao()
        repository = GoalRepository(goalDao)

        getAllGoals = repository.getAllGoals
    }

    fun addGoal(goal: Goal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGoal(goal)
        }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGoal(goal)
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGoal(goal)
        }
    }

    fun deleteAllGoals(goal: Goal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllGoals()
        }
    }
}