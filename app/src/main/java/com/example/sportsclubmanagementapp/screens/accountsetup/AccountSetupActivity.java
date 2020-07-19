package com.example.sportsclubmanagementapp.screens.accountsetup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        setSpinner(new String[]{"Primary Sport 1","Primary Sport 2","Primary Sport 3","Primary Sport"}, (Spinner)findViewById(R.id.primarySportSpinner));
        setSpinner(new String[]{"Secondary Sport 1","Secondary Sport 2","Secondary Sport 3","Secondary Sport"}, (Spinner)findViewById(R.id.secondarySportSpinner));
    }

    public void onClickContinueBtn(View view) { //onClick(Continue_btn)
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

    private void setSpinner(String[] items, Spinner spinner) {
        ArrayAdapter<String> workoutEffectivenessAdapter = new ArrayAdapter<String>(this, R.layout.spinner_row) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };

        workoutEffectivenessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < items.length; i++) {
            workoutEffectivenessAdapter.add(items[i]);
        }
        spinner.setAdapter(workoutEffectivenessAdapter);
        spinner.setSelection(workoutEffectivenessAdapter.getCount());
    }
}