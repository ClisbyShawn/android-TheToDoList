package com.android.shawnclisby.thetodolist.data

import android.app.Application
import androidx.lifecycle.*
import com.android.shawnclisby.thetodolist.data.models.Task
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : ViewModel() {

    private val taskRepository: TaskRepository =
        TaskRepository(application)

    val searchString = MutableLiveData("")
    private val hideCompleted = MutableLiveData(false)

    private val flowList = combine(
        searchString.asFlow(),
        hideCompleted.asFlow()
    ) { query, hide ->
        mapOf(
            "query" to query,
            "hide" to hide
        )
    }.flatMapLatest { map ->
        taskRepository.getTasks(map["query"] as String, map["hide"] as Boolean)
    }

    val taskList = flowList.asLiveData()

    fun insert(task: Task) {
        viewModelScope.launch {
            taskRepository.insert(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            taskRepository.update(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

    fun toggleFilter() {
        hideCompleted.value = !hideCompleted.value!!
    }
}