package com.nika.homefood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nika.homefood.R
import com.nika.homefood.ViewModel.HomeViewModel
import com.nika.homefood.ViewModel.HomelViewModelFactory
import com.nika.homefood.db.MealDataBase

class MainActivity : AppCompatActivity() {
     val viewModel :HomeViewModel by lazy {
         val mealDataBase=MealDataBase.getInstance(this)
         val homeViewModelProviderFactory=HomelViewModelFactory(mealDataBase)
         ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
     }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation=findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = Navigation.findNavController(this, R.id.frag_host)

        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}