package com.example.newsit.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsit.R
import com.example.newsit.databinding.ActivityLoadNewsBinding
import com.example.newsit.db.NewsDatabase
import com.example.newsit.models.Article
import com.example.newsit.ui.adapter.NewsAdapter
import com.example.newsit.ui.viewmodel.NewsViewModel
import com.example.newsit.util.Constants

class LoadNewsActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    lateinit var binding: ActivityLoadNewsBinding
    lateinit var viewModel: NewsViewModel
    private var articles = ArrayList<Article>()
    private lateinit var database: NewsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@LoadNewsActivity, R.layout.activity_load_news)

        database = NewsDatabase.getDatabase(this@LoadNewsActivity)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        val category = intent.getStringExtra("category")
        binding.tvNewsTitle.text = category

        showList()
        getDataByCategory(category.toString().lowercase())

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
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