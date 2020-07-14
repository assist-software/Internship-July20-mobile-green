package com.example.sportsclubmanagementapp.screens.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sportsclubmanagementapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button register_btn = findViewById(R.id.register_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(new Intent(GuestActivity.this, LoginActivity.class));
            }
        });
    }
}