package com.melvin.ongandroid.view

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ActivityMainBinding
import com.melvin.ongandroid.uitel.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint


//@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //   ProgressBar Spinner Loading
        val loading = LoadingDialog(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object :Runnable {
            override fun run() {
                loading.isDismiss()
            }
        }  , 5000 )



        // Retrieve NavController from the NavHostFragment
        val navHostFragment = binding.navHostFragment.getFragment() as NavHostFragment
        navController = navHostFragment.navController

        binding.bnvMainNavigation.setOnNavigationItemSelectedListener(this)

    }

    // This function show us the funcionality of the main navigation. MainActivity had to inherit BottomNavigationView class to be able to use this function.
    // This function will be removed after deploying the fragments.
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_Inicio -> {
                Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.nav_Staff -> {
                Toast.makeText(this, "Staff", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.nav_News -> {
                Toast.makeText(this, "Novedades", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.nav_Social -> {
                Toast.makeText(this, "Contribuir", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.nav_Contact -> {
                Toast.makeText(this, "Contacto", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }
}






