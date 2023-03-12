package com.example.testandroidgz.network

import com.example.testandroidgz.model.remote.Page
import com.example.testandroidgz.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: ${Constant.API_KEY}")
    @GET("search")
    suspend fun getSearchData(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<Page>
}