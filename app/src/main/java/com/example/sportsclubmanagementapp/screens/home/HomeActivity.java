package com.example.sportsclubmanagementapp.screens.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sportsclubmanagementapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigation;

        bottomNavigation = findViewById(R.id.nav_bar);
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                //openFragment(HomeFragment.newInstance("", ""));
                                return true;
                            case R.id.nav_clubs:
                                //openFragment(ClubsFragment.newInstance("", ""));
                                return true;
                            case R.id.nav_events:
                                //openFragment(EventsFragment.newInstance("", ""));
                                return true;
                            case R.id.nav_workouts:
                                //openFragment(WorkoutsFragment.newInstance("", ""));
                                return true;
                        }
                        return false;
                    }
                };
    }
}