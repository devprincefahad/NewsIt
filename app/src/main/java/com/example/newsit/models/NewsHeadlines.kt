package com.example.newsit.models

data class NewsHeadlines(
    val source: Source? = null,
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = ""
)