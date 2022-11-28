package com.example.newsit.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.newsit.api.NewsInstance
import com.example.newsit.api.NewsInterface
import com.example.newsit.db.NewsDao
import com.example.newsit.models.Article
import com.example.newsit.models.News
import com.example.newsit.util.Constants.Companion.API_KEY
import com.example.newsit.util.Constants.Companion.pageSize
import com.example.newsit.util.Constants.Companion.topPageSize
import com.example.newsit.util.NetworkUtils

class NewsRepository(private val newsDao: NewsDao) {

    val allNews: LiveData<List<News>> = newsDao.getAllDbNews()

    suspend fun insert(news: News) {
        newsDao.insert(news)
    }

    suspend fun delete(title: String) {
        newsDao.delete(title)
    }

    fun checkBookmark(title: String): Boolean {
        val list = newsDao.checkBookmark(title)
        if (!list.isNullOrEmpty()){
            return true
        }
        return false
    }

    suspend fun getArticles(category: String, query: String?, pageSize: Int) =
        NewsInstance.api.callHeadlines("us", pageSize, category, query, API_KEY)

    suspend fun getTopArticles() =
        NewsInstance.api.callTopHeadlines(topPageSize, API_KEY)
}
