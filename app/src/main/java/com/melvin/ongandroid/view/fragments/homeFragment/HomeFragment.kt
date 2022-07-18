package com.melvin.ongandroid.view.fragments.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.view.adapters.testimonials.TestimonialsAdapter
import com.melvin.ongandroid.view.adapters.welcome.WelcomeActivitiesAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initTestimonialRecyclerView()
        initWelcomeRecyclerView()
    }

    private fun initTestimonialRecyclerView() {
        binding.rvActivityTestimony.layoutManager = LinearLayoutManager(requireContext())
        binding.rvActivityTestimony.adapter = TestimonialsAdapter()
    }
    private fun initWelcomeRecyclerView() {
        val adapter = WelcomeActivitiesAdapter()
        //helper to snap cards in the center of the screen
        val snapHelper = LinearSnapHelper()
        binding.welcomeActivitiesRecyclerView.adapter = adapter
        snapHelper.attachToRecyclerView(binding.welcomeActivitiesRecyclerView)
    }
}