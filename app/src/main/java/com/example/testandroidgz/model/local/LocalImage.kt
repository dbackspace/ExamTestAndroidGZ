package com.example.testandroidgz.model.local

data class LocalImage(
    var id: Int,
    var width: Int,
    var height: Int,
    var url: String = "",
    var photographerName: String,
    var thumbnail: String = ""
)