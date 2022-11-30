package com.example.newsit.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsit.R
import com.example.newsit.databinding.ActivityMainBinding
import com.example.newsit.models.Article
import com.example.newsit.models.Category
import com.example.newsit.ui.adapter.CategoryAdapter
import com.example.newsit.ui.adapter.NewsAdapter
import com.example.newsit.ui.adapter.TopNewsAdapter
import com.example.newsit.ui.viewmodel.NewsViewModel
import com.example.newsit.util.Constants

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    lateinit var topAdapter: TopNewsAdapter
    lateinit var categoryAdapter: CategoryAdapter
    private var articles = mutableListOf<Article>()
    private var topArticles = mutableListOf<Article>()
    private var categories = ArrayList<Category>()
    lateinit var binding: ActivityMainBinding

    lateinit var vm: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        vm = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NewsViewModel::class.java)

        binding.tvViewAll.setOnClickListener {
            val intent = Intent(this, LoadNewsActivity::class.java)
            intent.putExtra("category", "General")
            startActivity(intent)
        }

        binding.imgAllBookmarks.setOnClickListener {
            val intent = Intent(this,LoadNewsActivity::class.java)
            startActivity(intent)
        }

        showList()
        callApi()
    }

    private fun callApi() {
        vm.fetchArticles("general", null, Constants.topPageSize)
        vm.articles.observe(this) {
            Toast.makeText(this@MainActivity, "inside method", Toast.LENGTH_SHORT).show()
            if (it != null && !it.articles.isNullOrEmpty()) {
                Log.d("mainactivity", it.totalResults.toString())
                articles.clear()
                articles.addAll(it.articles)
                adapter.notifyDataSetChanged()
            }
        }
        vm.fetchTopArticles()
        vm.topArticlesLD.observe(this) {
            if (it != null && !it.articles.isNullOrEmpty()) {
                Log.d("mainactivity", it.totalResults.toString())
                Log.d("mainactivity", "inside the method")
                topArticles.clear()
                topArticles.addAll(it.articles)
                topAdapter.notifyDataSetChanged()

            }
        }
    }

    private fun showList() {

        binding.recyclerTop.setHasFixedSize(true)
        binding.recyclerTop.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        topAdapter = TopNewsAdapter(this@MainActivity, topArticles)
        binding.recyclerTop.adapter = topAdapter

        categories.add(Category("Business"))
        categories.add(Category("Entertainment"))
        categories.add(Category("Health"))
        categories.add(Category("Science"))
        categories.add(Category("Sports"))
        categories.add(Category("Technology"))

        binding.recyclerCategories.setHasFixedSize(true)
        binding.recyclerCategories.layoutManager =
            GridLayoutManager(
                this@MainActivity, 3
            )
        categoryAdapter = CategoryAdapter(this@MainActivity, categories)
        binding.recyclerCategories.adapter = categoryAdapter

        binding.recyclerMain.setHasFixedSize(true)
        binding.recyclerMain.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = NewsAdapter(this@MainActivity, articles)
        binding.recyclerMain.adapter = adapter
    }

}