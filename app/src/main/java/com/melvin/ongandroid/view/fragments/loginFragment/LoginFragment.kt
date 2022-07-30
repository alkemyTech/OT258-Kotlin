package com.melvin.ongandroid.view.fragments.loginFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.melvin.ongandroid.databinding.FragmentLoginBinding
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
        binding.etEmailLogin.doOnTextChanged { text, start, before, count ->  manageButtonLogin()}
        binding.etPasswordLogin.doOnTextChanged { text, start, before, count ->  manageButtonLogin()}
    }

    private fun manageButtonLogin(){
        /*if (ValidateEmail.isEmail(binding.etEmailLogin.text.toString())){
            binding.loginBtn.isEnabled = true
        } else {
            binding.loginBtn.isEnabled = false
        }
    }*/
}