package com.juarez.upaxdemo.data.models

data class CustomResponse<T>(
    val isSuccess: Boolean = false,
    val data: T? = null,
    val message: String? = ""
)