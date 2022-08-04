package com.melvin.ongandroid.view.home.fragments.staffFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.melvin.ongandroid.view.home.fragments.bottomSheetFragment.BottomFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.databinding.FragmentStaffBinding
import com.melvin.ongandroid.model.staff.StaffDataModel
import com.melvin.ongandroid.view.home.adapters.staff.StaffAdapter
import com.melvin.ongandroid.viewmodel.Status
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StaffFragment : Fragment() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

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
        viewModel.staff.observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })
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
                    binding.rvStaff.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.rvStaff.visibility = View.VISIBLE
                    binding.llErrorStaffCall.visibility = View.GONE
                    binding.staffProgressBarId.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.rvStaff.visibility = View.GONE
                    binding.staffProgressBarId.visibility = View.GONE
                    binding.llErrorStaffCall.visibility = View.VISIBLE
                    setUpListeners()
                }
                Status.IDLE -> { }
            }
        })
    }

    //This function init the recyclerView
    private fun initRecyclerView(list: List<StaffDataModel>) {
        binding.rvStaff.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvStaff.adapter = StaffAdapter(list) { onItemSelected(it) }

    }

    // This function allows us to set up listeners
    private fun setUpListeners() {
        binding.btnRetryStaffCall.setOnClickListener {
            getStaff()
        }
    }

    private fun onItemSelected(staffDataModel: StaffDataModel) {
        val bundle = Bundle()
        val bundleLog = Bundle()
        bundle.putString("name", staffDataModel.name)
        bundle.putString("roll", staffDataModel.description)
        bundle.putString("facebookLink", staffDataModel.facebookUrl)
        bundle.putString("linkedinLink", staffDataModel.linkedinUrl)
        bundle.putString("picture", staffDataModel.image)

        // Creating bottomSheetDialog
        val bottomSheetDialog = BottomFragment()
        bottomSheetDialog.arguments = bundle
        bottomSheetDialog.show(parentFragmentManager, bottomSheetDialog.tag)

        // This 2 lines will log member_pressed on firebase when a member was selected to see they details >>>
            bundleLog.putString("Member", "A member has selected")
            firebaseAnalytics.logEvent("member_pressed", bundleLog)
        // <<<
    }
}