package com.android.shawnclisby.thetodolist.data

import android.app.Application
import androidx.lifecycle.*
import com.android.shawnclisby.thetodolist.data.models.OrderBy
import com.android.shawnclisby.thetodolist.data.models.SortOrder
import com.android.shawnclisby.thetodolist.data.models.SortOrder.DateOrder
import com.android.shawnclisby.thetodolist.data.models.SortOrder.TitleOrder
import com.android.shawnclisby.thetodolist.data.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : ViewModel() {

    private val taskRepository: TaskRepository =
        TaskRepository(application)

    /* region Home Fragment Operations */

    val searchString = MutableLiveData("")

    private val _hideCompleted = MutableLiveData(false)
    val hideCompleted: LiveData<Boolean> = _hideCompleted

    private val _sortOrder = MutableLiveData<SortOrder>(TitleOrder())
    val sortOrder: LiveData<SortOrder> = _sortOrder

    private val flowList = combine(
        searchString.asFlow(),
        _hideCompleted.asFlow(),
        _sortOrder.asFlow()
    ) { query, hide, order ->
        mapOf(
            "query" to query,
            "hide" to hide,
            "order" to order
        )
    }.flatMapLatest { map ->
        taskRepository.getTasks(
            map["query"] as String,
            map["hide"] as Boolean,
            map["order"] as SortOrder
        )
    }

    val taskList = flowList.asLiveData()

    fun toggleFilter() {
        _hideCompleted.value = !_hideCompleted.value!!
    }

    fun toggleTitleOrder() {
        when (_sortOrder.value) {
            is DateOrder -> _sortOrder.value = TitleOrder()
            is TitleOrder -> {
                if ((_sortOrder.value as TitleOrder).orderBy == OrderBy.ASC)
                    _sortOrder.value = TitleOrder(OrderBy.DESC)
                else _sortOrder.value = TitleOrder()
            }
            null -> _sortOrder.value = TitleOrder()
        }
    }

    fun toggleDateOrder() {
        when (_sortOrder.value) {
            is DateOrder -> {
                if ((_sortOrder.value as DateOrder).orderBy == OrderBy.ASC)
                    _sortOrder.value = DateOrder(OrderBy.DESC)
                else _sortOrder.value = DateOrder()
            }
            is TitleOrder -> _sortOrder.value = DateOrder()
            null -> _sortOrder.value = DateOrder()
        }
    }

    /* endregion Home Fragment Operations */

    /* region Add/Edit Fragment Operations */

    private var _taskData: MutableLiveData<Task?> = MutableLiveData(null)
    var taskData: LiveData<Task?> = _taskData

    private var dateInMillis: Long? = null

    fun taskDetail(task: Task?) {
        _taskData.value = task
    }

    fun saveEditTask(title: String, description: String, priority: Boolean) {
        if (taskData.value == null) insert(
            Task(
                title = title,
                description = description,
                priority = priority,
                dueDate = dateInMillis
            )
        ) else {
            taskData.value?.apply {
                this.title = title
                this.description = description
                this.priority = priority
                this.dueDate = dateInMillis
                update(this)
            }
        }
        dateInMillis = null
    }

    fun updateDate(timeInMillis: Long) {
        dateInMillis = timeInMillis
    }

    /* endregion Add/Edit Fragment Operations */

    /* region Basic Database Operations */

    private fun insert(task: Task) {
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

    private fun getTaskBy(id: Int): Flow<Task> {
        return taskRepository.getTask(id)
    }

    /* endregion Basic Database Operations */
}