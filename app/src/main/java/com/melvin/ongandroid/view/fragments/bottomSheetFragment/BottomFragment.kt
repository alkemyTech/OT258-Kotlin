package com.melvin.ongandroid.view.fragments.bottomSheetFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.melvin.ongandroid.databinding.LayoutDialogStaffBinding
import dagger.hilt.android.AndroidEntryPoint

class BottomFragment:BottomSheetDialogFragment() {

    private var _binding: LayoutDialogStaffBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutDialogStaffBinding.inflate(inflater, container, false)
        binding.apply {
            //ivPictureStaff
            tvNameStaff.text = requireArguments().getString("name", "*Nombre*") ?: "*Nombre*"
            tvRollStaff.text = requireArguments().getString("name", "*Nombre*") ?: "*Roll*"
            tvFacebook.text = requireArguments().getString("name", "*Nombre*") ?: "*Facebook*"
            tvLinkedin.text = requireArguments().getString("name", "*Nombre*") ?: "*Linkedin*"
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }
}