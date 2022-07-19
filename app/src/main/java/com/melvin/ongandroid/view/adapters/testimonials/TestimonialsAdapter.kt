package com.melvin.ongandroid.view.adapters.testimonials

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.testimonials.DataModel

class TestimonialsAdapter(private val list: List<DataModel>): RecyclerView.Adapter<TestimonialsViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TestimonialsViewHolder(layoutInflater.inflate(R.layout.item_testimony,parent,false))
    }

    override fun onBindViewHolder(holder: TestimonialsViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }

    override fun getItemCount(): Int=4
}