package com.melvin.ongandroid.view.fragments.staffFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.melvin.ongandroid.databinding.FragmentStaffBinding
import com.melvin.ongandroid.model.staff.StaffDataModel
import com.melvin.ongandroid.view.adapters.staff.StaffAdapter
import com.melvin.ongandroid.viewmodel.Status
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

@AndroidEntryPoint
class StaffFragment : Fragment() {

    private lateinit var binding: FragmentStaffBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStaffBinding.inflate(inflater, container, false)
        getStaff()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    //This function gets the Staff Data Model List
    private fun getStaff() {
        viewModel.onCreateStaff()
        viewModel.staff.observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })

        //Here the progress bar is observing the status value.
        viewModel.staffStatus.observe(viewLifecycleOwner, Observer {
            if (it == Status.SUCCESS) {
                binding.staffProgressBarId.isVisible = FALSE
            } else {
                binding.staffProgressBarId.isVisible = TRUE
            }
        })
    }

    //This function init the recyclerView
    private fun initRecyclerView(list: List<StaffDataModel>) {
        binding.rvStaff.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvStaff.adapter = StaffAdapter(list)
    }
}