package com.melvin.ongandroid.view.login.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.melvin.ongandroid.R
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.view.home.MainActivity
import com.melvin.ongandroid.viewmodel.InputTypeLogIn
import com.melvin.ongandroid.viewmodel.AuthViewModel
import com.melvin.ongandroid.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    private val callbackManager = CallbackManager.Factory.create()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics


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
        googleLogin()
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
                    onFacebookLoginError(resources.getString(R.string.on_facebook_login_error)) {
                        facebookLogin()
                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        //if requestCode is 100 continue to login with a google account
        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d(TAG, "signInWithCredential:success")
                                onLoginSuccess()
                                requireActivity().finish()
                            } else
                            // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure")
                            Snackbar.make(binding.root,
                                "Authentication failed.",
                                Snackbar.LENGTH_LONG)
                                .show()
                        }
                }
            } catch (e: ApiException) {
                Log.w(TAG, "signInWithCredential:failure")
                Snackbar.make(binding.root,
                    "Authentication failed.",
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }
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
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }

    private fun onFacebookLoginError(message: String, retryCB: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(resources.getString(R.string.retry)) { retryCB() }
            .show()
    }

    private fun clearLoginFields() {
        binding.etEmailLogin.setText("")
        binding.etPasswordLogin.setText("")
        binding.etEmailLogin.requestFocus()
    }

    private fun onClickLogin() {
        // start home activity on login button click
        binding.loginBtn.setOnClickListener {
                val email = binding.etEmailLogin.text.toString().trim()
                val password = binding.etPasswordLogin.text.toString().trim()
            context?.let {
                viewModel.onUserLogin(email, password, it)
            }
        }
    }

    private fun loginListener() {
        viewModel.loginStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {
                    //TODO:Show spinner
                }
                Status.SUCCESS -> {
                    onLoginSuccess()
                }
                Status.ERROR -> {
                    Snackbar.make(binding.root,
                        "El email/contraseña ingresado es incorrecto",
                        Snackbar.LENGTH_LONG)
                        .setBackgroundTint(ContextCompat.getColor(requireContext(),R.color.red))
                        .show()
                    clearLoginFields()
                }
                Status.IDLE -> {}
                else -> {}
            }
        }
    }

    // This function make the login with a google account
    private fun googleLogin() {
        binding.googleLoginButton.setOnClickListener {
            //configuration
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(requireContext(), googleConf)

            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, 100)
        }
    }
}




