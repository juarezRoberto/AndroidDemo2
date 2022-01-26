package com.juarez.upaxdemo.data.models

data class Response<out T>(
    val isSuccess: Boolean = false,
    val data: T? = null,
    val message: String = "",
)