package com.android.shawnclisby.thetodolist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.shawnclisby.thetodolist.data.TaskViewModel

class TaskViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}