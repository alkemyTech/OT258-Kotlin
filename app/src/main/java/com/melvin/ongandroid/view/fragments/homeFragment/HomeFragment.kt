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
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentHomeBinding
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
        initWelcomeRecyclerView()
        if (viewModel.validate()) showSnackBar(resources.getString(R.string.generalError))
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
    private fun initWelcomeRecyclerView() {
        val adapter = WelcomeActivitiesAdapter()
        //helper to snap cards in the center of the screen
        val snapHelper = LinearSnapHelper()
        binding.welcomeActivitiesRecyclerView.adapter = adapter
        snapHelper.attachToRecyclerView(binding.welcomeActivitiesRecyclerView)
    }

//fun to show snackbar and option to refresh view
    fun showSnackBar(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(resources.getString(R.string.retry)) { viewModel.refreshButton()}
            .setBackgroundTint(resources.getColor(R.color.red))
            .setActionTextColor(resources.getColor(R.color.white))
            .show()
}