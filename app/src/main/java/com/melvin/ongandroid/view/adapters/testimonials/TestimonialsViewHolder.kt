package com.melvin.ongandroid.view.adapters.testimonials

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemTestimonyBinding

class TestimonialsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemTestimonyBinding.bind(view)
    fun render(){
            Glide.with(binding.ivTestimony1.context)
                .load("https://i0.wp.com/psicologoarmandoarafat.com/wp-content/uploads/2019/06/De-hombre-a-hombre-%C2%BFQue%CC%81-te-hubiese-gustado-SABER-a-los-23-an%CC%83os_r.jpg?fit=320%2C480&ssl=1")
                .into(binding.ivTestimony1)
    }
}