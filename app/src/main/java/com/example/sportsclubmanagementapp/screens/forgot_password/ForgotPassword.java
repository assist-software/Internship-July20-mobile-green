package com.example.sportsclubmanagementapp.screens.forgot_password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;
import com.example.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private String emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    private void checkEmailExists() { //check if user email exists in database
        Map<String, String> email = new HashMap<>();
        email.put("Email", emailInput);
        Call<Void> call = ApiHelper.getApi().createPostEmail(email);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getBaseContext(), getResources().getText(R.string.email_not_exists), Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getBaseContext(), getResources().getText(R.string.forgot_password_sended), Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(() -> startActivity(new Intent(ForgotPassword.this, LoginActivity.class)), 2000);
                }
            }
            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(ForgotPassword.this, getString(R.string.api_failure) + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToLoginScreenAndSendEmail(View view){
        email = findViewById(R.id.email);
        emailInput = email.getText().toString();
        if( Utils.isEmailAddressValid(emailInput, email)){
            checkEmailExists();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void goToLoginScreen(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void goToBrowser(View view){
        String url = "http://25.35.52.160:8001/user/reset-password/";
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(url)));
    }
}