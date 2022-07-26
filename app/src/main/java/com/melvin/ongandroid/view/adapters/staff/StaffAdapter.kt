package com.melvin.ongandroid.view.adapters.staff

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.staff.StaffDataModel

class StaffAdapter(private val list: List<StaffDataModel>,private val onClickListener: (StaffDataModel)-> Unit) : RecyclerView.Adapter<StaffViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StaffViewHolder(layoutInflater.inflate(R.layout.item_staff, parent, false))
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val item = list[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = list.size
}