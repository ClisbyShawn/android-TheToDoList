package com.android.shawnclisby.thetodolist.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val priority: Boolean = false,
    val completed: Boolean = false
)
