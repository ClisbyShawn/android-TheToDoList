package com.android.shawnclisby.thetodolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.shawnclisby.thetodolist.data.local.models.Category
import com.android.shawnclisby.thetodolist.data.local.models.Member
import com.android.shawnclisby.thetodolist.data.local.models.SubTask
import com.android.shawnclisby.thetodolist.data.local.models.Task
import com.android.shawnclisby.thetodolist.other.Converters

@Database(
    entities = [Task::class, SubTask::class, Category::class, Member::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TheListDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

}