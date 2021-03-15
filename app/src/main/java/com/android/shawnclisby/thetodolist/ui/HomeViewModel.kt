package com.android.shawnclisby.thetodolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.shawnclisby.thetodolist.ui.WidgetState.FilterContainer
import com.android.shawnclisby.thetodolist.ui.WidgetState.SearchBar

class HomeViewModel : ViewModel() {

    private var _searchBar = MutableLiveData(SearchBar())
    val searchBar: LiveData<SearchBar> = _searchBar

    private var _filterContainer = MutableLiveData(FilterContainer())
    val filterContainer: LiveData<FilterContainer> = _filterContainer

    private var _emptyList = MutableLiveData(false)
    val emptyList: LiveData<Boolean> = _emptyList

    val onSearchToggled: () -> Unit = {
        if (searchBar.value?.showHide == null || !searchBar.value?.showHide!!)
            _searchBar.postValue(SearchBar(showHide = true))
        else _searchBar.postValue(SearchBar(showHide = false))
    }

    val onFilterToggled: () -> Unit = {
        if (_filterContainer.value?.showHide == null || !_filterContainer.value?.showHide!!)
            _filterContainer.postValue(FilterContainer(showHide = true))
        else _filterContainer.postValue(FilterContainer(showHide = false))
    }

    fun isListEmpty(size: Int) {
        _emptyList.value = size == 0
    }
}

sealed class WidgetState {
    data class SearchBar(val showHide: Boolean? = null) : WidgetState()
    data class FilterContainer(val showHide: Boolean? = null) : WidgetState()
}