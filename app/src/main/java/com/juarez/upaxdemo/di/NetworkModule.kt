package com.juarez.upaxdemo.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.juarez.upaxdemo.api.MovieAPI
import com.juarez.upaxdemo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesHttpInterceptor(): Interceptor {
        return Interceptor {
            val originalRequest = it.request()
            val originalUrl = originalRequest.url
            val url = originalUrl
                .newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .build()

            val requestBuilder = originalRequest
                .newBuilder()
                .url(url)
                .build()
            it.proceed(requestBuilder)
        }
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply { level = Level.BODY }

    @Singleton
    @Provides
    fun provideHttpClient(
        httpInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpInterceptor)
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    @Provides
    fun provideMovieAPI(retrofit: Retrofit): MovieAPI = retrofit.create(MovieAPI::class.java)
}