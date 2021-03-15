package com.android.shawnclisby.thetodolist.ui

import androidx.recyclerview.widget.DiffUtil
import com.android.shawnclisby.thetodolist.data.models.Task

class TaskDiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}