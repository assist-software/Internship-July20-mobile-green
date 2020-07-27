package com.example.sportsclubmanagementapp.screens.forgot_password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private String emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void goToLoginScreenAndSendEmail(View view){
        email = findViewById(R.id.email);
        emailInput = email.getText().toString();
        if( Utils.isEmailAddressValid(emailInput, email)){
            Toast.makeText(getBaseContext(), getResources().getText(R.string.forgot_password_sended), Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void goToLoginScreen(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
}