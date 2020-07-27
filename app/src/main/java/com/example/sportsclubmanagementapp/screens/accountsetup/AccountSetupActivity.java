package com.example.sportsclubmanagementapp.screens.accountsetup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountSetupActivity extends AppCompatActivity {
    private RadioButton radioFemale;
    private RadioButton radioMale;
    private Spinner primarySportSpinner;
    private Spinner secondarySportSpinner;
    private TextInputEditText height;
    private TextInputEditText weight;
    private TextInputEditText age;
    private List<Sport> sports;
    private long continueBtnLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        initComponents();
        getApiSports();
    }

    private void initComponents() {
        this.radioFemale = findViewById(R.id.femaleRadioButton);
        this.radioMale = findViewById(R.id.maleRadioButton);
        this.primarySportSpinner = findViewById(R.id.primarySportSpinner);
        this.secondarySportSpinner = findViewById(R.id.secondarySportSpinner);
        this.height = findViewById(R.id.heightTextInputEditText);
        this.weight = findViewById(R.id.weightTextInputEditText);
        this.age = findViewById(R.id.ageTextInputEditeText);
    }

    private void getApiSports() { //get all kinds of sports from api
        Call<List<Sport>> call = ApiHelper.getApi().getSports();
        call.enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(@NotNull Call<List<Sport>> call, @NotNull Response<List<Sport>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(AccountSetupActivity.this, getString(R.string.api_response_not_successful) + response.code(), Toast.LENGTH_SHORT).show();
                else {
                    sports = response.body();
                    setSpinner(Objects.requireNonNull(sports), primarySportSpinner);
                    setSpinner(sports, secondarySportSpinner);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Sport>> call, @NotNull Throwable t) {
                Toast.makeText(AccountSetupActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSpinner(List<Sport> items, Spinner spinner) { //populating spinner with sports from api
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

    public void onClickContinueBtn(View view) {
        if (SystemClock.elapsedRealtime() - continueBtnLastClickTime < 2000) {
            return; //continue button is only clickable one time per 2 seconds, as Toast.LENGTH_SHORT
        }
        this.continueBtnLastClickTime = SystemClock.elapsedRealtime();
        boolean isValid;
        TextView gender = findViewById(R.id.genderTextView);
        String heightInput = Objects.requireNonNull(this.height.getText()).toString().trim();
        String weightInput = Objects.requireNonNull(this.weight.getText()).toString().trim();
        String ageInput = Objects.requireNonNull(this.age.getText()).toString().trim();
        isValid = Utils.isGenderValid(this.radioFemale, this.radioMale, gender) && Utils.isPrimarySportValid(this.primarySportSpinner) && Utils.isSecondarySportValid(this.secondarySportSpinner)
                && Utils.isHeightValid(heightInput, this.height) && Utils.isWeightValid(weightInput, this.weight) && Utils.isAgeValid(ageInput, this.age);
        if (isValid) {
            createUserAccountSetup();
        }
    }

    private void createUserAccountSetup() { //create user with post api request
        UserAccountSetup userAccountSetup = getUserDetails();
        Call<Void> call = ApiHelper.getApi().createPostUserAccountSetup(userAccountSetup);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful())
                    Toast.makeText(AccountSetupActivity.this, R.string.account_setup_not_successful, Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(AccountSetupActivity.this, R.string.account_setup_successful, Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> goToLogInActivity(), 2000);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(AccountSetupActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
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
        return new UserAccountSetup(email, firstName, lastName, getGender(), 2, getAge(), password, getHeight(), getWeight(), getPrimarySport(), getSecondarySport());
    }

    private int getGender() {
        if (this.radioFemale.isChecked()) return 1;
        return 0;
    }

    private int getAge() {
        return Integer.parseInt(Objects.requireNonNull(this.age.getText()).toString().trim());
    }

    private double getWeight() {
        return Double.parseDouble(Objects.requireNonNull(this.weight.getText()).toString().trim());
    }

    private double getHeight() {
        return Double.parseDouble(Objects.requireNonNull(this.weight.getText()).toString().trim());
    }

    private int getPrimarySport() {
        String choice = this.primarySportSpinner.getSelectedItem().toString();
        int sportId = 0;
        for (Sport sport : this.sports) {
            if (choice.equals(sport.getSportName())) {
                sportId = sport.getId();
                break;
            }
        }
        return sportId;
    }

    private int getSecondarySport() {
        String choice = this.secondarySportSpinner.getSelectedItem().toString();
        int sportId = 0;
        for (Sport sport : this.sports) {
            if (choice.equals(sport.getSportName())) {
                sportId = sport.getId();
                break;
            }
        }
        return sportId;
    }

    private void goToLogInActivity() {
        startActivity(new Intent(AccountSetupActivity.this, LoginActivity.class));
    }

}