package com.example.sportsclubmanagementapp.screens.addworkout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.example.sportsclubmanagementapp.R;

public class AddWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        setToolbar();
        setSpinner(new String[]{"Low", "Medium", "High", "Workout Effectiveness"}, findViewById(R.id.workoutEffectivenessSpinner));
        setSpinner(new String[]{"Event 1", "Event 2", "Event 3", "Select Event"}, findViewById(R.id.eventSpinner));
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.myProfileToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
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

    }
}