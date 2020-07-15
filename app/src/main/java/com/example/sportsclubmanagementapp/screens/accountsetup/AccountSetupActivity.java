package com.example.sportsclubmanagementapp.screens.accountsetup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

public class AccountSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

    }

    public void continueBtn(View view) { //onClick(Continue_btn)
        Intent intent = new Intent(this, MainActivity.class);
        boolean isValid;

        isValid = isGenderValid();
        isValid = isValid && isHeightValid();
        isValid = isValid && isWeightValid();
        isValid = isValid && isAgeValid();


        if (isValid) {
            startActivity(intent);
        }
    }

    private boolean isGenderValid() {
        RadioButton radioFemale = findViewById(R.id.femaleRadioButton);
        RadioButton radioMale = findViewById(R.id.maleRadioButton);
        TextView gender = (TextView)findViewById(R.id.genderTextView);
        return Utils.isGenderValid(radioFemale,radioMale,gender);
    }

    private boolean isHeightValid() {
        TextInputEditText height = findViewById(R.id.heightTextInputEditText);
        String heightInput = height.getText().toString().trim();

        return Utils.isHeightValid(heightInput,height);
    }

    private boolean isWeightValid() {
        TextInputEditText weight = findViewById(R.id.weightTextInputEditText);
        String weightInput = weight.getText().toString().trim();

        return Utils.isWeightValid(weightInput,weight);
    }

    private boolean isAgeValid() {
        TextInputEditText age = findViewById(R.id.ageTextInputEditeText);
        String ageInput = age.getText().toString().trim();

        return Utils.isAgeValid(ageInput,age);
    }
}