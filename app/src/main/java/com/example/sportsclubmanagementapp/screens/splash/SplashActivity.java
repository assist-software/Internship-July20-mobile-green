package com.example.sportsclubmanagementapp.screens.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.guest.GuestActivity;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        redirectToGuestScreen();
    }

    private void redirectToGuestScreen() {

        SharedPreferences prefs = getSharedPreferences(getString(R.string.MY_PREFS_NAME),MODE_PRIVATE);
        String token = prefs.getString(getString(R.string.user_token),"no token");
        //Toast.makeText(this,"token is : " + token,Toast.LENGTH_LONG).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token.equals("no token")){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);  //should be changed
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }, 3000);
    }
}