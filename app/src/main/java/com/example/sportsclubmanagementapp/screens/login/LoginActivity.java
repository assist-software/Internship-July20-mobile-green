package com.example.sportsclubmanagementapp.screens.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.data.models.UserLogIn;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.register.RegisterActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View view) {

        boolean isValid;
        isValid = isEmailAddressValid();
        isValid = isValid && isPasswordValid();

        if (isValid) {
            checkUserExists();
        }
    }

    private boolean isEmailAddressValid() {
        TextInputEditText emailAddress = findViewById(R.id.email);
        String emailAddressInput = emailAddress.getText().toString().trim();

        return Utils.isEmailAddressValid(emailAddressInput, emailAddress);
    }

    private boolean isPasswordValid() {
        TextInputEditText password = findViewById(R.id.password);
        String passwordInput = password.getText().toString().trim();

        return Utils.isPasswordValid(passwordInput, password);
    }

    public void onClickNewHere(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void checkUserExists() {
        TextInputEditText emailAddress = findViewById(R.id.email);
        String emailAddressInput = emailAddress.getText().toString().trim();
        TextInputEditText password = findViewById(R.id.password);
        String passwordInput = password.getText().toString().trim();

        UserLogIn userLogIn = new UserLogIn(emailAddressInput, passwordInput);
        Call<UserLogIn> call = ApiHelper.getApi().createPostUserLogIn(userLogIn);
        call.enqueue(new Callback<UserLogIn>() {
            @Override
            public void onResponse(Call<UserLogIn> call, Response<UserLogIn> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Your email or password is incorrect! If you don't have an account then create one!", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    sharePreferencesToken(response.body().getToken());

                    Toast.makeText(LoginActivity.this, "Log in is successful!", Toast.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        goToMainScreen();
                    }, 3);
                }
            }

            @Override
            public void onFailure(Call<UserLogIn> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToMainScreen(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void sharePreferencesToken(String token){
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MY_PREFS_NAME),MODE_PRIVATE).edit();
        editor.putString(getString(R.string.user_token),token);
        editor.apply();
    }
}