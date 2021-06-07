package com.android.shawnclisby.thetodolist.data.local.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.android.shawnclisby.thetodolist.data.local.models.Category
import com.android.shawnclisby.thetodolist.data.local.models.Task

data class CategoryWithTasks(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "taskCategoryId"
    )
    val tasks: List<Task>
) {
    val progressCompleted: Float
        get() = (tasks.count { it.completed } / tasks.size).toFloat()
}