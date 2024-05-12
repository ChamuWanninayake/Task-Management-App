package com.example.goaltrackingapplication.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "goal_table")
data class Goal(
   @PrimaryKey(autoGenerate = true)
    val Id: Int,
    val goal_title: String,
    val goal_description: String,
    val goal_startTime: String,
    val imageId: Int
): Parcelable