package com.example.sportsclubmanagementapp.screens.myprofile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Notification;
import com.example.sportsclubmanagementapp.data.models.Sport;
import com.example.sportsclubmanagementapp.data.models.UserAccountSetup;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    Button buttonLoadImage;
    List<Notification> notification = new ArrayList<>();//for TESTS
    private Spinner primarySportSpinner;
    private Spinner secondarySportSpinner;
    private TextInputEditText height;
    private TextInputEditText weight;
    private TextInputEditText age;
    private String heightInput;
    private String weightInput;
    private String ageInput;
    private List<Sport> sports;
    private long saveChangesBtnLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initComponents();
        setMyProfileAvatar();
        setToolbar();
        setUpNotifications();
        getApiSports();
        getApiUserInfo();
        loadImage();
    }

    private void loadImage() {
        buttonLoadImage = (Button) findViewById(R.id.addPhotoMyProfile);
        buttonLoadImage.setOnClickListener(arg0 -> {

            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            assert selectedImage != null;
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            String[] path = picturePath.split("/");
            buttonLoadImage.setText(path[path.length-1]);
        }


    }

    private void initComponents() {
        primarySportSpinner = findViewById(R.id.primarySportSpinnerMyProfile);
        secondarySportSpinner = findViewById(R.id.secondarySportSpinnerMyProfile);
        height = findViewById(R.id.heightTextInputEditTextMyProfile);
        weight = findViewById(R.id.weightTextInputEditTextMyProfile);
        age = findViewById(R.id.ageTextInputEditTextMyProfile);
    }

    private void initData() {
        this.heightInput = Objects.requireNonNull(height.getText()).toString().trim();
        this.weightInput = Objects.requireNonNull(this.weight.getText()).toString().trim();
        this.ageInput = Objects.requireNonNull(this.age.getText()).toString().trim();
    }

    private void setMyProfileAvatar() {
        Glide.with(getBaseContext())
                .load(Utils.getAvatars(getBaseContext()).get(new Random().nextInt(5)))
                .apply(new RequestOptions().circleCrop())
                .into((ImageView) findViewById(R.id.avatar));
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
        if (notification.isEmpty()) {
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar, null));
        } else {
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar_news, null));
        }
    }

    public void goToNotificationsScreen(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        startActivity(new Intent(this, NotificationActivity.class));
    }

    private void getApiSports() {
        Call<List<Sport>> call = ApiHelper.getApi().getSports();
        call.enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(@NotNull Call<List<Sport>> call, @NotNull Response<List<Sport>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(MyProfileActivity.this, R.string.api_response_not_successful + response.code(), Toast.LENGTH_SHORT).show();
                else {
                    sports = response.body();
                    setSpinner(Objects.requireNonNull(sports), primarySportSpinner);
                    setSpinner(sports, secondarySportSpinner);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Sport>> call, @NotNull Throwable t) {
                Toast.makeText(MyProfileActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        workoutEffectivenessAdapter.add(getString(R.string.select_your_favorite_sport));
        spinner.setAdapter(workoutEffectivenessAdapter);
        spinner.setSelection(workoutEffectivenessAdapter.getCount());
    }

    private void getApiUserInfo() {
        Call<UserAccountSetup> call = ApiHelper.getApi().getUserData(getToken());
        call.enqueue(new Callback<UserAccountSetup>() {
            @Override
            public void onResponse(@NotNull Call<UserAccountSetup> call, @NotNull Response<UserAccountSetup> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MyProfileActivity.this, R.string.api_response_not_successful + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    assert response.body() != null;
                    setUserDataToViews(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserAccountSetup> call, @NotNull Throwable t) {
                Toast.makeText(MyProfileActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), getString(R.string.no_token_prefs));
    }

    @SuppressLint("SetTextI18n")
    private void setUserDataToViews(UserAccountSetup userData) {
        TextView userName = findViewById(R.id.accountSetupTextView);
        userName.setText(userData.getFirst_name() + " " + userData.getLast_name());
        this.height.setText(String.valueOf(userData.getHeight()));
        this.weight.setText(String.valueOf(userData.getWeight()));
        this.age.setText(String.valueOf(userData.getAge()));
        primarySportSpinner.setSelection(userData.getPrimary_sport() - 1);
        secondarySportSpinner.setSelection(userData.getSecondary_sport() - 1);
    }

    public void onClickSaveChangesBtn(View view) {
        if (SystemClock.elapsedRealtime() - saveChangesBtnLastClickTime < 2000) {
            return; //save changes button is only clickable one time per second
        }
        saveChangesBtnLastClickTime = SystemClock.elapsedRealtime();
        boolean isValid;
        initData();
        isValid = Utils.isPrimarySportValid(this.primarySportSpinner) && Utils.isSecondarySportValid(this.secondarySportSpinner) && Utils.isHeightValid(heightInput, height) && Utils.isWeightValid(weightInput, this.weight) && Utils.isAgeValid(ageInput, this.age);
        if (isValid) {
            putApiUserNewInfo();
        }
    }

    private void putApiUserNewInfo() {
        UserAccountSetup userNewInfo = getUserInfo();
        Call<Void> call = ApiHelper.getApi().updateUserProfile(getToken(), userNewInfo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MyProfileActivity.this, R.string.api_response_not_successful + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyProfileActivity.this, R.string.api_my_profile_changes, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(MyProfileActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private UserAccountSetup getUserInfo() {
        return new UserAccountSetup(getAge(), getHeight(), getWeight(), getPrimarySport(), getSecondarySport());
    }

    private int getAge() {
        return Integer.parseInt(Objects.requireNonNull(this.age.getText()).toString().trim());
    }

    private double getWeight() {
        return Double.parseDouble(Objects.requireNonNull(this.weight.getText()).toString().trim());
    }

    private double getHeight() {
        return Double.parseDouble(Objects.requireNonNull(this.height.getText()).toString().trim());
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