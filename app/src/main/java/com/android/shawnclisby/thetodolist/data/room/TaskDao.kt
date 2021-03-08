package com.android.shawnclisby.thetodolist.data.room

import androidx.room.*
import com.android.shawnclisby.thetodolist.data.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getTasksFlow(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id=:id")
    fun getTask(id:Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<Task>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}