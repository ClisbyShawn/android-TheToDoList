package com.android.shawnclisby.thetodolist.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val priority: Boolean = false,
    val completed: Boolean = false,
    val created: Long = System.currentTimeMillis()
) {
    val createdDateFormat: String
    get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(created)
}
