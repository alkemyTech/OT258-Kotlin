package com.melvin.ongandroid.view.fragments.staffFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentStaffBinding
import com.melvin.ongandroid.view.adapters.staff.StaffAdapter

class StaffFragment : Fragment() {
    private lateinit var binding: FragmentStaffBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStaffBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }
    private fun initRecyclerView(){
        binding.rvStaff.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvStaff.adapter = StaffAdapter()
    }
}