package com.android.shawnclisby.thetodolist.ui

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.shawnclisby.thetodolist.data.models.Task
import com.android.shawnclisby.thetodolist.databinding.ItemTaskListBinding
import com.android.shawnclisby.thetodolist.ui.TaskRecyclerAdapter.TaskViewHolder
import com.android.shawnclisby.thetodolist.util.hide
import com.android.shawnclisby.thetodolist.util.show

class TaskRecyclerAdapter(private val context: Context, private val interaction: TaskInteraction?) :
    ListAdapter<Task, TaskViewHolder>(TaskDiffCallBack()) {

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
            itemBinding.apply {
                tvItemTaskTitle.text = task.title
                tvItemTaskDate.text = task.createdDateFormat

                if (task.completed) {
                    chbxItemTask.isChecked = task.completed
                    tvItemTaskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                if (task.priority) ivItemTaskPriority.show() else ivItemTaskPriority.hide()

                task.dueDate?.let { ivItemTaskDueDate.show() }

                chbxItemTask.setOnCheckedChangeListener { _, isChecked ->
                    task.completed = isChecked
                    interaction?.onTaskCompletionChanged(task)
                    if (isChecked) tvItemTaskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    else tvItemTaskTitle.paintFlags = 0
                }

                root.setOnClickListener { interaction?.onTaskItemClicked(task) }
            }
        }
    }

    interface TaskInteraction {
        fun onTaskItemClicked(task: Task)

        fun onTaskCompletionChanged(task: Task)
    }
}