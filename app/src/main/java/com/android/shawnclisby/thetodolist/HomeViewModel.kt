package com.android.shawnclisby.thetodolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.shawnclisby.thetodolist.WidgetState.FilterContainer
import com.android.shawnclisby.thetodolist.WidgetState.SearchBar

class HomeViewModel : ViewModel() {

    private var _searchBar = MutableLiveData(SearchBar())
    val searchBar: LiveData<SearchBar> = _searchBar

    private var _filterContainer = MutableLiveData(FilterContainer())
    val filterContainer: LiveData<FilterContainer> = _filterContainer

    val onSearchToggled: () -> Unit = {
            if (searchBar.value?.showHide == null || !searchBar.value?.showHide!!)
                _searchBar.postValue(SearchBar(showHide = true))
            else _searchBar.postValue(SearchBar(showHide = false))
    }

    val onFilterToggled: () -> Unit = {
        if(_filterContainer.value?.showHide == null || !_filterContainer.value?.showHide!!)
            _filterContainer.postValue(FilterContainer(showHide = true))
        else _filterContainer.postValue(FilterContainer(showHide = false))
    }
}

sealed class WidgetState {
    data class SearchBar(val showHide: Boolean? = null) : WidgetState()
    data class FilterContainer(val showHide: Boolean? = null): WidgetState()
}