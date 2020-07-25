package com.example.sportsclubmanagementapp.screens.myprofile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Notification;
import com.example.sportsclubmanagementapp.data.models.Sport;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {

    //for TESTS
    List<Notification> notification = new ArrayList<>();

    private Spinner primarySportSpinner;
    private Spinner secondarySportSpinner;
    private TextInputEditText height;
    private TextInputEditText weight;
    private TextInputEditText age;
    private List<Sport> sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getApiSports();
        setToolbar();
        setUpNotifications();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponent();
        Glide.with(getBaseContext())
                .load(Utils.getAvatars(getBaseContext()).get(new Random().nextInt(5)))
                .apply(new RequestOptions().circleCrop())
                .into((ImageView) findViewById(R.id.avatar));
    }

    private void initComponent() {
        primarySportSpinner = findViewById(R.id.primarySportSpinnerMyProfile);
        secondarySportSpinner = findViewById(R.id.secondarySportSpinnerMyProfile);
        height = findViewById(R.id.heightTextInputEditTextMyProfile);
        weight = findViewById(R.id.weightTextInputEditTextMyProfile);
        age = findViewById(R.id.ageTextInputEditTextMyProfile);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.myProfileToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setUpNotifications() {
        //for TESTS
        notification.add(new Notification("2 min ago", "Coach", "John Down", "invited you in", "Running Club"));
        ImageView notificationIcon = findViewById(R.id.notificationImageView);
        if (notification.isEmpty())
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar, null));
        else
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar_news, null));
    }

    public void goToNotificationsScreen(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void onClickSaveChangesBtn(View view) {
        boolean isValid;
        isValid = isPrimarySportValid() && isSecondarySportValid() && isHeightValid() && isWeightValid() && isAgeValid();
        if (isValid) {
            //post request api
        } else {
            // if nothing is changed, do nothing
            // else change only one specific detail, with other remaining the same
        }
    }

    private boolean isPrimarySportValid() {
        return Utils.isPrimarySportValid(primarySportSpinner);
    }

    private boolean isSecondarySportValid() {
        return Utils.isSecondarySportValid(secondarySportSpinner);
    }

    private boolean isHeightValid() {
        String heightInput = Objects.requireNonNull(height.getText()).toString().trim();
        return Utils.isHeightValid(heightInput, height);
    }

    private boolean isWeightValid() {
        String weightInput = Objects.requireNonNull(weight.getText()).toString().trim();
        return Utils.isWeightValid(weightInput, weight);
    }

    private boolean isAgeValid() {
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

    private void getApiSports() {
        Call<List<Sport>> call = ApiHelper.getApi().getSports();
        call.enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(@NotNull Call<List<Sport>> call, @NotNull Response<List<Sport>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(MyProfileActivity.this, "Error response: " + response.code(), Toast.LENGTH_SHORT).show();
                else {
                    sports = response.body();
                    setSpinner(Objects.requireNonNull(sports), primarySportSpinner);
                    setSpinner(sports, secondarySportSpinner);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Sport>> call, @NotNull Throwable t) {
                Toast.makeText(MyProfileActivity.this, "Error failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getAge() {
        return Integer.parseInt(Objects.requireNonNull(age.getText()).toString().trim());
    }

    private double getWeight() {
        return Double.parseDouble(Objects.requireNonNull(weight.getText()).toString().trim());
    }

    private double getHeight() {
        return Double.parseDouble(Objects.requireNonNull(weight.getText()).toString().trim());
    }

    private int getPrimarySport() {
        String choice = primarySportSpinner.getSelectedItem().toString();
        int sportId = 0;
        for (Sport sport : sports) {
            if (choice.equals(sport.getSportName())) {
                sportId = sport.getId();
                break;
            }
        }
        return sportId;
    }

    private int getSecondarySport() {
        String choice = secondarySportSpinner.getSelectedItem().toString();
        int sportId = 0;
        for (Sport sport : sports) {
            if (choice.equals(sport.getSportName())) {
                sportId = sport.getId();
                break;
            }
        }
        return sportId;
    }
}