package com.example.testandroidgz.model.remote

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class Src (
    @SerializedName("original")
    @Expose
    val original: String,

    @SerializedName("large2x")
    @Expose
    val large2x: String,

    @SerializedName("large")
    @Expose
    val large: String,

    @SerializedName("medium")
    @Expose
    val medium: String,

    @SerializedName("small")
    @Expose
    val small: String,

    @SerializedName("portrait")
    @Expose
    private val portrait: String,

    @SerializedName("square")
    @Expose
    private val square: String,

    @SerializedName("landscape")
    @Expose
    private val landscape: String,

    @SerializedName("tiny")
    @Expose
    private val tiny: String,
)