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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        getStaff()
    }

    //This function evaluate the status value, if fails, it shows a retry button. If is successful, it shows the recyclerview.
    private fun getStaff() {
        viewModel.onCreateStaff()
        viewModel.staffStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status!!) {
                Status.LOADING -> {
                    binding.llErrorStaffCall.visibility = View.GONE
                    binding.staffProgressBarId.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.rvStaff.visibility = View.VISIBLE
                    binding.llErrorStaffCall.visibility = View.GONE
                    binding.staffProgressBarId.visibility = View.GONE
                    viewModel.staff.observe(viewLifecycleOwner, Observer {
                        initRecyclerView(it)
                    })
                }
                Status.ERROR -> {
                    binding.rvStaff.visibility = View.GONE
                    binding.staffProgressBarId.visibility = View.GONE
                    binding.llErrorStaffCall.visibility = View.VISIBLE
                    setUpListeners()
                }
            }
        })
    }

    //This function init the recyclerView
    private fun initRecyclerView(list: List<StaffDataModel>) {
        binding.rvStaff.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvStaff.adapter = StaffAdapter(list){onItemSelected(it)}
    }

    // This function allows us to set up listeners
    private fun setUpListeners() {
        binding.btnRetryStaffCall.setOnClickListener {
            getStaff()
        }
    }

    private fun onItemSelected(staffDataModel: StaffDataModel){

    }
}