package com.melvin.ongandroid.view.fragments.bottomSheetFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.LayoutDialogStaffBinding
import dagger.hilt.android.AndroidEntryPoint

 // This class shows the details staff once you have clicked on a staff member. This inherits from BottomSheetDialogFragment
class BottomFragment:BottomSheetDialogFragment() {

     // Attributes
    private var _binding: LayoutDialogStaffBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Load values in the bottom sheet dialog
        _binding = LayoutDialogStaffBinding.inflate(inflater, container, false)
        binding.apply {
            Glide.with(requireContext())
                .load(requireArguments().getString("picture"))
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.progress_animation)
                .into(ivPictureStaff)
            tvNameStaff.text = requireArguments().getString("name", "*Nombre*") ?: "*Nombre*"
            tvRollStaff.text = requireArguments().getString("roll", "*Nombre*") ?: "*Roll*"
            tvFacebook.text = requireArguments().getString("facebookLink", "*Nombre*") ?: "*Facebook*"
            tvLinkedin.text = requireArguments().getString("linkedinLink", "*Nombre*") ?: "*Linkedin*"
        }
        val facebookUrl = requireArguments().getString("facebookLink")
        val linkedinUrl = requireArguments().getString("linkedinLink")
        facebookUrl?.let { url ->
            binding.tvFacebook.setOnClickListener(View.OnClickListener {
                launchBrowser(url)
            })
        }
        linkedinUrl?.let { url ->
            binding.tvLinkedin.setOnClickListener(View.OnClickListener {
                launchBrowser(url)
            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

     private fun launchBrowser(url: String) {
         var finalURL = url
         val browserIntent = Intent(Intent.ACTION_VIEW)
         if (!url.startsWith("http://") && !url.startsWith("https://"))
             finalURL = "http://$url";
         browserIntent.data = Uri.parse(finalURL)
         startActivity(browserIntent)
     }
}