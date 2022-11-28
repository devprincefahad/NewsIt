package com.example.newsit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News
    (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)