package com.example.newsit.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsit.R

class DetailsActivity : AppCompatActivity() {

    lateinit var text_detail_title: TextView
    lateinit var text_detail_time: TextView
    lateinit var text_detail_detail: TextView
    lateinit var text_detail_content: TextView
    lateinit var img_detail_title: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setUI()

    }

    private fun setUI() {

        text_detail_title = findViewById(R.id.text_detail_title)
        text_detail_time = findViewById(R.id.text_detail_time)
        text_detail_detail = findViewById(R.id.text_detail_detail)
        text_detail_content = findViewById(R.id.text_detail_content)
        img_detail_title = findViewById(R.id.img_detail_title)

        val content = intent.getStringExtra("content").toString()
        val description = intent.getStringExtra("description").toString()
        val publishedAt = intent.getStringExtra("publishedAt").toString()
        val title = intent.getStringExtra("title").toString()
        val urlToImage = intent.getStringExtra("urlToImage").toString()

        text_detail_title.text = title
        text_detail_time.text = publishedAt
        text_detail_detail.text = description
        text_detail_content.text = content

        Glide.with(this@DetailsActivity).load(urlToImage).into(img_detail_title)

    }
}