package com.melvin.ongandroid.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.login.LoginPreferences
import com.melvin.ongandroid.view.home.MainActivity
import com.melvin.ongandroid.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var preferences: LoginPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        preferences = LoginPreferences(this)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                // Simulating service request
                delay(1000L) // changed to 1 seg.
            }

            // Check whether user is already logged in o not
            val token = preferences.getToken()

            if (token.isNullOrEmpty()) {
                // User hasn't logged in yet
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            } else {
                // User is already logged in
                //TODO: quitar el delay hardcodeado y checkear en la API si el token es valido
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            finish()
        }
    }
}