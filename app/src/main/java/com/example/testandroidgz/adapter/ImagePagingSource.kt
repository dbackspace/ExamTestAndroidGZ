package com.example.testandroidgz.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testandroidgz.model.local.LocalImage
import com.example.testandroidgz.network.ApiService
import com.example.testandroidgz.util.toLocalImage
import javax.inject.Inject

class ImagePagingSource @Inject constructor(private val imageService: ApiService) :
    PagingSource<Int, LocalImage>() {

    var searchQuery: String = ""

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocalImage> {
        return try {
            val nextPage = params.key ?: 1
            val response = imageService.getSearchData(searchQuery, nextPage)

            LoadResult.Page(
                data = response.body()!!.photos.map { img -> img.toLocalImage() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = response.body()!!.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocalImage>): Int? {
        return null // start from beginning
    }
}