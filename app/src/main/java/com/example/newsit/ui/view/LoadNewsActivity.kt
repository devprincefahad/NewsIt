package com.example.newsit.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsit.R
import com.example.newsit.databinding.ActivityLoadNewsBinding
import com.example.newsit.db.NewsDatabase
import com.example.newsit.models.Article
import com.example.newsit.models.News
import com.example.newsit.ui.adapter.NewsAdapter
import com.example.newsit.ui.adapter.NewsDbAdapter
import com.example.newsit.ui.viewmodel.NewsViewModel
import com.example.newsit.util.Constants

class LoadNewsActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    lateinit var newsAdapter: NewsDbAdapter
    lateinit var binding: ActivityLoadNewsBinding
    lateinit var viewModel: NewsViewModel
    private var articles = ArrayList<Article>()
    private var newsList = ArrayList<News>()
    private lateinit var database: NewsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@LoadNewsActivity, R.layout.activity_load_news)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        val category = intent.getStringExtra("category")

        if (!category.isNullOrEmpty()) {
            binding.tvNewsTitle.text = category
            showList()
            getDataByCategory(category.toString().lowercase())
        } else {
            binding.tvNewsTitle.text = "Saved News"
            loadSavedDb()
        }

        /* binding.loadNewsRecycler.setHasFixedSize(true)
         binding.loadNewsRecycler.layoutManager = LinearLayoutManager(this@LoadNewsActivity)
         newsAdapter = NewsDbAdapter(this@LoadNewsActivity, newsList)
         binding.loadNewsRecycler.adapter = newsAdapter*/

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadSavedDb() {
        viewModel.allNews.observe(this, Observer {
            var nList = mutableListOf<News>()
            for (i in it) {
                nList.add(
                    News(
                        i.id,
                        i.title,
                        i.description,
                        i.url,
                        i.urlToImage,
                        i.publishedAt,
                        i.content
                    )
                )
            }
            Log.d("data", nList.toString())
            newsAdapter = NewsDbAdapter(this@LoadNewsActivity, nList)
            binding.loadNewsRecycler.adapter = newsAdapter
//            newsList.clear()
//            newsList.addAll(it)
//            Log.d("data", newsList.toString())
//            Log.d("data", it.toString())
            newsAdapter.notifyDataSetChanged()
        })

        database = NewsDatabase.getDatabase(this@LoadNewsActivity)
    }

    private fun getDataByCategory(category: String) {
        viewModel.fetchArticles(category, null, Constants.maxPageSize)
        viewModel.articles.observe(this) {
            if (it != null && !it.articles.isNullOrEmpty()) {
                articles.clear()
                articles.addAll(it.articles)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun showList() {
        binding.loadNewsRecycler.setHasFixedSize(true)
        binding.loadNewsRecycler.layoutManager = LinearLayoutManager(this@LoadNewsActivity)
        adapter = NewsAdapter(this@LoadNewsActivity, articles)
        binding.loadNewsRecycler.adapter = adapter
    }
}