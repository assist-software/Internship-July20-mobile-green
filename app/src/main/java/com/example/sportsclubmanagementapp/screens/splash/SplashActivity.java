package com.example.sportsclubmanagementapp.screens.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.guest.GuestActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        redirectToGuestScreen();
    }

    private void redirectToGuestScreen(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent = new Intent(SplashActivity.this, GuestActivity.class);
               startActivity(intent);
            }
        }, 1);
    }
}