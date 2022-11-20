package com.example.newsit.models

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsHeadlines>
)