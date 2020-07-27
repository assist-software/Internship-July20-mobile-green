package com.example.sportsclubmanagementapp.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
    private String firstAndLastNameInput;
    private String emailAddressInput;
    private String passwordInput;
    private String confirmPasswordInput;
    private long registerBtnLastClickTime = 0;
    private long logInBtnLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();
    }

    private void initComponents() {
        this.firstAndLastName = findViewById(R.id.firstAndLastNameTextInputEditText);
        this.emailAddress = findViewById(R.id.emailAdDressTextInputEditText);
        this.password = findViewById(R.id.passwordTextInputEditText);
        this.confirmPassword = findViewById(R.id.confirmPasswordTextInputEditText);
    }

    private void initData() {
        this.firstAndLastNameInput = Objects.requireNonNull(this.firstAndLastName.getText()).toString().trim();
        this.emailAddressInput = Objects.requireNonNull(this.emailAddress.getText()).toString().trim();
        this.passwordInput = Objects.requireNonNull(this.password.getText()).toString().trim();
        this.confirmPasswordInput = Objects.requireNonNull(this.confirmPassword.getText()).toString().trim();
    }

    public void onClickRegisterBtn(View view) {
        if (SystemClock.elapsedRealtime() - registerBtnLastClickTime < 1000) {
            return;
        }
        this.registerBtnLastClickTime = SystemClock.elapsedRealtime();
        initData();  //get new data every time when user clicks on register button
        boolean isValid;
        isValid = isFirstAndLastNameValid() && isEmailAddressValid() && isPasswordValid();
        if (isValid) {
            createUserRegister();
        }
    }

    private boolean isFirstAndLastNameValid() {
        return Utils.isFirstAndLastNameValid(this.firstAndLastNameInput, this.firstAndLastName);
    }

    private boolean isEmailAddressValid() {
        return Utils.isEmailAddressValid(this.emailAddressInput, this.emailAddress);
    }

    private boolean isPasswordValid() {
        return Utils.isPasswordValid(this.passwordInput, this.confirmPasswordInput, this.password, this.confirmPassword);
    }

    public void onClickLogInBtn(View view) {
        if (SystemClock.elapsedRealtime() - logInBtnLastClickTime < 2000) {
            return; //log in button is only clickable one time per 2 seconds, as Toast.LENGTH_SHORT
        }
        logInBtnLastClickTime = SystemClock.elapsedRealtime();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void createUserRegister() {
        Call<Void> call = ApiHelper.getApi().createPostUserRegister(new UserRegister(this.emailAddressInput, this.firstAndLastNameInput, this.passwordInput, this.confirmPasswordInput));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful())
                    Toast.makeText(RegisterActivity.this, R.string.register_not_successful, Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(RegisterActivity.this, R.string.register_successful, Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> navigateAccountSetupActivity(), 2000);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(RegisterActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateAccountSetupActivity() { //pass data to account setup screen to make a post api request, to create new user
        Intent intent = new Intent(RegisterActivity.this, AccountSetupActivity.class);
        intent.putExtra("name", this.firstAndLastNameInput);
        intent.putExtra("email", this.emailAddressInput);
        intent.putExtra("password", this.passwordInput);
        startActivity(intent);
    }
}