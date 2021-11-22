package com.gregkluska.datasource.model

import com.google.gson.annotations.SerializedName

data class Pagination (

    @SerializedName("pages")
    val pages: Int
)