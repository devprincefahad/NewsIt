package com.example.newsit.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsit.R
import com.example.newsit.models.News
import com.example.newsit.ui.view.DetailsActivity

class NewsDbAdapter(
    val context: Context,
    private val dbNews: List<News>
) :
    RecyclerView.Adapter<NewsDbViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsDbViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.headline_list_item,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: NewsDbViewHolder, position: Int) {
        holder.textTitle.text = dbNews[position].title
//        holder.img_headline.setImageResource(dbNews.get(position).urlToImage)
        if (dbNews.get(position).urlToImage != null) {
            Glide.with(context).load(dbNews[position].urlToImage).into(holder.img_headline)
        }
        holder.linearLayout.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("content", dbNews[position].content)
            intent.putExtra("description", dbNews[position].description)
            intent.putExtra("publishedAt", dbNews[position].publishedAt)
            intent.putExtra("title", dbNews[position].title)
            intent.putExtra("urlToImage", dbNews[position].urlToImage)
            intent.putExtra("url", dbNews[position].url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dbNews.size
    }

}

class NewsDbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textTitle: TextView = itemView.findViewById(R.id.text_title)
    val img_headline: ImageView = itemView.findViewById(R.id.img_headline)
    val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)

}
