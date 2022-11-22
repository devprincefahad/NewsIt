package com.example.newsit.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsit.R
import com.example.newsit.ui.adapter.CustomAdapter
import com.example.newsit.models.NewsResponse
import com.example.newsit.models.Article
import com.example.newsit.ui.viewmodel.NewsViewModel
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var adapter: CustomAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var news: Call<NewsResponse>
    var totalResults = -1
    private var articles = mutableListOf<Article>()
    lateinit var progressBar: ProgressBar
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var searchView: SearchView
    lateinit var vm: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        vm = ViewModelProvider(this).get(NewsViewModel::class.java)

        findIds()
        setBtnClickListener()

        /* searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(query: String?): Boolean {
                 progressBar.visibility = View.VISIBLE
                 if (query != null) {
                     searchArticles(query)
                 }
                 getNewsHeadlines("general", query!!)
                 return true
             }

             override fun onQueryTextChange(newText: String?): Boolean {
                 return false
             }

         })*/

        progressBar.visibility = View.VISIBLE

        showList()

        vm.fetchArticles("general", null)
        vm.articles.observe(this, Observer {
            //Log.d("mainactivity",it[totalResults].toString())
            if (it != null && !it.articles.isNullOrEmpty()) {
                articles.clear()
                articles.addAll(it.articles)
                adapter.notifyDataSetChanged()
            }
        })
//        getNewsHeadlines("general", null)

    }

    private fun showList() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = CustomAdapter(this@MainActivity, articles)
        recyclerView.adapter = adapter
    }

    /*private suspend fun getNewsHeadlines(category: String, query: String?) {
        news = NewsInstance.newsInstance.callHeadlines("us", category, query, API_KEY)
        getNews()
    }*/

    /*private fun searchArticles(query: String) {
        vm.searchArticles("us", "general", query, API_KEY)
            .observe(this, Observer {
                if (!it.isNullOrEmpty()) {
                    articles.clear()
                    articles.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            })
    }*/

    /* private fun getNews() {
         news.enqueue(object : retrofit2.Callback<NewsResponse> {
             override fun onResponse(
                 call: Call<NewsResponse>,
                 response: Response<NewsResponse>
             ) {
                 progressBar.visibility = View.INVISIBLE
                 val news = response.body()
                 if (news != null) {
                     Toast.makeText(applicationContext, "Fetching", Toast.LENGTH_SHORT).show()
                     Log.d("news", news.toString())
                     totalResults = news.totalResults
                     articles.clear()
                     articles.addAll(news.articles)
                     adapter.notifyDataSetChanged()
                 }
             }

             override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                 Toast.makeText(applicationContext, "Error in fetching news", Toast.LENGTH_LONG)
                     .show()
             }
         })
     }*/

    override fun onClick(v: View?) {
        progressBar.visibility = View.VISIBLE
        val id = v!!.id
        when (id) {
            /* R.id.btn1 -> getNewsHeadlines("business", null)
             R.id.btn2 -> getNewsHeadlines("entertainment", null)
             R.id.btn3 -> getNewsHeadlines("general", null)
             R.id.btn4 -> getNewsHeadlines("health", null)
             R.id.btn5 -> getNewsHeadlines("science", null)
             R.id.btn6 -> getNewsHeadlines("sports", null)
             R.id.btn7 -> getNewsHeadlines("technology", null)*/
        }
    }

    private fun findIds() {
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        recyclerView = findViewById(R.id.recycler_main)
        progressBar = findViewById(R.id.progress_Bar)
        searchView = findViewById(R.id.searchView)
    }

    private fun setBtnClickListener() {
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
    }

}