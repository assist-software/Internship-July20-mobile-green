package com.example.sportsclubmanagementapp.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {  //onClick(registerButton)
        Intent intent = new Intent(this, AccountSetupActivity.class);
        boolean isValid;

        isValid = isFirstAndLastNameValid();
        isValid = isValid && isEmailAddressValid();
        isValid = isValid && isPasswordValid();


        if (isValid) {
            startActivity(intent);
        }
    }

    private boolean isFirstAndLastNameValid() {
        TextInputEditText firstAndLastName = (TextInputEditText) findViewById(R.id.firstAndLastNameTextInputEditText);
        String firstAndLastNameInput = firstAndLastName.getText().toString().trim();

        return Utils.isFirstAndLastNameValid(firstAndLastNameInput,firstAndLastName);
    }

    private boolean isEmailAddressValid() {
        TextInputEditText emailAddress = (TextInputEditText) findViewById(R.id.emailAdDressTextInputEditText);
        String emailAddressInput = emailAddress.getText().toString().trim();

        return Utils.isEmailAddressValid(emailAddressInput,emailAddress);
    }

    private boolean isPasswordValid() {
        TextInputEditText password = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);
        TextInputEditText confirmPassword = (TextInputEditText) findViewById(R.id.confirmPasswordTextInputEditText);
        String passwordInput = password.getText().toString().trim();
        String confirmPasswordInput = confirmPassword.getText().toString().trim();

        return Utils.isPasswordValid(passwordInput,confirmPasswordInput,password,confirmPassword);
    }

    public void logIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}