package com.example.sportsclubmanagementapp.screens.accountsetup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Sport;
import com.example.sportsclubmanagementapp.data.models.UserAccountSetup;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.login.LoginActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Objects;


public class AccountSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);


        getApiSports();
    }


    public void onClickContinueBtn(View view) {
        boolean isValid;

        isValid = isGenderValid();
        isValid = isValid && isHeightValid();
        isValid = isValid && isWeightValid();
        isValid = isValid && isAgeValid();
        if (isValid) {
            createUserAccountSetup();
        }
    }

    private boolean isGenderValid() {
        RadioButton radioFemale = findViewById(R.id.femaleRadioButton);
        RadioButton radioMale = findViewById(R.id.maleRadioButton);
        TextView gender = findViewById(R.id.genderTextView);
        return Utils.isGenderValid(radioFemale, radioMale, gender);
    }

    private boolean isHeightValid() {
        TextInputEditText height = findViewById(R.id.heightTextInputEditText);
        String heightInput = Objects.requireNonNull(height.getText()).toString().trim();

        return Utils.isHeightValid(heightInput, height);
    }

    private boolean isWeightValid() {
        TextInputEditText weight = findViewById(R.id.weightTextInputEditText);
        String weightInput = Objects.requireNonNull(weight.getText()).toString().trim();

        return Utils.isWeightValid(weightInput, weight);
    }

    private boolean isAgeValid() {
        TextInputEditText age = findViewById(R.id.ageTextInputEditeText);
        String ageInput = Objects.requireNonNull(age.getText()).toString().trim();

        return Utils.isAgeValid(ageInput, age);
    }

    private void setSpinner(List<Sport> items, Spinner spinner) {
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
        for (int i = 0; i < items.size(); i++) {
            workoutEffectivenessAdapter.add(items.get(i).getSportName());

        }
        workoutEffectivenessAdapter.add("Select your favorite sport:");
        spinner.setAdapter(workoutEffectivenessAdapter);
        spinner.setSelection(workoutEffectivenessAdapter.getCount());
    }

    private void createUserAccountSetup() {
        UserAccountSetup userAccountSetup = getUserDetails();
        Call<Void> call = ApiHelper.getApi().createPostUserAccountSetup(userAccountSetup);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AccountSetupActivity.this, "Data is not valid!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AccountSetupActivity.this, "User has been created!", Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(() -> goToLogInActivity(), 3);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(AccountSetupActivity.this, "Error failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private UserAccountSetup getUserDetails() {
        String email = Objects.requireNonNull(getIntent().getExtras()).getString("email");
        String firstAndLastName = getIntent().getExtras().getString("name");
        String[] name = Objects.requireNonNull(firstAndLastName).split(" ");
        String firstName = name[0];
        String lastName = name[1];
        String password = getIntent().getExtras().getString("password");
        int gender = getGender();
        int role = 2;
        int age = getAge();
        double weight = getWeight();
        double height = getHeight();
        String primarySport = getPrimarySport();
        String secondarySport = getSecondarySport();
        return new UserAccountSetup(email, firstName, lastName, gender, role, age, password, height, weight, primarySport, secondarySport);
    }

    private int getGender() {
        RadioButton radioFemale = findViewById(R.id.femaleRadioButton);
        if (radioFemale.isChecked()) return 1;
        return 0;
    }

    private int getAge() {
        TextInputEditText age = findViewById(R.id.ageTextInputEditeText);
        return Integer.parseInt(Objects.requireNonNull(age.getText()).toString().trim());
    }

    private double getWeight() {
        TextInputEditText weight = findViewById(R.id.weightTextInputEditText);
        return Double.parseDouble(Objects.requireNonNull(weight.getText()).toString().trim());
    }

    private double getHeight() {
        TextInputEditText weight = findViewById(R.id.heightTextInputEditText);
        return Double.parseDouble(Objects.requireNonNull(weight.getText()).toString().trim());
    }

    private String getPrimarySport() {
        Spinner spinner = findViewById(R.id.primarySportSpinner);
        return spinner.getSelectedItem().toString();
    }

    private String getSecondarySport() {
        Spinner spinner = findViewById(R.id.secondarySportSpinner);
        return spinner.getSelectedItem().toString();
    }

    private void goToLogInActivity() {
        Intent intent = new Intent(AccountSetupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void getApiSports() {
        Call<List<Sport>> call = ApiHelper.getApi().getSports();
        call.enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(@NotNull Call<List<Sport>> call, @NotNull Response<List<Sport>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AccountSetupActivity.this, "Error response: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Sport> sports = response.body();
                setSpinner(Objects.requireNonNull(sports), findViewById(R.id.primarySportSpinner));
                setSpinner(sports, findViewById(R.id.secondarySportSpinner));
            }

            @Override
            public void onFailure(@NotNull Call<List<Sport>> call, @NotNull Throwable t) {
                Toast.makeText(AccountSetupActivity.this, "Error failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}