package com.melvin.ongandroid.view.adapters.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemNewsBinding
import com.melvin.ongandroid.model.news.NewsModel

class NewsAdapter(
    private val list: List<NewsModel>
) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = list.size
}