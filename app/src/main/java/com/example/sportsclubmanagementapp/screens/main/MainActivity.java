package com.example.sportsclubmanagementapp.screens.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sportsclubmanagementapp.screens.main.fragments.clubs.ClubsFragment;
import com.example.sportsclubmanagementapp.screens.main.fragments.events.EventsFragment;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.HomeFragment;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.main.fragments.workouts.WorkoutsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.nav_bar);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance());
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentsContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            openFragment(HomeFragment.newInstance());
                            return true;
                        case R.id.nav_clubs:
                            openFragment(ClubsFragment.newInstance());
                            return true;
                        case R.id.nav_events:
                            openFragment(EventsFragment.newInstance());
                            return true;
                        case R.id.nav_workouts:
                            openFragment(WorkoutsFragment.newInstance());
                            return true;
                    }
                    return false;
                }
            };
}