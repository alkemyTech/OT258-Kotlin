package com.melvin.ongandroid.view.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.encoders.ObjectEncoder
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentSignUpBinding
import com.melvin.ongandroid.viewmodel.InputTypeSignUp
import com.melvin.ongandroid.viewmodel.Status
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var _binding: FragmentSignUpBinding
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners(){
        // Register-signUp button
        binding.signUpBtn.setOnClickListener {
            viewModel.sendNewUser(binding.etNameSignUp.text.toString(),
                binding.etEmailSignUp.text.toString(),
                binding.etPasswordSignUp.text.toString())
        }

        binding.etNameSignUp.doOnTextChanged { text, start, before, count -> viewModel.manageButtonSignUp(binding.etNameSignUp.text.toString(), InputTypeSignUp.NAME) }
        binding.etEmailSignUp.doOnTextChanged { text, start, before, count -> viewModel.manageButtonSignUp(binding.etEmailSignUp.text.toString(), InputTypeSignUp.EMAIL)}
        binding.etPasswordSignUp.doOnTextChanged { text, start, before, count -> viewModel.manageButtonSignUp(binding.etPasswordSignUp.text.toString(), InputTypeSignUp.PASSWORD)}
        binding.etRepeatPasswordSignUp.doOnTextChanged { text, start, before, count -> viewModel.manageButtonSignUp(binding.etRepeatPasswordSignUp.text.toString(), InputTypeSignUp.CONFIRMPASSWORD)}
    }

    private fun setUpObservers(){
        // observe API request status to add new user
        viewModel.statusSignUpNewUser.observe(viewLifecycleOwner, Observer {
            when (it){
                Status.LOADING -> binding.signUpBtn.isEnabled = false
                Status.SUCCESS -> {
                    Navigation.findNavController(binding.signUpBtn).navigate(R.id.action_signUpFragment_to_loginFragment)
                    showSnackBar(getString(R.string.successfully_sign_up_new_user), resources.getColor(R.color.green))
                }
                Status.ERROR -> {
                    //TODO
                    showSnackBar("MAL", resources.getColor(R.color.red))
                }
                Status.IDLE -> { }//Do nothing
            }
        })

        viewModel.statusButtonSignUp.observe(viewLifecycleOwner, Observer {
            binding.signUpBtn.isEnabled = it
        })

        // Show error on name Input from sighUp fragment
        viewModel.nameSignUpApplied.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.tfNameSignUp.isErrorEnabled = false
            } else {
                binding.tfNameSignUp.isErrorEnabled = true
                binding.tfNameSignUp.error = "Complete this field"
            }
        })

        // Show error on email Input from sighUp fragment
        viewModel.emailSignUpApplied.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.tfMailSignUp.isErrorEnabled = false
            } else {
                binding.tfMailSignUp.isErrorEnabled = true
                binding.tfMailSignUp.error = "Email doesn't meet the condition"
            }
        })

        // Show error on password Input from sighUp fragment
        viewModel.passwordSignUpApplied.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.tfPasswordSignUp.isErrorEnabled = false
            } else {
                binding.tfPasswordSignUp.isErrorEnabled = true
                binding.tfPasswordSignUp.error = "Password must has at least a number, a lower case letter, an upper case letter, a special character and without whitespace"
            }
        })

        // Show error on email Input from sighUp fragment
        viewModel.confirmPasswordSignUpApplied.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.tfConfirmPasswordSignUp.isErrorEnabled = false
            } else {
                binding.tfConfirmPasswordSignUp.isErrorEnabled = true
                binding.tfConfirmPasswordSignUp.error = "Passwords are not the same"
            }
        })
    }

    //displays a generic message
    private fun showSnackBar(message: String, color: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(color)
            .show()
    }
}