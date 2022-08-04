package com.melvin.ongandroid.view.home.adapters.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.databinding.ItemWelcomeActivitiesBinding
import com.melvin.ongandroid.model.slides.SlidesDataModel
import kotlin.math.min

class WelcomeActivitiesAdapter(
    var list: List<SlidesDataModel>
) : RecyclerView.Adapter<WelcomeActivitiesViewHolder>() {

    companion object DiffCallback : DiffUtil.ItemCallback<SlidesDataModel>() {
        override fun areItemsTheSame(
            oldItem: SlidesDataModel,
            newItem: SlidesDataModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SlidesDataModel,
            newItem: SlidesDataModel
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WelcomeActivitiesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWelcomeActivitiesBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return WelcomeActivitiesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WelcomeActivitiesViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = min(list.size, 4)
}