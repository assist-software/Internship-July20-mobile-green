package com.example.sportsclubmanagementapp.screens.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Notification;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigation;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener;
    private List<Notification> notification = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNotifications(); //set up notification icon for top toolbar
        //set up left navigation drawer
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        //Bottom NavBar
        setListenerForNavigation(); //set up listener for bottom navigation
        openFragment(HomeFragment.newInstance());
    }

    private void setUpNotifications() {
        //for TESTS
        notification.add(new Notification("2 min ago", "Coach", "John Down", "invited you in", "Running Club"));

        ImageView notificationIcon = findViewById(R.id.notificationImageView);
        if (notification.isEmpty())
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar, null));
        else
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar_news, null));
    }

    private void setListenerForNavigation() {
        navigationItemSelectedListener = item -> {
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
        };

        bottomNavigation = findViewById(R.id.nav_bar);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
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
                deleteSharePreferencesToken();
                Toast.makeText(this, "Log out successful!", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(this::goToGuestScreen, 3);
                break;
        }
        return false;
    }

    public void openFragment(Fragment fragment) {
        //open selected fragment and display it inside the container
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

    private void deleteSharePreferencesToken() {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.user_token), "no token");
        editor.apply();
    }
}