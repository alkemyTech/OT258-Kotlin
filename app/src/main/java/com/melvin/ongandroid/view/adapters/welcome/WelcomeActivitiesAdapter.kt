package com.melvin.ongandroid.view.adapters.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.data.WelcomeActivity
import com.melvin.ongandroid.model.slides.SlidesDataModel

class WelcomeActivitiesAdapter (var list: List<SlidesDataModel>) : RecyclerView.Adapter<WelcomeActivitiesViewHolder>() {
    companion object DiffCallback : DiffUtil.ItemCallback<WelcomeActivity>() {
        override fun areItemsTheSame(oldItem: WelcomeActivity, newItem: WelcomeActivity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WelcomeActivity,
            newItem: WelcomeActivity
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WelcomeActivitiesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WelcomeActivitiesViewHolder(
            layoutInflater.inflate(R.layout.item_welcome_activities, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WelcomeActivitiesViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = if (list.size<4) list.size-1 else 4
}