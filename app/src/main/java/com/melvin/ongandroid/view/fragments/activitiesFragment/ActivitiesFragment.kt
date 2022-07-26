package com.melvin.ongandroid.view.fragments.activitiesFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentActivitiesBinding
import com.melvin.ongandroid.model.activities.ActivitiesDataModel
import com.melvin.ongandroid.view.adapters.activitiesAdapter.ActivitiesAdapter
import com.melvin.ongandroid.viewmodel.Status
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {
    private var _binding: FragmentActivitiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            initComponent()
        }
    }

    /*
    This function calls loadActivities of the viewModel and is responsible for
    activating or deactivating the components according to the response state.*/
    private fun initComponent() {
        viewModel.onLoadActivities()
        viewModel.activitiesStatus.observe(viewLifecycleOwner) {
            when (it) {
                //change progressBarForTest for spinner ticket OT258-86
                Status.LOADING -> {
                    binding.progressBarForTest.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBarForTest.visibility = View.GONE
                    viewModel.activities.observe(viewLifecycleOwner, Observer { listActivities ->
                        initActivitiesRecyclerView(listActivities)
                    })
                }
                Status.ERROR -> {
                    binding.progressBarForTest.visibility = View.GONE
                    onLoadError(getString(R.string.an_error_has_occurred_obtaining_the_information)) {
                        viewModel.onLoadActivities()
                    }
                }
            }
        }
    }

    //This function init the recyclerview with the query's response
    private fun initActivitiesRecyclerView(list: List<ActivitiesDataModel>) {
        binding.rvActivities.layoutManager = LinearLayoutManager(requireContext())
        binding.rvActivities.adapter = ActivitiesAdapter(list)
    }

    //displays a message and a generic button that will be set when calling the function
    private fun onLoadError(message: String, retryCB: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(resources.getString(R.string.retry)) { retryCB() }
            .show()
    }
}