package com.example.newsit.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsit.models.Article
import com.example.newsit.models.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)

    @Query("SELECT * from news")
    fun getAllDbNews(): LiveData<List<News>>

    @Query("DELETE FROM news WHERE title=:title")
    suspend fun delete(title: String)

    @Query("SELECT * FROM news WHERE title=:title")
    fun checkBookmark(title: String): List<News>

}