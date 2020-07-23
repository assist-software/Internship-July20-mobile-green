package com.example.sportsclubmanagementapp.screens.myprofile;

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
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MyProfileActivity extends AppCompatActivity {

    List<Notification> notification = new ArrayList<>();
    private List<Drawable> avatars; //for TESTS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        prepareAvatars(); //set random avatar for TESTS
        displayAvatar();

        setSpinner(new String[]{"Primary Sport 1", "Primary Sport 2", "Primary Sport 3", "Primary Sport"},
                findViewById(R.id.primarySportSpinnerMyProfile));
        setSpinner(new String[]{"Secondary Sport 1", "Secondary Sport 2", "Secondary Sport 3", "Secondary Sport"},
                findViewById(R.id.secondarySportSpinnerMyProfile));

        setToolbar();
        setUpNotifications();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.myProfileToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
    private void displayAvatar() {
        Glide.with(this)
                .load(avatars.get(new Random().nextInt(5)))
                .apply(new RequestOptions().circleCrop())
                .into((ImageView) findViewById(R.id.avatar));
    }


    private void setUpNotifications() {
        //for TESTS
        notification.add(new Notification("2 min ago", "Coach", "John Down", "invited you in", "Running Club"));

        ImageView notificationIcon = findViewById(R.id.notificationImageView);
        if( notification.isEmpty() )
            notificationIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_notifications_toolbar, null));
        else
            notificationIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_notifications_toolbar_news, null));
    }

    public void goToNotificationsScreen(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void onClickSaveChangesBtn(View view) {
        boolean isValid;

        isValid = isHeightValid();
        isValid = isValid && isWeightValid();
        isValid = isValid && isAgeValid();

        if (isValid) {
            //post request api
        } else {
            // if nothing is changed, do nothing
            // else change only one specific detail, with other remaining the same
        }
    }

    private boolean isHeightValid() {
        TextInputEditText height = findViewById(R.id.heightTextInputEditTextMyProfile);
        String heightInput = Objects.requireNonNull(height.getText()).toString().trim();

        return Utils.isHeightValid(heightInput, height);
    }

    private boolean isWeightValid() {
        TextInputEditText weight = findViewById(R.id.weightTextInputEditTextMyProfile);
        String weightInput = Objects.requireNonNull(weight.getText()).toString().trim();

        return Utils.isWeightValid(weightInput, weight);
    }

    private boolean isAgeValid() {
        TextInputEditText age = findViewById(R.id.ageTextInputEditeTextMyProfile);
        String ageInput = Objects.requireNonNull(age.getText()).toString().trim();

        return Utils.isAgeValid(ageInput, age);
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
        for (String item : items) {
            workoutEffectivenessAdapter.add(item);
        }
        spinner.setAdapter(workoutEffectivenessAdapter);
        spinner.setSelection(workoutEffectivenessAdapter.getCount());
    }

    private void prepareAvatars() {
        avatars = new ArrayList<>();
        avatars.add(ContextCompat.getDrawable(Objects.requireNonNull(getBaseContext()), R.drawable.avatar_1));
        avatars.add(ContextCompat.getDrawable(Objects.requireNonNull(getBaseContext()), R.drawable.avatar_2));
        avatars.add(ContextCompat.getDrawable(Objects.requireNonNull(getBaseContext()), R.drawable.avatar_3));
        avatars.add(ContextCompat.getDrawable(Objects.requireNonNull(getBaseContext()), R.drawable.avatar_4));
        avatars.add(ContextCompat.getDrawable(Objects.requireNonNull(getBaseContext()), R.drawable.avatar_5));
    }
}