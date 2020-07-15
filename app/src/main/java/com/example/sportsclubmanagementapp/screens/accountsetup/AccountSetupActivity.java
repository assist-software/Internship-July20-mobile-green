package com.example.sportsclubmanagementapp.screens.accountsetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class AccountSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

    }

    public void continueBtn(View view) { //onClick(Continue_btn)
        Intent intent = new Intent(this, MainActivity.class);
        boolean isValid = true;

        isValid = isValid && isGenderValid();
        isValid = isValid && isHeightValid();
        isValid = isValid && isWeightValid();
        isValid = isValid && isAgeValid();

        if (isValid){
            startActivity(intent);
        }

    }

    private boolean isGenderValid(){
        RadioButton radioFemale = findViewById(R.id.femaleRadioButton);
        RadioButton radioMale = findViewById(R.id.maleRadioButton);
        if (!(radioFemale.isChecked() || radioMale.isChecked())){
            return false;
        }
        return true;
    }
    private boolean isHeightValid(){
        TextInputEditText height = findViewById(R.id.heightTextInputEditText);
        String heightInput = height.getText().toString().trim();
        if (heightInput.isEmpty()){
            height.setError("Field can't be empty");
            return false;
        }
        try{
            int heightNumber = Integer.parseInt(heightInput);
            if (heightNumber<80){
                height.setError("Height is not valid! Please enter your height in cm.");
                return false;
            }
        }
        catch(NumberFormatException e){
            height.setError("Height is not valid! Please enter your height in cm.");
            return false;
        }

        return true;
    }

    private boolean isWeightValid(){
        TextInputEditText weight = findViewById(R.id.weightTextInputEditText);
        String weightInput = weight.getText().toString().trim();
        if (weightInput.isEmpty()){
            weight.setError("Field can't be empty");
            return false;
        }

        try{
            int weightNumber = Integer.parseInt(weightInput);
        }
        catch(NumberFormatException e){
            weight.setError("Weight is not valid! Please enter your weight in kg.");
            return false;
        }

        return true;
    }

    private boolean isAgeValid(){
        TextInputEditText age = findViewById(R.id.ageTextInputEditeText);
        String ageInput = age.getText().toString().trim();
        if (ageInput.isEmpty()){
            age.setError("Field can't be empty");
            return false;
        }

        try{
            int ageNumber = Integer.parseInt(ageInput);
        }
        catch(NumberFormatException e){
            age.setError("Age is not valid!");
            return false;
        }

        return true;
    }
}