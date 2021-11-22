package com.gregkluska.datasource.model

import com.google.gson.annotations.SerializedName

data class Meta(

    @SerializedName("pagination")
    val pagination: Pagination
)