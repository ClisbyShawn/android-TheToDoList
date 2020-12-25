package com.android.shawnclisby.thetodolist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class TaskViewModel : ViewModel() {

    private val taskRepository: TaskRepository =
        TaskRepository()

    val taskList: LiveData<List<Task>> =
        taskRepository.getList().asLiveData()
}