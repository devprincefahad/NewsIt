package com.example.newsit.api

import com.example.newsit.models.Article
import com.example.newsit.models.NewsResponse
import com.example.newsit.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=${Constants.API_KEY}")
    suspend fun callHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String?,
        @Query("apiKey") api_Key: String
    ): Response<NewsResponse>

}