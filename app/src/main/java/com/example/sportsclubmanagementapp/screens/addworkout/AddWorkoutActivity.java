package com.example.sportsclubmanagementapp.screens.addworkout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Workouts;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWorkoutActivity extends AppCompatActivity {
    private long addWorkoutBtnClickTime = 0;
    private Spinner eventSpinner;
    private Spinner workoutEffectivenessSpinner;
    private TextInputEditText durationTextInputEditText;
    private TextInputEditText heartRateTextInputEditText;
    private TextInputEditText caloriesTextInputEditText;
    private TextInputEditText avgSpeedTextInputEditText;
    private TextInputEditText distanceTextInputEditText;
    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        initComponents();
        setToolbar();
        getEventsApi();
        setSpinner(new String[]{getString(R.string.low), getString(R.string.medium), getString(R.string.high), getString(R.string.workout_effectiveness)}, this.workoutEffectivenessSpinner);
    }

    private void initComponents() {
        this.eventSpinner = findViewById(R.id.eventSpinner);
        this.workoutEffectivenessSpinner = findViewById(R.id.workoutEffectivenessSpinner);
        this.durationTextInputEditText = findViewById(R.id.durationTextInputEditText);
        this.heartRateTextInputEditText = findViewById(R.id.heartRateTextInputEditText);
        this.caloriesTextInputEditText = findViewById(R.id.caloriesTextInputEditText);
        this.avgSpeedTextInputEditText = findViewById(R.id.averageSpeedTextInputEditText);
        this.distanceTextInputEditText = findViewById(R.id.distanceTextInputEditText);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.myProfileToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void getEventsApi() {
        Call<List<Event>> call = ApiHelper.getApi().getEvents(getToken());
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NotNull Call<List<Event>> call, @NotNull Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddWorkoutActivity.this, R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                } else {
                    events = response.body();
                    assert events != null;
                    filterJoinedEvents(events);
                    String[] eventsName = getEventsName(events);
                    setSpinner(eventsName, eventSpinner);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Event>> call, @NotNull Throwable t) {
                Toast.makeText(AddWorkoutActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), "no token");
    }

    private void filterJoinedEvents(List<Event> events) {
        ListIterator<Event> it = events.listIterator();
        while (it.hasNext()) {
            Event e = it.next();
            if (e.getStatus() == null) {
                it.remove();
            } else if (e.getStatus()[0] == 0) {
                it.remove();
            }
        }
    }

    private String[] getEventsName(List<Event> events) {
        String[] eventsName = new String[events.size() + 1];
        for (int i = 0; i < events.size(); i++) {
            eventsName[i] = events.get(i).getName();
        }
        eventsName[events.size()] = getString(R.string.event);
        return eventsName;
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


    public void onClickSaveWorkoutBtn(View view) {
        if (SystemClock.elapsedRealtime() - addWorkoutBtnClickTime < 2000) {
            return; //continue button is only clickable one time per 2 seconds, as Toast.LENGTH_SHORT
        }
        this.addWorkoutBtnClickTime = SystemClock.elapsedRealtime();
        boolean isValid;
        isValid = isEventValid() && isDurationValid() && isHearRateValid() && caloriesValid() && avgSpeedValid() && distanceValid() && workoutEffectivenessValid();
        if (isValid) {
            createUserWorkout();
        }
    }

    private boolean isEventValid() {
        return Utils.isEventValid(this.eventSpinner);
    }

    private boolean isDurationValid() {
        String durationInput = Objects.requireNonNull(this.durationTextInputEditText.getText()).toString().trim();
        return Utils.isDurationValid(durationInput, this.durationTextInputEditText);
    }

    private boolean isHearRateValid() {
        String heartInput = Objects.requireNonNull(this.heartRateTextInputEditText.getText()).toString().trim();
        return Utils.isHeartRateValid(heartInput, this.heartRateTextInputEditText);
    }

    private boolean caloriesValid() {
        String caloriesInput = Objects.requireNonNull(this.caloriesTextInputEditText.getText()).toString().trim();
        return Utils.isCaloriesValid(caloriesInput, this.caloriesTextInputEditText);
    }

    private boolean avgSpeedValid() {
        String avgSpeedInput = Objects.requireNonNull(this.avgSpeedTextInputEditText.getText()).toString().trim();
        return Utils.isAvgSpeedValid(avgSpeedInput, this.avgSpeedTextInputEditText);
    }

    private boolean distanceValid() {
        String distanceInput = Objects.requireNonNull(this.distanceTextInputEditText.getText()).toString().trim();
        return Utils.isDistanceValid(distanceInput, this.distanceTextInputEditText);
    }

    private boolean workoutEffectivenessValid() {
        return Utils.isWorkoutEffectivenessValid(this.workoutEffectivenessSpinner);
    }

    private void createUserWorkout() {
        Workouts workout = getWorkoutDetails();
        Call<Void> call = ApiHelper.getApi().createPostWorkout(getToken(), workout);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddWorkoutActivity.this, R.string.account_setup_not_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddWorkoutActivity.this, R.string.save_workout, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(AddWorkoutActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Workouts getWorkoutDetails() {
        return new Workouts(getEvent(), getDuration(), getDistance(), getHeartRate(), getCalories(), getAvgSpeed(), getWorkoutEffectiveness());
    }

    private int getEvent() {
        String choice = this.eventSpinner.getSelectedItem().toString();
        int eventId = 0;
        for (Event e : events) {
            if (e.getName().equals(choice)) {
                eventId = e.getId();
                break;
            }
        }
        return eventId;
    }

    private double getDuration() {
        return Double.parseDouble(Objects.requireNonNull(this.durationTextInputEditText.getText()).toString());
    }

    private double getHeartRate() {
        return Double.parseDouble(Objects.requireNonNull(this.heartRateTextInputEditText.getText()).toString());
    }

    private double getCalories() {
        return Double.parseDouble(Objects.requireNonNull(this.caloriesTextInputEditText.getText()).toString());
    }

    private double getAvgSpeed() {
        return Double.parseDouble(Objects.requireNonNull(this.avgSpeedTextInputEditText.getText()).toString());
    }

    private double getDistance() {
        return Double.parseDouble(Objects.requireNonNull(this.distanceTextInputEditText.getText()).toString());
    }

    private String getWorkoutEffectiveness() {
        return this.workoutEffectivenessSpinner.getSelectedItem().toString();
    }

}