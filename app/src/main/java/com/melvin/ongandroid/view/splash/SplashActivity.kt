package com.melvin.ongandroid.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.login.Preferences
import com.melvin.ongandroid.view.home.MainActivity
import com.melvin.ongandroid.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        preferences = Preferences(this)

        lifecycleScope.launch {
            var result = withContext(Dispatchers.IO) {
                // Simulating service request
                delay(1000L) // changed to 1 seg.
            }

            // Check whether user is already logged in o not
            val token = preferences.getToken()

            if (token.isNullOrEmpty() || token == " ") {
                // User hasn't logged in yet
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            } else {
                // User is already logged in
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            finish()
        }
    }
}