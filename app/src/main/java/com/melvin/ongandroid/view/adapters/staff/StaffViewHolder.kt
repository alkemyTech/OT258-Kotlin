package com.melvin.ongandroid.view.adapters.staff

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemStaffBinding
import com.melvin.ongandroid.model.staff.StaffDataModel


class StaffViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemStaffBinding.bind(view)
    fun render(member: StaffDataModel,onClickListener: (StaffDataModel)-> Unit) {
        binding.tvName.text = member.name.toString()
        Glide.with(binding.ivPhoto.context)
            .load(member.image.toString())
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.progress_animation)
            .into(binding.ivPhoto)
        itemView.setOnClickListener{ onClickListener(member)}
    }
}