package com.fastnews.service.api

import com.fastnews.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RedditAPI {

    var httpClient = OkHttpClient.Builder()

    var builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())

    private var retrofit = builder
        .client(httpClient.build())
        .build()

    var redditService: RedditService = retrofit.create(RedditService::class.java)

}