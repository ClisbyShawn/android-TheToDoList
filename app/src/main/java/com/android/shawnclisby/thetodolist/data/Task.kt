package com.android.shawnclisby.thetodolist.data

data class Task(
    val id: Int,
    val title: String,
    val priority: Boolean = false,
    val completed: Boolean = false
)
