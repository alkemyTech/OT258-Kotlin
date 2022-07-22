package com.melvin.ongandroid.view.adapters.staff

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemStaffBinding


class StaffViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemStaffBinding.bind(view)
    fun render() {
        Glide.with(binding.ivPhoto.context)
            .load("https://cloudfront-us-east-1.images.arcpublishing.com/infobae/BLZJHTB27ZHUPKK3A7GXTMIEQA.jpg")
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.progress_animation)
            .into(binding.ivPhoto)
    }
}