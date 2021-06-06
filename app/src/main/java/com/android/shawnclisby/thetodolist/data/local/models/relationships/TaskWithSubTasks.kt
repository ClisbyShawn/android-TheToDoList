package com.android.shawnclisby.thetodolist.data.local.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.android.shawnclisby.thetodolist.data.local.models.SubTask
import com.android.shawnclisby.thetodolist.data.local.models.Task

data class TaskWithSubTasks(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "parentTaskId"
    )
    val subtasks: List<SubTask>
)
