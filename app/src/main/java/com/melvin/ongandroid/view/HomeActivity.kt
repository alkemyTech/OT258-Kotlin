package com.melvin.ongandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.databinding.ActivityHomeBinding
import com.melvin.ongandroid.view.adapters.testimonials.TestimonialsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }
    private fun initRecyclerView() {
        binding.rvActivityTestimony.layoutManager = LinearLayoutManager(this)
        binding.rvActivityTestimony.adapter = TestimonialsAdapter()
    }
}