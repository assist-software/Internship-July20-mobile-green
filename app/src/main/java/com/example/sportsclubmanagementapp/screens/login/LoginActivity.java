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
import com.example.sportsclubmanagementapp.screens.forgot_password.ForgotPassword;
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
    private String emailAddressInput;
    private String passwordInput;
    private long logInBtnLastClickTime = 0;
    private long registerBtnLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private void initComponents() {
        this.emailAddress = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
    }

    private void initData(){
        this.emailAddressInput = Objects.requireNonNull(this.emailAddress.getText()).toString().trim();
        this.passwordInput = Objects.requireNonNull(this.password.getText()).toString().trim();
    }

    public void onClickLogin(View view) {
        initData();
        boolean isValid = Utils.isEmailAddressValid(this.emailAddressInput, this.emailAddress) && Utils.isPasswordValid(this.passwordInput, this.password);
        if (isValid) {
            checkUserExists();
        }
    }

    public void goToForgotPassword(View view){
        startActivity(new Intent(this, ForgotPassword.class));
    }

    public void onClickNewHereRegister(View view) {
        if (SystemClock.elapsedRealtime() - registerBtnLastClickTime < 1000) {
            return; //register button is only clickable one time per second
        }
        this.registerBtnLastClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void goToMainScreen() {
        if (SystemClock.elapsedRealtime() - logInBtnLastClickTime < 2000) {
            return; //log in button is only clickable one time per 2 seconds, as Toast.LENGTH_SHORT
        }
        this.logInBtnLastClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void checkUserExists() { //check if user email exists in database and password is correct with post api request
        UserLogIn userLogIn = new UserLogIn(this.emailAddressInput, this.passwordInput);
        Call<UserLogIn> call = ApiHelper.getApi().createPostUserLogIn(userLogIn);
        call.enqueue(new Callback<UserLogIn>() {
            @Override
            public void onResponse(@NotNull Call<UserLogIn> call, @NotNull Response<UserLogIn> response) {
                if (!response.isSuccessful())
                    Toast.makeText(LoginActivity.this, R.string.log_in_not_successful, Toast.LENGTH_SHORT).show();
                else {
                    sharePreferencesToken(Objects.requireNonNull(response.body()).getToken());
                    Toast.makeText(LoginActivity.this, R.string.log_in_successful, Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> goToMainScreen(), 2000);
                }
            }
            @Override
            public void onFailure(@NotNull Call<UserLogIn> call, @NotNull Throwable t) {
                Toast.makeText(LoginActivity.this, getString(R.string.api_failure) + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sharePreferencesToken(String token) { //save token for user locally, for next api calls
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.user_token), token);
        editor.apply();
    }
}