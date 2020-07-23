package com.example.sportsclubmanagementapp.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.UserRegister;
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
    private TextInputEditText firstAndLastName;
    private TextInputEditText emailAddress;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();
    }

    private void initComponents() {
        firstAndLastName = findViewById(R.id.firstAndLastNameTextInputEditText);
        emailAddress = findViewById(R.id.emailAdDressTextInputEditText);
        password = findViewById(R.id.passwordTextInputEditText);
        confirmPassword = findViewById(R.id.confirmPasswordTextInputEditText);
    }

    public void onClickRegisterBtn(View view) {

        boolean isValid;
        isValid = isFirstAndLastNameValid() && isEmailAddressValid() && isPasswordValid();
        if (isValid) {
            createUserRegister();
        }
    }

    private boolean isFirstAndLastNameValid() {
        String firstAndLastNameInput = Objects.requireNonNull(firstAndLastName.getText()).toString().trim();
        return Utils.isFirstAndLastNameValid(firstAndLastNameInput, firstAndLastName);
    }

    private boolean isEmailAddressValid() {
        String emailAddressInput = Objects.requireNonNull(emailAddress.getText()).toString().trim();
        return Utils.isEmailAddressValid(emailAddressInput, emailAddress);
    }

    private boolean isPasswordValid() {
        String passwordInput = Objects.requireNonNull(password.getText()).toString().trim();
        String confirmPasswordInput = Objects.requireNonNull(confirmPassword.getText()).toString().trim();
        return Utils.isPasswordValid(passwordInput, confirmPasswordInput, password, confirmPassword);
    }

    public void onClickLogInBtn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void createUserRegister() {
        UserRegister userRegister = getUserDetails();
        Call<Void> call = ApiHelper.getApi().createPostUserRegister(userRegister);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful())
                    Toast.makeText(RegisterActivity.this, "User already exists! Please log in or choose a new email.", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(RegisterActivity.this, "Set up your account, to create your user!", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(() -> navigateAccountSetupActivity(userRegister.getFirst_and_last_name(), userRegister.getEmail(), userRegister.getPassword()), 2000);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private UserRegister getUserDetails() {
        String firstAndLastNameInput = Objects.requireNonNull(firstAndLastName.getText()).toString().trim();
        String emailAddressInput = Objects.requireNonNull(emailAddress.getText()).toString().trim();
        String passwordInput = Objects.requireNonNull(password.getText()).toString().trim();
        String confirmPasswordInput = Objects.requireNonNull(confirmPassword.getText()).toString().trim();
        return new UserRegister(emailAddressInput, firstAndLastNameInput, passwordInput, confirmPasswordInput);
    }

    private void navigateAccountSetupActivity(String firstAndLastName, String emailAddress, String password) {
        Intent intent = new Intent(RegisterActivity.this, AccountSetupActivity.class);
        intent.putExtra("name", firstAndLastName);
        intent.putExtra("email", emailAddress);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}