package com.melvin.ongandroid.view.adapters.activitiesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.databinding.ItemActivitiesBinding
import com.melvin.ongandroid.model.activities.ActivitiesDataModel

class ActivitiesAdapter(
    private val activitiesList: List<ActivitiesDataModel>
) :
    RecyclerView.Adapter<ActivitiesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivitiesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemActivitiesBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return ActivitiesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ActivitiesViewHolder,
        position: Int
    ) {
        val item = activitiesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = activitiesList.size
}
