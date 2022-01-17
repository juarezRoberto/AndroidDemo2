package com.juarez.upaxdemo.api

import okhttp3.Interceptor
import okhttp3.Response

class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl
            .newBuilder()
            .addQueryParameter("api_key", "f9478c87639b2154ed68f527b8f615f2")
            .build()
        val requestBuilder = originalRequest
            .newBuilder()
            .header("Authorization", "Token")
            .url(url)
            .build()
        return chain.proceed(requestBuilder)
    }
}