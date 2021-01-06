package com.android.shawnclisby.thetodolist.ui

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.shawnclisby.thetodolist.data.models.Task
import com.android.shawnclisby.thetodolist.databinding.ItemTaskListBinding
import com.android.shawnclisby.thetodolist.ui.TaskRecyclerAdapter.TaskViewHolder
import com.android.shawnclisby.thetodolist.util.hide
import com.android.shawnclisby.thetodolist.util.show

class TaskRecyclerAdapter(private val context: Context, private val interaction: TaskInteraction?) :
    ListAdapter<Task, TaskViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class TaskViewHolder(private val itemBinding: ItemTaskListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(task: Task) {
            itemBinding.tvItemTaskTitle.text = task.title
            if (task.completed) {
                itemBinding.chbxItemTask.isChecked = task.completed
                itemBinding.tvItemTaskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            if (task.priority) itemBinding.ivItemTaskPriority.show() else itemBinding.ivItemTaskPriority.hide()

            itemBinding.chbxItemTask.setOnCheckedChangeListener { _, isChecked ->
                interaction?.onTaskCompletionChanged(task.id, isChecked)
                if (isChecked) itemBinding.tvItemTaskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                else itemBinding.tvItemTaskTitle.paintFlags = 0
            }

            itemBinding.root.setOnClickListener { interaction?.onTaskItemClicked(task) }
        }
    }

    interface TaskInteraction {
        fun onTaskItemClicked(task: Task)

        fun onTaskCompletionChanged(id: Int, checked: Boolean)
    }
}