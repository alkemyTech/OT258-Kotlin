package com.melvin.ongandroid.view.login.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.melvin.ongandroid.R
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.model.login.GenericLogin
import com.melvin.ongandroid.model.login.Login
import com.melvin.ongandroid.util.checkMail
import com.melvin.ongandroid.util.checkPassword
import com.melvin.ongandroid.view.home.MainActivity
import com.melvin.ongandroid.viewmodel.InputTypeLogIn
import com.melvin.ongandroid.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

enum class Status { LOADING, SUCCESS, ERROR }

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    private val callbackManager = CallbackManager.Factory.create()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        onClickSignUp()
        facebookLogin()
        onClickLogin()
        loginListener()
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

    private fun initComponent() {
        // Checking whether both email input and password input meet the conditions
        binding.etEmailLogin.doOnTextChanged { text, start, before, count ->
            viewModel.manageButtonLogin(
                binding.etEmailLogin.text.toString(),
                InputTypeLogIn.EMAIL
            )
        }
        binding.etPasswordLogin.doOnTextChanged { text, start, before, count ->
            viewModel.manageButtonLogin(
                binding.etPasswordLogin.text.toString(),
                InputTypeLogIn.PASSWORD
            )
        }
    }
    // This function make the login with a facebook account
    private fun facebookLogin() {
        val email = "email"
        binding.facebookLoginButton.setPermissions(email)
        binding.facebookLoginButton.fragment = this
        binding.facebookLoginButton.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$result")
                    handleFacebookAccessToken(result.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook: onCancel: Cancel")
                }

                override fun onError(error: FacebookException?) {
                    Log.d(TAG, "facebook: onError: error")
                    onLoadError(resources.getString(R.string.on_facebook_login_error)) {
                        facebookLogin()
                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                onLoginSuccess()
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onLoginSuccess() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    private fun onLoadError(message: String, retryCB: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(resources.getString(R.string.retry)) { retryCB() }
            .show()
    }

    // Login

    private fun onClickLogin() {
        // start home activity on login button click
        binding.loginBtn.setOnClickListener {
            var logD = Login (
                binding.etEmailLogin.text.toString().trim(),
                binding.etPasswordLogin.text.toString().trim()
            )
            context?.let {
                viewModel.onLoadLogin(logD, it)
            }

        }

    }

    private fun loginListener() {
        onClickLogin()
        viewModel.login.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                GenericLogin.Status.LOADING -> {

                }
                GenericLogin.Status.SUCCESS-> {

                    startActivity(Intent(requireContext(), MainActivity::class.java))

                }
                GenericLogin.Status.ERROR -> {
                    binding.etEmailLogin.error = "Error"
                    binding.etPasswordLogin.error = "Error"
                }

                else -> {}
            }
        })
    }
}







