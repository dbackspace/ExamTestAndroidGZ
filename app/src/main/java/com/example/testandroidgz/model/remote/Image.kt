package com.example.testandroidgz.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("width")
    @Expose
    val width: Int,
    @SerializedName("height")
    @Expose
    val height: Int,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("photographer")
    @Expose
    val photographer: String,
    @SerializedName("photographer_url")
    @Expose
    val photographerUrl: String,
    @SerializedName("photographer_id")
    @Expose
    val photographerId: Int,
    @SerializedName("src")
    @Expose
    val src: Src,
    @SerializedName("avg_color")
    @Expose
    val avgColor: String,
    @SerializedName("alt")
    @Expose
    val alt: String,
)