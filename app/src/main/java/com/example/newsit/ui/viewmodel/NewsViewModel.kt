package com.example.newsit.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.newsit.db.NewsDatabase
import com.example.newsit.models.News
import com.example.newsit.models.NewsResponse
import com.example.newsit.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRepository
    val allNews: LiveData<List<News>>

    init {
        val dao = NewsDatabase.getDatabase(application).newsDao()
        repository = NewsRepository(dao)
        allNews = repository.allNews
    }

    private val _articles = MutableLiveData<NewsResponse>()
    val articles: LiveData<NewsResponse> = _articles

    private val top_articlesMLD = MutableLiveData<NewsResponse>()
    val topArticlesLD: LiveData<NewsResponse> = top_articlesMLD

    private val _isBookmarked = MutableLiveData<Boolean>()
    val isBookmarked: LiveData<Boolean> = _isBookmarked

    fun insertNote(news: News) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(news)
    }

    fun deleteNote(title: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(title)
    }

    fun checkNote(title: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isBookmarked.postValue(repository.checkBookmark(title))
            }
        }
    }

    fun fetchArticles(category: String, query: String? = null, pageSize: Int) {
        viewModelScope.launch {
            val response =
                withContext(Dispatchers.IO) { repository.getArticles(category, query, pageSize) }
            if (response.isSuccessful) {
                if (response.body() != null) {
                    _articles.value = response.body()
                }
            }
        }
    }

    fun fetchTopArticles() {
        viewModelScope.launch {
            val response =
                withContext(Dispatchers.IO) { repository.getTopArticles() }
            if (response.isSuccessful) {
                if (response.body() != null) {
                    top_articlesMLD.value = response.body()
                }
            }
        }
    }

}