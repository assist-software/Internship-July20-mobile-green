package com.example.sportsclubmanagementapp.screens.guest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;

import com.example.sportsclubmanagementapp.screens.register.RegisterActivity;

public class GuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
    }

    public void onClickLoginGuest(View view){
        startActivity(new Intent(GuestActivity.this, LoginActivity.class));
    }

    public void onClickRegisterGuest(View view){
        startActivity(new Intent(GuestActivity.this, RegisterActivity.class));
    }
}