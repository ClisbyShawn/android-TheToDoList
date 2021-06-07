package com.android.shawnclisby.thetodolist.data.local

import androidx.room.*
import com.android.shawnclisby.thetodolist.data.local.models.Task
import com.android.shawnclisby.thetodolist.data.local.models.relationships.TaskWithSubTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM tasks WHERE taskId =:taskId")
    fun getTaskByIdWithSubTasks(taskId: String): Flow<TaskWithSubTasks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)
}