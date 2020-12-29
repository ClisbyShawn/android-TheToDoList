package com.android.shawnclisby.thetodolist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

class TaskViewModel : ViewModel() {

    private val taskRepository: TaskRepository =
        TaskRepository()

    private val flowList = taskRepository.flowList

    private val _taskList: MutableLiveData<List<Task>> =
        flowList.asLiveData() as MutableLiveData<List<Task>>

    val taskList: LiveData<List<Task>> = _taskList

    private val _searchString = MutableLiveData("")
    private val _hideCompleted = MutableLiveData(false)

    suspend fun searchFilterSortList() {
        flowList.combine(
            flowOf(_searchString.value ?: "")
        ) { tasks, searchQuery ->
            if (_hideCompleted.value!!) {
                searchQueryOperation(filterOperation(tasks), searchQuery)
            } else searchQueryOperation(tasks, searchQuery)

        }.collect { value -> _taskList.postValue(value) }
    }

    /* region Search */

    fun search(searchString: String) {
        _searchString.value = searchString
    }

    private fun searchQueryOperation(tasks: List<Task>, searchQuery: String): ArrayList<Task> {
        val matchingTasks: ArrayList<Task> = ArrayList()
        tasks.forEach { task ->
            if (task.title.toLowerCase().contains(searchQuery.toLowerCase()))
                matchingTasks.add(task)
        }
        return matchingTasks
    }

    /* endregion Search */

    /* region Filter */

    fun filterToggle() {
        _hideCompleted.value = !_hideCompleted.value!!
    }

    private fun filterOperation(tasks: List<Task>): List<Task> {
        val unCompletedTasks: ArrayList<Task> = ArrayList()
        tasks.forEach { task ->
            if (!task.completed) unCompletedTasks.add(task)
        }
        return unCompletedTasks.toList()
    }

    /* endregion Filter */
}