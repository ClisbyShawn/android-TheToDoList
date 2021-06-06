package com.android.shawnclisby.thetodolist.data.models

import java.text.DateFormat

abstract class BaseTask(
    var createdById: String,
    var createdAt: Long,
    var priority: Priority,
    var title: String,
    var completed: Boolean,
    var description: String?,
    var dueDate: Long?,
    var subtaskIds: List<String>?,
    var progressPercentage: Float,
    var assignedToId: List<String>?
) {
    val createdDateFormat: String
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(createdAt)
}