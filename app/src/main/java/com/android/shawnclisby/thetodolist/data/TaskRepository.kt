package com.android.shawnclisby.thetodolist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepository {

    fun getList(): Flow<List<Task>> = flow {
        emit(
            listOf(
                Task(0, "Take out trash", priority = true, completed = true),
                Task(1, "Wash the dishes", priority = true, completed = false),
                Task(
                    2,
                    "Put the Christmas lights up before December 25th, 2020.",
                    priority = false,
                    completed = false
                ),
                Task(3, "Check the mailbox today.", priority = false, completed = true),
                Task(
                    4,
                    "Platinum God of War: Chains of Olympus",
                    priority = false,
                    completed = false
                ),
                Task(
                    5,
                    "Build this app and test/add more features.",
                    priority = true,
                    completed = false
                )

            )
        )

    }
}