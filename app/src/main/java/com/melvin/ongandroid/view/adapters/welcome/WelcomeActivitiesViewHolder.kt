package com.melvin.ongandroid.view.adapters.welcome

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemWelcomeActivitiesBinding
import com.melvin.ongandroid.model.slides.SlidesDataModel


class WelcomeActivitiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemWelcomeActivitiesBinding.bind(view)

    // Bind the slide data with the slide item view
    fun bind(slide: SlidesDataModel) {
        var description = slide.description!!.length-4
        binding.apply {
            tvTitle.text = slide.title
            tvDescription.text = slide.description!!.substring(3,description)
            Glide.with(ivCardPicture.context)
                .load(slide.image)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.progress_animation)
                .into(ivCardPicture)
        }
    }
}