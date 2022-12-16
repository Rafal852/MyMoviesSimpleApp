package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.MoviesAdapter
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val nacController = Navigation.findNavController(this, R.id.container_fragment)

        NavigationUI.setupWithNavController(bottomNavigation, nacController)

    }
}