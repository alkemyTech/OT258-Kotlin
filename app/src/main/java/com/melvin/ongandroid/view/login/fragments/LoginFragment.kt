package com.melvin.ongandroid.view.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.melvin.ongandroid.R
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.viewmodel.InputTypeLogIn
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

enum class Status { LOADING, SUCCESS, ERROR }
@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
//    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        onClickSignUp()
//        onClickLogin()
        return binding.root
    }

    private fun onClickSignUp() {
        binding.loginRegisterTxt.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initComponent()

        // status button login changes depending on whether meets the conditions or not
        viewModel.statusButtonLogin.observe(viewLifecycleOwner, Observer {
            binding.loginBtn.isEnabled = it
        })
    }

    private fun initComponent(){
        // Checking whether both email input and password input meet the conditions
        binding.etEmailLogin.doOnTextChanged { text, start, before, count ->  viewModel.manageButtonLogin(binding.etEmailLogin.text.toString(), InputTypeLogIn.EMAIL) }
        binding.etPasswordLogin.doOnTextChanged { text, start, before, count ->  viewModel.manageButtonLogin(binding.etPasswordLogin.text.toString(), InputTypeLogIn.PASSWORD) }
    }




 }