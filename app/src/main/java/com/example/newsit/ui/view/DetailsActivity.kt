package com.example.newsit.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.newsit.R
import com.example.newsit.databinding.ActivityDetailsBinding
import com.example.newsit.ui.adapter.NewsAdapter
import android.content.Intent
import android.net.ParseException
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.newsit.db.NewsDatabase
import com.example.newsit.models.News
import com.example.newsit.ui.viewmodel.NewsViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    lateinit var content: String
    lateinit var description: String
    lateinit var publishedAt: String
    lateinit var title: String
    lateinit var urlToImage: String
    lateinit var url: String
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NewsViewModel::class.java)

        content = intent.getStringExtra("content").toString()
        description = intent.getStringExtra("description").toString()
        publishedAt = intent.getStringExtra("publishedAt").toString()
        title = intent.getStringExtra("title").toString()
        urlToImage = intent.getStringExtra("urlToImage").toString()
        url = intent.getStringExtra("url").toString()

        checkBookmark()

        setUI()
        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }

        binding.imgShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(intent)
        }

        binding.imgBookmark.setOnClickListener {
            val bookMark =
                News(0, title, description, url, urlToImage, publishedAt, content)

            viewModel.insertNote(bookMark)
            binding.imgBookmark.visibility = View.GONE
            binding.imgBookmarkEd.visibility = View.VISIBLE
        }

        binding.imgBookmarkEd.setOnClickListener {
            viewModel.deleteNote(title)
            binding.imgBookmarkEd.visibility = View.GONE
            binding.imgBookmark.visibility = View.VISIBLE
        }

        binding.openInBrowser.setOnClickListener {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

    }

    /* fun parseDateToddMMyyyy(time: String?): String? {
         var str: String? = null
         var date: Date? = null
         try {
             //2022-11-26T06:45:00Z
             val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
             val outputFormat = SimpleDateFormat("dd-MMM-yyyy h:mm a")
             date = inputFormat.parse(time)
             Log.d("mainactivity", date.toString())
             str = outputFormat.format(date)
         } catch (e: java.text.ParseException) {
             e.printStackTrace()
         }
         Log.d("mainactivity", str.toString())
         return str
     }*/

    fun checkBookmark() {
        viewModel.checkNote(title)
        viewModel.isBookmarked.observe(this, androidx.lifecycle.Observer {

            if (it) {
                binding.imgBookmark.visibility = View.GONE
                binding.imgBookmarkEd.visibility = View.VISIBLE
            } else {
                binding.imgBookmark.visibility = View.VISIBLE
                binding.imgBookmarkEd.visibility = View.GONE
            }

        })
    }

    private fun setUI() {
        /*val date = parseDateToddMMyyyy(publishedAt)
        Log.d("mainactivity", date.toString())*/
        binding.textDetailTitle.text = title
        binding.textDetailTime.text = publishedAt
        binding.textDetailDetail.text = description
        binding.textDetailContent.text = content

        Glide.with(this@DetailsActivity).load(urlToImage).into(binding.imgDetailTitle)

    }
}