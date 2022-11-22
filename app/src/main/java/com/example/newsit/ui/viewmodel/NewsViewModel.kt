package com.example.newsit.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsit.models.Article
import com.example.newsit.models.NewsResponse
import com.example.newsit.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel : ViewModel() {

    private val _articles = MutableLiveData<NewsResponse>()
    val articles: LiveData<NewsResponse> = _articles

    fun fetchArticles(category: String, query: String?) {
        viewModelScope.launch {
            val response =
                withContext(Dispatchers.IO) { NewsRepository.getArticles(category, query!!) }

            if (response.isSuccessful) {
                if (response.body() != null) {
                    _articles.value = response.body()
                }
            }
        }
    }


    /*fun searchArticles(country: String, category: String, query: String, api: String) =
        liveData<List<NewsResponse>?>(Dispatchers.IO) {
            val response = withContext(Dispatchers.IO) {
                NewsRepository.getArticles(
                    country,
                    category,
                    query,
                    api
                )
            }
            if (response.isSuccessful) {
                if (response.body() != null) {
                    articles.postValue(response.body())
                }
            }
        }*/
}