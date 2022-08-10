package com.melvin.ongandroid.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.melvin.ongandroid.R
import com.melvin.ongandroid.view.home.MainActivity
import com.melvin.ongandroid.view.login.LoginActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private val IODispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            var result = withContext(Dispatchers.IO) {
                // Simulating service request
                delay(5000L) // 5 seg.
            }
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }
}