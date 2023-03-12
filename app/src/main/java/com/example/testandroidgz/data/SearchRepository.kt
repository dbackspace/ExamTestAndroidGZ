package com.example.testandroidgz.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testandroidgz.adapter.ImagePagingSource
import com.example.testandroidgz.model.local.LocalImage
import com.example.testandroidgz.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService) {
    fun search(query: String): Flow<PagingData<LocalImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(apiService).apply {
                    searchQuery = query
                }
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 15
    }
}