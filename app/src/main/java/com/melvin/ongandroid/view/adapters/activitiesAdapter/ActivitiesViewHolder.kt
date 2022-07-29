package com.melvin.ongandroid.view.adapters.activitiesAdapter

import android.view.View
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemActivitiesBinding
import com.melvin.ongandroid.model.activities.ActivitiesDataModel

class ActivitiesViewHolder(
    private val binding: ItemActivitiesBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ActivitiesDataModel
    ) {
        binding.tvActivitiesTittle.text = item.name.toString()
        binding.tvDescriptionActivities.text =
            HtmlCompat.fromHtml(item.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
        Glide.with(binding.ivActivities.context)
            .load(item.image.toString())
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.progress_animation)
            .into(binding.ivActivities)
    }
}
