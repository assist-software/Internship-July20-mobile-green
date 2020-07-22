package com.example.sportsclubmanagementapp.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.accountsetup.AccountSetupActivity;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void onClickRegisterBtn(View view) {

        boolean isValid;
        isValid = isFirstAndLastNameValid();
        isValid = isValid && isEmailAddressValid();
        isValid = isValid && isPasswordValid();

        if (isValid) {
            createUserRegister();
        }
    }

    private boolean isFirstAndLastNameValid() {
        TextInputEditText firstAndLastName = findViewById(R.id.firstAndLastNameTextInputEditText);
        String firstAndLastNameInput = Objects.requireNonNull(firstAndLastName.getText()).toString().trim();

        return Utils.isFirstAndLastNameValid(firstAndLastNameInput, firstAndLastName);
    }

    private boolean isEmailAddressValid() {
        TextInputEditText emailAddress = findViewById(R.id.emailAdDressTextInputEditText);
        String emailAddressInput = Objects.requireNonNull(emailAddress.getText()).toString().trim();

        return Utils.isEmailAddressValid(emailAddressInput, emailAddress);
    }

    private boolean isPasswordValid() {
        TextInputEditText password = findViewById(R.id.passwordTextInputEditText);
        TextInputEditText confirmPassword = findViewById(R.id.confirmPasswordTextInputEditText);
        String passwordInput = Objects.requireNonNull(password.getText()).toString().trim();
        String confirmPasswordInput = Objects.requireNonNull(confirmPassword.getText()).toString().trim();

        return Utils.isPasswordValid(passwordInput, confirmPasswordInput, password, confirmPassword);
    }

    public void onClickLogInBtn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void createUserRegister() {
        TextInputEditText firstAndLastName = findViewById(R.id.firstAndLastNameTextInputEditText);
        String firstAndLastNameInput = Objects.requireNonNull(firstAndLastName.getText()).toString().trim();
        TextInputEditText emailAddress = findViewById(R.id.emailAdDressTextInputEditText);
        String emailAddressInput = Objects.requireNonNull(emailAddress.getText()).toString().trim();
        TextInputEditText password = findViewById(R.id.passwordTextInputEditText);
        TextInputEditText confirmPassword = findViewById(R.id.confirmPasswordTextInputEditText);
        String passwordInput = Objects.requireNonNull(password.getText()).toString().trim();
        String confirmPasswordInput = Objects.requireNonNull(confirmPassword.getText()).toString().trim();

        User userRegister = new User(emailAddressInput, firstAndLastNameInput, passwordInput, confirmPasswordInput);
        Call<User> call = ApiHelper.getApi().createPostUserRegister(userRegister);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Error response: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                if (response.code() == 200) {
                    Toast.makeText(RegisterActivity.this, "User successfully created! ", Toast.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(() -> navigateAccountSetupActivity(), 3);
                } else {
                    Toast.makeText(RegisterActivity.this, "User already exists! Please log in.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error1: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateAccountSetupActivity() {
        Intent intent = new Intent(RegisterActivity.this, AccountSetupActivity.class);
        startActivity(intent);
    }
}