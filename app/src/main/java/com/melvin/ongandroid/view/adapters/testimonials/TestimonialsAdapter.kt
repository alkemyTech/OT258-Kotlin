package com.melvin.ongandroid.view.adapters.testimonials

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R

class TestimonialsAdapter: RecyclerView.Adapter<TestimonialsViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TestimonialsViewHolder(layoutInflater.inflate(R.layout.item_testimony,parent,false))
    }

    override fun onBindViewHolder(holder: TestimonialsViewHolder, position: Int) {
        holder.render()
    }

    override fun getItemCount(): Int=4
}