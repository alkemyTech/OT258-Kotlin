package com.melvin.ongandroid.view.home.adapters.testimonials

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemTestimonyBinding
import com.melvin.ongandroid.model.testimonials.DataModel


class TestimonialsViewHolder(
    private val binding: ItemTestimonyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(testimony: DataModel) {
        with(binding) {
            tvName.text = testimony.name.toString()
            tvTestimony.text = testimony.description.toString()
            Glide.with(ivTestimony.context)
                .load(testimony.image.toString())
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.progress_animation)
                .into(ivTestimony)
        }
    }

}