package com.example.sportsclubmanagementapp.screens.guest;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;
import com.example.sportsclubmanagementapp.screens.register.RegisterActivity;

public class GuestActivity extends AppCompatActivity {
    private long logInBtnLastClickTime = 0;
    private long registerBtnLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
    }

    public void onClickLoginGuest(View view) {
        if (SystemClock.elapsedRealtime() - registerBtnLastClickTime < 1000) {
            return; //register button is only clickable one time for a second
        }
        this.registerBtnLastClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(GuestActivity.this, LoginActivity.class));
    }

    public void onClickRegisterGuest(View view) {
        if (SystemClock.elapsedRealtime() - logInBtnLastClickTime < 1000) {
            return; //login button is only clickable one time for a second
        }
        this.logInBtnLastClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(GuestActivity.this, RegisterActivity.class));
    }
}