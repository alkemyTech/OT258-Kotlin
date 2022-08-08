package com.melvin.ongandroid.view.home.fragments.contactFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentContactBinding
import com.melvin.ongandroid.util.checkMail
import com.melvin.ongandroid.util.checkMessage
import com.melvin.ongandroid.util.checkName
import com.melvin.ongandroid.viewmodel.Status
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

    override fun onResume() {
        setTextInputListeners()
        setSubmitBtnListener()
        setSubmitBtnVisibilityListener()
        super.onResume()
    }
    // function to validate that the fields are correctly completed if it does not show the corresponding error message
    private fun setTextInputListeners() {
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
    private fun setSubmitBtnVisibilityListener() {
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) {
            viewModel.validateDataContact()
            binding.btnSend.isEnabled = it
        }
    }

    /*
       This function calls sendContactDate with send botton is pressed is responsible for
       activating or deactivating the components according to the response state.*/
    private fun setSubmitBtnListener() {
        binding.btnSend.setOnClickListener {
            viewModel.sendContactDate()
        }
        viewModel.sendContactStatus.observe(viewLifecycleOwner) {
            when (it) {
                //change progressBarForTest for spinner ticket OT258-86
                Status.LOADING -> {
                    binding.progressBarForTest.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBarForTest.visibility = View.GONE
                    showSnackBar(getString(R.string.successfully_contact_data_post_message))
                    resetView()
                }
                Status.ERROR -> {
                    onLoadError()
                }
                Status.IDLE -> { }//Do nothing
            }
        }
    }

    //displays a generic message
    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.green))
            .show()
    }

    //fun to reset values
    private fun resetView() {
        with(binding) {
            etName.text?.clear()
            etMail.text?.clear()
            etMessage.text?.clear()
            tfName.isErrorEnabled = false
            tfMail.isErrorEnabled = false
            tfMessage.isErrorEnabled = false
        }
    }

    private fun onLoadError() {
        Snackbar.make(binding.root, resources.getString(R.string.on_contact_sending_error), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.red))
            .show()
        binding.progressBarForTest.visibility = View.GONE
        binding.tfName.isErrorEnabled = true
        binding.tfName.error = getString(R.string.something_went_wrong)
        binding.tfMail.isErrorEnabled = true
        binding.tfMail.error = getString(R.string.something_went_wrong)
        binding.tfMessage.isErrorEnabled = true
        binding.tfMessage.error = getString(R.string.something_went_wrong)
        binding.etName.doAfterTextChanged {
            binding.tfName.isErrorEnabled = false
            binding.tfMail.isErrorEnabled = false
            binding.tfMessage.isErrorEnabled = false
        }
        binding.etMail.doAfterTextChanged {
            binding.tfName.isErrorEnabled = false
            binding.tfMail.isErrorEnabled = false
            binding.tfMessage.isErrorEnabled = false
        }
        binding.etMessage.doAfterTextChanged {
            binding.tfName.isErrorEnabled = false
            binding.tfMail.isErrorEnabled = false
            binding.tfMessage.isErrorEnabled = false
        }
    }
}

