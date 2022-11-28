package com.example.newsit.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsit.R
import com.example.newsit.models.Article
import com.example.newsit.ui.view.DetailsActivity

class TopNewsAdapter(
    val context: Context,
    private val topHeadlines: List<Article>
) :
    RecyclerView.Adapter<TopNewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TopNewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.top_headline_list_item,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: TopNewsViewHolder, position: Int) {
        holder.topTextTitle.text = topHeadlines[position].title
        if (topHeadlines.get(position).urlToImage != null) {
            Glide.with(context).load(topHeadlines[position].urlToImage).into(holder.topImg_headline)
        }
        holder.topCardView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("author", topHeadlines[position].author)
            intent.putExtra("content", topHeadlines[position].content)
            intent.putExtra("description", topHeadlines[position].description)
            intent.putExtra("publishedAt", topHeadlines[position].publishedAt)
            intent.putExtra("title", topHeadlines[position].title)
            intent.putExtra("urlToImage", topHeadlines[position].urlToImage)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return topHeadlines.size
    }
}

class TopNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val topTextTitle: TextView = itemView.findViewById(R.id.top_text_title)
    val topImg_headline: ImageView = itemView.findViewById(R.id.top_img_headline)
    val topCardView: CardView = itemView.findViewById(R.id.topCardView)

}
