package com.android.shawnclisby.thetodolist.data

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val taskRepository: TaskRepository =
        TaskRepository()

    private var _taskList: MutableLiveData<List<Task>> = MutableLiveData()
    val taskList: LiveData<List<Task>> = _taskList

    private val flowList: Flow<List<Task>> =
        taskRepository.getList()

    init {
        viewModelScope.launch {
            initialize()
        }
    }

    private suspend fun initialize() {
        flowList.collect { list-> _taskList.postValue(list) }
    }

    suspend fun search(query: String) {
        flowList.combine(
            flowOf(query)
        ) { tasks, searchQuery ->
            searchQueryOperation(tasks, searchQuery)
        }.collect { value ->
            _taskList.postValue(value)
        }
    }

    private fun searchQueryOperation(tasks: List<Task>, searchQuery: String): ArrayList<Task> {
        val matchingTasks: ArrayList<Task> = ArrayList()
        tasks.forEach { task ->
            if (task.title.toLowerCase().contains(searchQuery.toLowerCase()))
                matchingTasks.add(task)
        }
        return matchingTasks
    }
}