package com.melvin.ongandroid.view.adapters.staff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R

class StaffAdapter() : RecyclerView.Adapter<StaffViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StaffViewHolder(layoutInflater.inflate(R.layout.item_staff, parent, false))
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        holder.render()
    }

    override fun getItemCount(): Int = 8
}