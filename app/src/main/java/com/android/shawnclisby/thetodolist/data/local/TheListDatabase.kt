package com.android.shawnclisby.thetodolist.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.shawnclisby.thetodolist.data.models.Task
import com.android.shawnclisby.thetodolist.other.Converters

@Database(entities = [Task::class], version = 1)
@TypeConverters(Converters::class)
abstract class TheListDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TheListDatabase? = null

        fun getDatabase(context: Context): TheListDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TheListDatabase::class.java,
                        "the-list-database"
                    )
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }

}