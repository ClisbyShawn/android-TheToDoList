package com.android.shawnclisby.thetodolist.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.shawnclisby.thetodolist.data.local.models.Category
import com.android.shawnclisby.thetodolist.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val categories = categoryRepository.getCategories().asLiveData()

    fun insertCategory(category: Category) {
        viewModelScope.launch(IO) {
            categoryRepository.insertCategory(category)
        }
    }
}