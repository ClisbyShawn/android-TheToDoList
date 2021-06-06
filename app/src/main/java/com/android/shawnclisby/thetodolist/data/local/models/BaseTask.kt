package com.android.shawnclisby.thetodolist.data.local.models

import java.text.DateFormat

abstract class BaseTask(
    var createdBy: Member,
    var createdAt: Long,
    var priority: Priority,
    var title: String,
    var completed: Boolean,
    var description: String?,
    var dueDate: Long?,
    var assignedTo: Member?
) {
    val createdDateFormat: String
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(createdAt)
}