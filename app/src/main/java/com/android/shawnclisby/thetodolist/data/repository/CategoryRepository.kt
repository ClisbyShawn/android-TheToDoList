package com.android.shawnclisby.thetodolist.data.repository

import com.android.shawnclisby.thetodolist.data.local.CategoryDao
import com.android.shawnclisby.thetodolist.data.local.models.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {

    fun getCategories() = categoryDao.getAllCategoriesWithTasks()

    fun getCategoryById(categoryId: String) = categoryDao.getCategoryByIdWithTasks(categoryId)

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

}