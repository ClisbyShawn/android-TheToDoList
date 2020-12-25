package com.android.shawnclisby.thetodolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.shawnclisby.thetodolist.WidgetState.SearchBar

class HomeViewModel : ViewModel() {

    private var _searchBar = MutableLiveData(SearchBar())
    val searchBar: LiveData<SearchBar> = _searchBar

    val onToggled: () -> Unit = {
            if (searchBar.value?.showHide == null || !searchBar.value?.showHide!!)
                _searchBar.postValue(SearchBar(showHide = true))
            else _searchBar.postValue(SearchBar(showHide = false))
    }
}

sealed class WidgetState {
    data class SearchBar(val showHide: Boolean? = null) : WidgetState()
}