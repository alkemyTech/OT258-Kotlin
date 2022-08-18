package com.melvin.ongandroid.view.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ActivityMainBinding
import com.melvin.ongandroid.model.login.LoginPreferences
import com.melvin.ongandroid.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var loginPreferences: LoginPreferences
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve NavController from the NavHostFragment
        val navHostFragment = binding.navHostFragment.getFragment() as NavHostFragment
        navController = navHostFragment.navController

        //  binding.bnvMainNavigation.setOnNavigationItemSelectedListener(this)
        binding.bnvMainNavigation.setupWithNavController(navController)
    }
    override fun onBackPressed() {
        val currentFragmentId = navController.currentDestination?.id
        if (currentFragmentId == R.id.homeFragment) {
            onConfirmLogout()
        } else {
            super.onBackPressed()
        }
    }
    private fun onConfirmLogout() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Seguro que queres cerrar sesión?")
            .setTitle("Salir")
            .setCancelable(true)
            .setPositiveButton("Si") { _,_ ->
                onLogout()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
    private fun onLogout() {
        //clear session data
        firebaseAuth.signOut()
        googleSignInClient.signOut()
        LoginManager.getInstance().logOut()
        loginPreferences.clear()
        //navigate to login screen
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        finish()
    }
}