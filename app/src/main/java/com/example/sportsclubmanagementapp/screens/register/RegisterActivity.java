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
        TextInputEditText firstAndLastName = (TextInputEditText) findViewById(R.id.firstAndLastNameTextInputEditText);
        String firstAndLastNameInput = firstAndLastName.getText().toString().trim();

        return Utils.isFirstAndLastNameValid(firstAndLastNameInput, firstAndLastName);
    }

    private boolean isEmailAddressValid() {
        TextInputEditText emailAddress = (TextInputEditText) findViewById(R.id.emailAdDressTextInputEditText);
        String emailAddressInput = emailAddress.getText().toString().trim();

        return Utils.isEmailAddressValid(emailAddressInput, emailAddress);
    }

    private boolean isPasswordValid() {
        TextInputEditText password = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);
        TextInputEditText confirmPassword = (TextInputEditText) findViewById(R.id.confirmPasswordTextInputEditText);
        String passwordInput = password.getText().toString().trim();
        String confirmPasswordInput = confirmPassword.getText().toString().trim();

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
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User already exists! Please log in or choose a new email.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (response.code() == 202) {
                    Toast.makeText(RegisterActivity.this, "Set up your account, to create your user!", Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        navigateAccountSetupActivity(userRegister.getFirst_and_last_name(),userRegister.getEmail(),userRegister.getPassword());
                    }, 3);
                } else {
                    Toast.makeText(RegisterActivity.this, "User already exists! Please log in or choose a new email.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private UserRegister getUserDetails(){
        TextInputEditText firstAndLastName = (TextInputEditText) findViewById(R.id.firstAndLastNameTextInputEditText);
        String firstAndLastNameInput = firstAndLastName.getText().toString().trim();
        TextInputEditText emailAddress = (TextInputEditText) findViewById(R.id.emailAdDressTextInputEditText);
        String emailAddressInput = emailAddress.getText().toString().trim();
        TextInputEditText password = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);
        TextInputEditText confirmPassword = (TextInputEditText) findViewById(R.id.confirmPasswordTextInputEditText);
        String passwordInput = password.getText().toString().trim();
        String confirmPasswordInput = confirmPassword.getText().toString().trim();

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