package com.example.sportsclubmanagementapp.screens.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.guest.GuestActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(GuestActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#1a1a1a"))
                .withAfterLogoText("SPORTS CLUB")
                .withLogo(R.mipmap.ic_launcher_round);

        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextSize(22);
        config.getAfterLogoTextView().setLetterSpacing((float)0.06);
        View easySplashScreen = config.create();

        setContentView(easySplashScreen);
    }


}