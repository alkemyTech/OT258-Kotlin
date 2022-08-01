package com.melvin.ongandroid.view.fragments.loginFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.util.checkMail
import com.melvin.ongandroid.util.checkPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initComponent()
    }

    private fun initComponent(){
        // Checking whether both email input and password input meet the conditions
        binding.etEmailLogin.doOnTextChanged { text, start, before, count ->  manageButtonLogin() } // Email
        binding.etPasswordLogin.doOnTextChanged { text, start, before, count ->  manageButtonLogin() } // Password
    }

    // Validating email and password
    private fun manageButtonLogin(){
        binding.loginBtn.isEnabled =  binding.etEmailLogin.text.toString().checkMail() && binding.etPasswordLogin.text.toString().checkPassword()
    }
}