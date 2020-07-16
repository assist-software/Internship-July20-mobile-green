package com.example.sportsclubmanagementapp.screens.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagementapp.screens.guest.GuestActivity;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.register.RegisterActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View view){
        startActivity(new Intent(this, MainActivity.class));
        /*boolean isValid;

        isValid = isEmailAddressValid();
        isValid = isValid && isPasswordValid();

        if (isValid) {
            startActivity(new Intent(this, MainActivity.class));
        }*/
    }

    private boolean isEmailAddressValid() {
        TextInputEditText emailAddress = findViewById(R.id.email);
        String emailAddressInput = emailAddress.getText().toString().trim();

        return Utils.isEmailAddressValid(emailAddressInput,emailAddress);
    }

    private boolean isPasswordValid() {
        TextInputEditText password = findViewById(R.id.password);
        String passwordInput = password.getText().toString().trim();

        return Utils.isPasswordValid(passwordInput,password);
    }

    public void onClickNewHere(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }
}