package com.example.sportsclubmanagementapp.screens.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
    private Drawable avatar;

    private long addWorkoutBtnClickTime = 0;
    private long notificationToolbarBtnClickTime = 0;
    private long notificationDrawerBtnClickTime = 0;
    private long myProfileToolbarBtnClickTime = 0;
    private long myProfileDrawerBtnClickTime = 0;
    private long calendarDrawerBtnClickTime = 0;
    private long logOutDrawerBtnClickTime = 0;

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
        if (SystemClock.elapsedRealtime() - notificationToolbarBtnClickTime < 1000) return;
        notificationToolbarBtnClickTime = SystemClock.elapsedRealtime();
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void goToAddWorkoutScreen(View view) {
        if (SystemClock.elapsedRealtime() - addWorkoutBtnClickTime < 1000) return;
        addWorkoutBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, AddWorkoutActivity.class));
    }

    private void goToMyProfileScreen() {
        if (SystemClock.elapsedRealtime() - myProfileDrawerBtnClickTime < 1000) return;
        myProfileDrawerBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, MyProfileActivity.class));
    }

    public void goToMyProfileScreen(View view) {
        if (SystemClock.elapsedRealtime() - myProfileToolbarBtnClickTime < 1000) return;
        myProfileToolbarBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, MyProfileActivity.class));
    }

    private void goToNotificationsScreen() {
        if (SystemClock.elapsedRealtime() - notificationDrawerBtnClickTime < 1000) return;
        notificationDrawerBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, NotificationActivity.class));
    }

    private void goToCalendarScreen() {
        if (SystemClock.elapsedRealtime() - calendarDrawerBtnClickTime < 1000) return;
        calendarDrawerBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, CalendarActivity.class));
    }

    private void goToGuestScreen() {
        if (SystemClock.elapsedRealtime() - logOutDrawerBtnClickTime < 1000) return;
        logOutDrawerBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, GuestActivity.class));
    }

    private void deleteSharePreferencesToken() {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.user_token), "no token");
        editor.apply();
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }
}