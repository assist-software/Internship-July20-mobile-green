package com.example.sportsclubmanagementapp.screens.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.guest.GuestActivity;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        redirectToNextScreen();
    }

    private void redirectToNextScreen() {  //if user has no token then he is redirected to guest screen else to main screen
        SharedPreferences prefs = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE);
        String token = prefs.getString(getString(R.string.user_token), "no token");
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (Objects.requireNonNull(token).equals("no token")) {
                startActivity(new Intent(SplashActivity.this, GuestActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, 3000);
    }
}