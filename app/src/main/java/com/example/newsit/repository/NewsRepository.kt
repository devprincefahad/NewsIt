package com.example.newsit.repository

import com.example.newsit.api.NewsInstance
import com.example.newsit.api.NewsInterface
import com.example.newsit.util.Constants.Companion.API_KEY

object NewsRepository {

    suspend fun getArticles(category: String, query: String) =
        NewsInstance.api.callHeadlines("us", category, query, API_KEY)

}