package com.example.sportsclubmanagementapp.screens.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.UserLogIn;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.register.RegisterActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText emailAddress;
    private TextInputEditText password;

    private long logInBtnLastClickTime = 0;
    private long registerBtnLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private void initComponents() {
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void onClickLogin(View view) {
        boolean isValid = isEmailAddressValid() && isPasswordValid();
        if (isValid) {
            checkUserExists();
        }
    }

    private boolean isEmailAddressValid() {
        String emailAddressInput = Objects.requireNonNull(emailAddress.getText()).toString().trim();
        return Utils.isEmailAddressValid(emailAddressInput, emailAddress);
    }

    private boolean isPasswordValid() {
        String passwordInput = Objects.requireNonNull(password.getText()).toString().trim();
        return Utils.isPasswordValid(passwordInput, password);
    }

    public void onClickNewHere(View view) {
        if (SystemClock.elapsedRealtime() - registerBtnLastClickTime < 1000) return;
        registerBtnLastClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void checkUserExists() {
        String emailAddressInput = Objects.requireNonNull(emailAddress.getText()).toString().trim();
        String passwordInput = Objects.requireNonNull(password.getText()).toString().trim();
        UserLogIn userLogIn = new UserLogIn(emailAddressInput, passwordInput);
        Call<UserLogIn> call = ApiHelper.getApi().createPostUserLogIn(userLogIn);
        call.enqueue(new Callback<UserLogIn>() {
            @Override
            public void onResponse(@NotNull Call<UserLogIn> call, @NotNull Response<UserLogIn> response) {
                if (!response.isSuccessful())
                    Toast.makeText(LoginActivity.this, R.string.log_in_not_successful, Toast.LENGTH_LONG).show();
                else {
                    sharePreferencesToken(Objects.requireNonNull(response.body()).getToken());
                    Toast.makeText(LoginActivity.this, R.string.log_in_successful, Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(() -> goToMainScreen(), 2000);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserLogIn> call, @NotNull Throwable t) {
                Toast.makeText(LoginActivity.this, getString(R.string.api_failure) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToMainScreen() {
        if (SystemClock.elapsedRealtime() - logInBtnLastClickTime < 2000) return;
        logInBtnLastClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void sharePreferencesToken(String token) {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.user_token), token);
        editor.apply();
    }
}