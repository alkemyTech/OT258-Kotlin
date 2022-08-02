package com.melvin.ongandroid.view.home.adapters.welcome

import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemWelcomeActivitiesBinding
import com.melvin.ongandroid.model.slides.SlidesDataModel


class WelcomeActivitiesViewHolder(
    private val binding: ItemWelcomeActivitiesBinding
) : RecyclerView.ViewHolder(binding.root) {

    // Bind the slide data with the slide item view
    fun bind(slide: SlidesDataModel) {
        binding.apply {
            tvTitle.text = slide.title
            tvDescription.text =
                if (slide.description.isNullOrEmpty()) "" else deleteHTML(slide.description!!)
            Glide.with(ivCardPicture.context)
                .load(slide.image)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.progress_animation)
                .into(ivCardPicture)
        }
    }

    // Delete tags HTML from strings
    private fun deleteHTML(html: String): String {
        return HtmlCompat.fromHtml(
            html,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
    }
}