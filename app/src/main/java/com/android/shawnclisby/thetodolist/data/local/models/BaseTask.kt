package com.android.shawnclisby.thetodolist.data.local.models

import java.text.DateFormat

abstract class BaseTask(
    var createdAt: Long,
    var priority: Priority,
    var title: String,
    var completed: Boolean,
    var description: String?,
    var dueDate: Long?,
) {
    val createdDateFormat: String
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(createdAt)
}