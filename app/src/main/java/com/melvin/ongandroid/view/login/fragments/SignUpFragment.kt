package com.melvin.ongandroid.view.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentSignUpBinding
import com.melvin.ongandroid.viewmodel.AuthViewModel
import com.melvin.ongandroid.viewmodel.InputTypeSignUp
import com.melvin.ongandroid.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var _binding: FragmentSignUpBinding
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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

    private fun setUpListeners() {
        // Register-signUp button
        binding.signUpBtn.setOnClickListener {
            viewModel.sendNewUser(binding.etNameSignUp.text.toString(),
                binding.etEmailSignUp.text.toString(),
                binding.etPasswordSignUp.text.toString())

            firebaseAnalytics.logEvent("register_pressed") {
                param("message", "register_pressed")
            }
        }
        //Login navigation button
        binding.loginRegisterTxt.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.etNameSignUp.doOnTextChanged { text, _, _, _ -> viewModel.onSignupFieldChange(text.toString(), InputTypeSignUp.NAME)}
        binding.etEmailSignUp.doOnTextChanged { text, _, _, _ -> viewModel.onSignupFieldChange(text.toString(), InputTypeSignUp.EMAIL)}
        binding.etPasswordSignUp.doOnTextChanged { text, _, _, _ -> viewModel.onSignupFieldChange(text.toString(), InputTypeSignUp.PASSWORD)}
        binding.etRepeatPasswordSignUp.doOnTextChanged { text, _, _, _ -> viewModel.onSignupFieldChange(text.toString(), InputTypeSignUp.CONFIRM_PASSWORD)}
    }

    private fun setUpObservers() {
        // observe API request status to add new user
        viewModel.statusSignUpNewUser.observe(viewLifecycleOwner, Observer {
            when (it) {
                Status.LOADING -> binding.signUpBtn.isEnabled = false
                Status.SUCCESS -> {
                    binding.signUpBtn.isEnabled = true // This line will be removed
                    Navigation.findNavController(binding.signUpBtn)
                        .navigate(R.id.action_signUpFragment_to_loginFragment)
                    showSnackBar(getString(R.string.successfully_sign_up_new_user),
                        resources.getColor(R.color.green))
                    firebaseAnalytics.logEvent("sign_up_success") {
                        param("message", "sign_up_success")
                    }
                }
                Status.ERROR -> {
                    showSnackBar("Couldn't register the user", resources.getColor(R.color.red))
                    firebaseAnalytics.logEvent("sign_up_error") {
                        param("message", "sign_up_error")
                    }

                }
                Status.IDLE -> {}//Do nothing
            }
        })

        // SignUp button status
        viewModel.statusButtonSignUp.observe(viewLifecycleOwner) {
            binding.signUpBtn.isEnabled = it
        }

        // SignUp inputs status
        viewModel.signUpFormError.observe(viewLifecycleOwner) {
            when (it.field) {
                InputTypeSignUp.NAME -> {
                    binding.tfNameSignUp.isErrorEnabled = it.isInvalid
                    binding.tfNameSignUp.error = it.message
                }
                InputTypeSignUp.EMAIL -> {
                    binding.tfMailSignUp.isErrorEnabled = it.isInvalid
                    binding.tfMailSignUp.error = it.message
                }
                InputTypeSignUp.PASSWORD -> {
                    binding.tfPasswordSignUp.isErrorEnabled = it.isInvalid
                    binding.tfPasswordSignUp.error = it.message
                }
                InputTypeSignUp.CONFIRM_PASSWORD -> {
                    binding.tfConfirmPasswordSignUp.isErrorEnabled = it.isInvalid
                    binding.tfConfirmPasswordSignUp.error = it.message
                }
            }
        }

        // SignUp ConfirmPassword input status
        viewModel.passwordsMatch.observe(viewLifecycleOwner){
            binding.tfConfirmPasswordSignUp.isErrorEnabled = it.isInvalid
            binding.tfConfirmPasswordSignUp.error = it.message
        }
    }

    // displays a generic message
    private fun showSnackBar(message: String, color: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(color)
            .show()
    }
}