package com.melvin.ongandroid.view.adapters.testimonials

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemTestimonyBinding
import com.melvin.ongandroid.model.testimonials.DataModel

class TestimonialsAdapter(
    private val list: List<DataModel>
) : RecyclerView.Adapter<TestimonialsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTestimonyBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return TestimonialsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestimonialsViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = list.size
}