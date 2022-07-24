package com.melvin.ongandroid.view.adapters.news

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemNewsBinding
import com.melvin.ongandroid.model.news.NewsModel


class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemNewsBinding.bind(view)
    fun render(news: NewsModel) {
        binding.newsTitle.text = news.name
        binding.newsDescription.text = news.content
        Glide.with(binding.newsImage.context)
            .load(news.imageUrl)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.progress_animation)
            .into(binding.newsImage)
    }
}