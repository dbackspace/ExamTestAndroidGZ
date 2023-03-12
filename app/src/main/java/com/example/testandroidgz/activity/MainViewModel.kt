package com.example.testandroidgz.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.testandroidgz.data.SearchRepository
import com.example.testandroidgz.model.local.LocalImage
import com.example.testandroidgz.util.updateValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {
    private val searchResult = MutableLiveData<PagingData<LocalImage>>()

    fun search(query: String) {
        viewModelScope.launch {
            repository.search(query).collect { item ->
                searchResult.updateValue(item)
            }
        }
    }

    fun getSearchResult(): MutableLiveData<PagingData<LocalImage>> {
        return searchResult
    }
}