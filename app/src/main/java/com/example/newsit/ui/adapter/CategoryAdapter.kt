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
import com.example.newsit.R
import com.example.newsit.models.Category
import com.example.newsit.ui.view.DetailsActivity
import com.example.newsit.ui.view.LoadNewsActivity

class CategoryAdapter(
    val context: Context,
    private val category: ArrayList<Category>

) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.categories_list_item,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.tv_category.text = category[position].category_title
        holder.categoryCardView.setOnClickListener {
            val intent = Intent(context, LoadNewsActivity::class.java)
            intent.putExtra("category", category[position].category_title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return category.size
    }

}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_category: TextView = itemView.findViewById(R.id.tv_category)
    val categoryCardView: CardView = itemView.findViewById(R.id.categoryCardView)

}
