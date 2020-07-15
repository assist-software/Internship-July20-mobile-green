package com.example.sportsclubmanagementapp.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;
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
        boolean isValid = true;

        isValid = isValid && isFirstAndLastNameValid();
        isValid = isValid && isEmailAddressValid();
        isValid = isValid && isPasswordValid();


        if (isValid) {
            startActivity(intent);
        }
    }

    private boolean isFirstAndLastNameValid() {

        Pattern FIRST_AND_LAST_NAME_PATTERN = Pattern.compile("[a-zA-Z]*[\\s][a-zA-Z].*");
        TextInputEditText firstAndLastName = (TextInputEditText) findViewById(R.id.firstAndLastNameTextInputEditText);
        String firstAndLastNameInput = firstAndLastName.getText().toString().trim();

        if (firstAndLastNameInput.isEmpty()){
            firstAndLastName.setError("Field can't be empty!");
            return false;
        }
        else if (!FIRST_AND_LAST_NAME_PATTERN.matcher(firstAndLastNameInput).matches()){
            firstAndLastName.setError("Field is not valid! Please enter your name like \"First Last\"!");
            return false;
        }
        return true;
    }

    private boolean isEmailAddressValid() {
        TextInputEditText emailAddress = (TextInputEditText) findViewById(R.id.emailAdDressTextInputEditText);
        String emailAddressInput = emailAddress.getText().toString().trim();

        if (emailAddressInput.isEmpty()) {
            emailAddress.setError("Field can't be empty!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressInput).matches()) {
            emailAddress.setError("Please enter a valid email address!");
            return false;
        }
        return true;
    }

    private boolean isPasswordValid() {
        //at least 6 characters with at least 1 digit, 1 lower case letter, 1 upper case letter, and no whitespaces.
        Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");

        TextInputEditText password = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);
        TextInputEditText confirmPassword = (TextInputEditText) findViewById(R.id.confirmPasswordTextInputEditText);
        String passwordInput = password.getText().toString().trim();
        String confirmPasswordInput = confirmPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty!");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password too weak! It should contain at least 6 characters with at least one digit, one lower case letter, one upper case letter, and now whitespaces.");
            return false;
        } else if (confirmPasswordInput.isEmpty()) {
            confirmPassword.setError("Pleare repeat your password here!");
            return false;
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            confirmPassword.setError("Passwords are not equal!");
            return false;
        }


        return true;
    }


    public void logIn(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}