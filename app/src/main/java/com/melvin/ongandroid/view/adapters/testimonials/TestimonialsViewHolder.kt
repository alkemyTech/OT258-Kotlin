package com.melvin.ongandroid.view.adapters.testimonials

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.ItemTestimonyBinding
import com.melvin.ongandroid.model.testimonials.DataModel

class TestimonialsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemTestimonyBinding.bind(view)
    fun render(testimony: DataModel) {
        binding.tvName.text = testimony.name.toString()
        binding.tvTestimony.text = testimony.description.toString()
        Glide.with(binding.ivTestimony.context)
            .load(testimony.image.toString())
            .into(binding.ivTestimony)
    }
}