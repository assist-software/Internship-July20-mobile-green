package com.example.sportsclubmanagementapp.screens.guest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;

import com.example.sportsclubmanagementapp.screens.register.RegisterActivity;

public class GuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        Button login_btn = findViewById(R.id.login_btn);
        Button register_btn = findViewById(R.id.register_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GuestActivity.this, LoginActivity.class));
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GuestActivity.this, RegisterActivity.class));
            }
        });
    }
}