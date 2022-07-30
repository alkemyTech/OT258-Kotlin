package com.melvin.ongandroid.view.fragments.contactFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentActivitiesBinding
import com.melvin.ongandroid.databinding.FragmentContactBinding
import com.melvin.ongandroid.util.checkMail
import com.melvin.ongandroid.util.checkMessage
import com.melvin.ongandroid.util.checkName
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            validateData()
            activeButton()
        }
    }

    // function to validate that the fields are correctly completed if it does not show the corresponding error message
    private fun validateData() {
        with(binding) {
            etName.doAfterTextChanged {
                if (!it.toString().checkName()) {
                    tfName.isErrorEnabled = true
                    tfName.error =
                        getString(R.string.error_contact_name_warning)
                } else
                    tfName.isErrorEnabled = false
                viewModel.setContactName(it.toString())

            }

            etMail.doAfterTextChanged {
                if (!it.toString().checkMail()) {
                    tfMail.isErrorEnabled = true
                    tfMail.error =
                        getString(R.string.error_contact_mail_warning)
                } else
                    tfMail.isErrorEnabled = false
                viewModel.setContactMail(it.toString())
            }

            etMessage.doAfterTextChanged {
                if (!it.toString().checkMessage()) {
                    tfMessage.isErrorEnabled = true
                    tfMessage.error =
                        getString(R.string.error_contact_message_warning)
                } else
                    tfMessage.isErrorEnabled = false
                viewModel.setContactMessage(it.toString())
            }
        }
    }

    //fun to Activate and deactivate send button
    private fun activeButton() {
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) {
            viewModel.validateDataContact()
            binding.btnSend.isEnabled = it
        }
    }
}