package com.example.newsit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsit.adapters.CustomAdapter
import com.example.newsit.models.NewsApiResponse
import com.example.newsit.models.NewsHeadlines
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var adapter: CustomAdapter
    lateinit var newsList: RecyclerView
    lateinit var news: Call<NewsApiResponse>
    var totalResults = -1
    private var articles = mutableListOf<NewsHeadlines>()
    lateinit var progressBar: ProgressBar
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        findIds()
        setBtnClickListener()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                progressBar.visibility = View.VISIBLE
                getNewsHeadlines("general", query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        progressBar.visibility = View.VISIBLE

        showList()
        getNewsHeadlines("general", null)
    }

    private fun showList() {
        newsList.setHasFixedSize(true)
        newsList.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = CustomAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
    }

    private fun getNewsHeadlines(category: String, query: String?) {
        news = NewsService.newsInstance.callHeadlines("us", category, query, API_KEY)
        getNews()
    }

    private fun getNews() {
        news.enqueue(object : retrofit2.Callback<NewsApiResponse> {
            override fun onResponse(
                call: Call<NewsApiResponse>,
                response: Response<NewsApiResponse>
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

            override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error in fetching news", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    override fun onClick(v: View?) {
        progressBar.visibility = View.VISIBLE
        val id = v!!.id
        when (id) {
            R.id.btn1 -> getNewsHeadlines("business", null)
            R.id.btn2 -> getNewsHeadlines("entertainment", null)
            R.id.btn3 -> getNewsHeadlines("general", null)
            R.id.btn4 -> getNewsHeadlines("health", null)
            R.id.btn5 -> getNewsHeadlines("science", null)
            R.id.btn6 -> getNewsHeadlines("sports", null)
            R.id.btn7 -> getNewsHeadlines("technology", null)
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
        newsList = findViewById(R.id.recycler_main)
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