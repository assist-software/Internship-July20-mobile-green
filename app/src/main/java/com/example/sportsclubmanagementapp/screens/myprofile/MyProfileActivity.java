package com.example.sportsclubmanagementapp.screens.myprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setToolbar();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.myProfileToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

    public void goToNotificationsScreen(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void saveChangesBtn(View view){
        boolean isValid;

        isValid = isHeightValid();
        isValid = isValid && isWeightValid();
        isValid = isValid && isAgeValid();

        if (isValid){
                //post request api
        }
        else{
                // if nothing is changed, do nothing
            // else change only one specific detail, with other remaining the same
        }
    }

    private boolean isHeightValid() {
        TextInputEditText height = findViewById(R.id.heightTextInputEditTextMyProfile);
        String heightInput = height.getText().toString().trim();

        return Utils.isHeightValid(heightInput,height);
    }

    private boolean isWeightValid() {
        TextInputEditText weight = findViewById(R.id.weightTextInputEditTextMyProfile);
        String weightInput = weight.getText().toString().trim();

        return Utils.isWeightValid(weightInput,weight);
    }

    private boolean isAgeValid() {
        TextInputEditText age = findViewById(R.id.ageTextInputEditeTextMyProfile);
        String ageInput = age.getText().toString().trim();

        return Utils.isAgeValid(ageInput,age);
    }
}