package com.example.food_recepti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //NavHostFragment navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        //NavController navController = navHostFragment.getNavController();
        //BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        //NavigationUI.setupWithNavController(bottomNav, navController);
    }
}