package com.android.shawnclisby.thetodolist.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var description: String,
    var priority: Boolean = false,
    var completed: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    var dueDate: Long? = null
) {
    val createdDateFormat: String
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(created)

    val dueDateFormat: String?
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(dueDate)
}
