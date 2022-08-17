package com.melvin.ongandroid.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.melvin.ongandroid.databinding.ActivityLoginBinding
import com.melvin.ongandroid.view.home.MainActivity
import com.melvin.ongandroid.viewmodel.AuthViewModel
import com.melvin.ongandroid.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.sessionStatus.value == Status.IDLE
            }
        }
        viewModel.sessionStatus.observe(this) {
            if (it == Status.SUCCESS) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
                startActivity(intent)
                finish()
            } else  if (it == Status.ERROR) {
                setContentView(binding.root)
            }
        }
        viewModel.checkSessionStatus()
        _binding = ActivityLoginBinding.inflate(layoutInflater)
    }
}