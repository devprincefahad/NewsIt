package com.example.newsit.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.newsit.NewsApplication
import com.example.newsit.db.NewsDao
import com.example.newsit.db.NewsDatabase
import com.example.newsit.models.Article
import com.example.newsit.models.News
import com.example.newsit.models.NewsResponse
import com.example.newsit.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

//    fun hasInternetConnection(): Boolean {
//        val connectivityManager = getApplication<NewsApplication>().getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val activeNetwork = connectivityManager.activeNetwork ?: return false
//            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: false
//            return when {
//                cap
//            }
//        }
//    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}