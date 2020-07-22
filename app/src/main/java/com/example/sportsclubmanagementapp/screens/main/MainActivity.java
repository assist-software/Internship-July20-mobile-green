package com.example.sportsclubmanagementapp.screens.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.addworkout.AddWorkoutActivity;
import com.example.sportsclubmanagementapp.screens.calendar.CalendarActivity;
import com.example.sportsclubmanagementapp.screens.guest.GuestActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.clubs.ClubsFragment;
import com.example.sportsclubmanagementapp.screens.main.fragments.events.EventsFragment;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.HomeFragment;
import com.example.sportsclubmanagementapp.screens.main.fragments.workouts.WorkoutsFragment;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigation;
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
    private DrawerLayout drawer;

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
        switch (item.getItemId()) {
            case R.id.drawerMenuProfile:
                goToMyProfileScreen();
                break;
            case R.id.drawerMenuNotification:
                goToNotificationsScreen();
                break;

            case R.id.drawerMenuCalendar:
                goToCalendarScreen();
                break;

            case R.id.drawerMenuLogOut:
                deteleSharePreferencesToken();
                Toast.makeText(this, "Log out successful!", Toast.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    goToGuestScreen();
                }, 3);
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

    public void goToNotificationsScreen(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void goToAddWorkoutScreen(View view) {
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        startActivity(intent);
    }

    private void goToMyProfileScreen() {
        Intent intent = new Intent(this, MyProfileActivity.class);
        startActivity(intent);
    }

    private void goToNotificationsScreen() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void goToCalendarScreen() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    private void goToGuestScreen() {
        Intent intent = new Intent(this, GuestActivity.class);
        startActivity(intent);
    }

    private void deteleSharePreferencesToken() {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.user_token), "no token");
        editor.apply();
    }


}