package com.example.sportsclubmanagementapp.screens.main;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.sportsclubmanagementapp.screens.calendar.CalendarActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.clubs.ClubsFragment;
import com.example.sportsclubmanagementapp.screens.main.fragments.events.EventsFragment;

import com.example.sportsclubmanagementapp.screens.main.fragments.home.HomeFragment;
import com.example.sportsclubmanagementapp.R;

import com.example.sportsclubmanagementapp.screens.main.fragments.workouts.WorkoutsFragment;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        //Bottom NavBar
        bottomNavigation = findViewById(R.id.nav_bar);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.drawerMenuProfile:
                intent = new Intent(this, MyProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.drawerMenuNotification:
                intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;

            case R.id.drawerMenuCalendar:
                intent = new Intent(this, CalendarActivity.class);
                startActivity(intent);
                break;

            case R.id.drawerMenuLogOut:
                
                break;
        }
        return false;
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


    public void goToNotificationsScreen(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
}