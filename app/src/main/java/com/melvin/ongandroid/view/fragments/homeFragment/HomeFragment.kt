package com.melvin.ongandroid.view.fragments.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.slides.SlidesDataModel
import com.melvin.ongandroid.model.testimonials.DataModel
import com.melvin.ongandroid.view.adapters.testimonials.TestimonialsAdapter
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.melvin.ongandroid.view.adapters.welcome.WelcomeActivitiesAdapter


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        getTestimonials()
        testimonialsArrowClick()
        getSlides()
    }
    //This function start the testimonials query, an gives the response to the recyclerview
    private fun getTestimonials() {
        viewModel.onCreate()
        viewModel.testimonialModel.observe(viewLifecycleOwner, Observer {
            initTestimonialRecyclerView(it)
        })
    }
    //This function init the recyclerview with the query's response
    private fun initTestimonialRecyclerView(list: List<DataModel>) {
        binding.rvActivityTestimony.layoutManager = LinearLayoutManager(requireContext())
        binding.rvActivityTestimony.adapter = TestimonialsAdapter(list)
    }

    private fun testimonialsArrowClick(){
        binding.btnTestimonials.setOnClickListener{
        }
    }

    // Start and request the slides list to be used with the recycler view
    private fun getSlides() {
        viewModel.onCreateSlides()
        viewModel.slidesModel.observe(viewLifecycleOwner, Observer {
            initWelcomeRecyclerView(it)
        })
    }

    // Init the recyclerview with the query's response
    private fun initWelcomeRecyclerView(list: List<SlidesDataModel>){
        //helper to snap cards in the center of the screen
        val snapHelper = LinearSnapHelper()
        if (list.isNotEmpty()){
            binding.rvWelcomeActivityView.adapter = WelcomeActivitiesAdapter(list)
            snapHelper.attachToRecyclerView(binding.rvWelcomeActivityView)
        } else {
            binding.tlRowBienvenidx.visibility = View.GONE
        }
    }
}