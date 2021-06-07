package com.android.shawnclisby.thetodolist.data.local

import androidx.room.*
import com.android.shawnclisby.thetodolist.data.local.models.Category
import com.android.shawnclisby.thetodolist.data.local.models.relationships.CategoryWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Transaction
    @Query("SELECT * FROM categories")
    fun getAllCategoriesWithTasks(): Flow<List<CategoryWithTasks>>

    @Transaction
    @Query("SELECT * FROM categories WHERE categoryId =:categoryId")
    fun getCategoryByIdWithTasks(categoryId: String): Flow<CategoryWithTasks>
}