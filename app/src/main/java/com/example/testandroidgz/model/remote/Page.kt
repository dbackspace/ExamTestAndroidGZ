package com.example.testandroidgz.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("total_results")
    @Expose
    val totalResults: Int,
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("per_page")
    @Expose
    val perPage: Int,
    @SerializedName("photos")
    @Expose
    val photos: List<Image>,
    @SerializedName("next_page")
    @Expose
    val nextPage: String,
    @SerializedName("prev_page")
    @Expose
    val prevPage: String,
)