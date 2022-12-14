package com.example.newsit.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsit.ui.view.DetailsActivity
import com.example.newsit.R
import com.example.newsit.models.Article
import com.example.newsit.models.News

class NewsAdapter(
    val context: Context,
    private val headlines: List<Article>
) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.headline_list_item,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.textTitle.text = headlines[position].title
        holder.textSource.text = headlines[position].source!!.name
        if (headlines.get(position).urlToImage != null) {
            Glide.with(context).load(headlines[position].urlToImage).into(holder.img_headline)
        }
        holder.linearLayout.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("author", headlines[position].author)
            intent.putExtra("content", headlines[position].content)
            intent.putExtra("description", headlines[position].description)
            intent.putExtra("publishedAt", headlines[position].publishedAt)
            intent.putExtra("title", headlines[position].title)
            intent.putExtra("urlToImage", headlines[position].urlToImage)
            intent.putExtra("url", headlines[position].url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return headlines.size
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textTitle: TextView = itemView.findViewById(R.id.text_title)
    val textSource: TextView = itemView.findViewById(R.id.text_source)
    val img_headline: ImageView = itemView.findViewById(R.id.img_headline)
    val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)

}
