package com.android.shawnclisby.thetodolist.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey val categoryId: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String
)