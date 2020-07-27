package com.example.sportsclubmanagementapp.screens.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

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
        setListenerForDrawerNavigation(); //set up listener navigation drawer
        setListenerForBottomNavigation(); //set up listener for bottom navigation
        openFragment(HomeFragment.newInstance()); //default fragment
    }

    private void setUpNotifications() { //for TESTS
        notification.add(new Notification("2 min ago", "Coach", "John Down", "invited you in", "Running Club"));
        ImageView notificationIcon = findViewById(R.id.notificationImageView);
        if (notification.isEmpty()) {
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar, null));
        } else {
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar_news, null));
        }
    }

    private void setListenerForDrawerNavigation() {
        NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
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
                    Toast.makeText(this, "Log out successful!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(this::goToGuestScreen, 2000);
                    break;
            }
            return false;
        };
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private void setListenerForBottomNavigation() {
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
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
        BottomNavigationView bottomNavigation = findViewById(R.id.nav_bar);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    public void openFragment(Fragment fragment) { //open selected fragment and display it inside the container
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentsContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToNotificationsScreen(View view) {
        if (SystemClock.elapsedRealtime() - notificationToolbarBtnClickTime < 1000) {
            return; //notification toolbar button is only clickable one time for a second
        }
        this.notificationToolbarBtnClickTime = SystemClock.elapsedRealtime();
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        startActivity(new Intent(this, NotificationActivity.class));
    }

    private void goToNotificationsScreen() {
        if (SystemClock.elapsedRealtime() - notificationDrawerBtnClickTime < 1000) {
            return; //notification drawer button is only clickable one time for a second
        }
        this.notificationDrawerBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void goToAddWorkoutScreen(View view) {
        if (SystemClock.elapsedRealtime() - addWorkoutBtnClickTime < 1000) {
            return; //add workout button is only clickable one time for a second
        }
        this.addWorkoutBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, AddWorkoutActivity.class));
    }

    private void goToMyProfileScreen() {
        if (SystemClock.elapsedRealtime() - myProfileDrawerBtnClickTime < 1000) {
            return; //my profile drawer button button is only clickable one time for a second
        }
        this.myProfileDrawerBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, MyProfileActivity.class));
    }

    public void goToMyProfileScreen(View view) {
        if (SystemClock.elapsedRealtime() - myProfileToolbarBtnClickTime < 1000) {
            return; //my profile toolbar button is only clickable one time for a second
        }
        this.myProfileToolbarBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, MyProfileActivity.class));
    }

    private void goToCalendarScreen() {
        if (SystemClock.elapsedRealtime() - calendarDrawerBtnClickTime < 1000) {
            return; //calendar drawer button is only clickable one time for a second
        }
        this.calendarDrawerBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, CalendarActivity.class));
    }

    private void goToGuestScreen() {
        if (SystemClock.elapsedRealtime() - logOutDrawerBtnClickTime < 1000) {
            return; //log out drawer button is only clickable one time for a second
        }
        this.logOutDrawerBtnClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, GuestActivity.class));
    }

    private void deleteSharePreferencesToken() { //when user log out, token is deleted from share preferences
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