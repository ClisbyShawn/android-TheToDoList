package com.android.shawnclisby.thetodolist.data

data class Task(
    val id: Int,
    val title: String,
    val priority: Boolean = false,
    val completed: Boolean = false
) {

    override fun equals(other: Any?): Boolean {
        val otherTask = other as Task
        return (this.id == otherTask.id) && (this.title == otherTask.title)
                && (this.priority == otherTask.priority) && (this.completed == otherTask.completed)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + priority.hashCode()
        result = 31 * result + completed.hashCode()
        return result
    }
}
