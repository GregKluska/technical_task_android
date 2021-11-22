package com.gregkluska.datasource.util

import com.gregkluska.datasource.model.Meta

data class GenericResponse<T>(
    val meta: Meta?,
    val data: T
)