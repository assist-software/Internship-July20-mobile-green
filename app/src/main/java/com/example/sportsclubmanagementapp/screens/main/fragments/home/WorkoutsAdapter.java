package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Workout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutsViewHolder> {

    private List<Workout> workouts;
    private Context context;

    public WorkoutsAdapter(List<Workout> workouts, Context context) {
        this.workouts = workouts;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkoutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workouts, parent, false);
        return new WorkoutsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsViewHolder holder, int position) {
        try {
            holder.bind(workouts.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.workouts.size();
    }

    public Context getContext() {
        return this.context;
    }

    public static class WorkoutsViewHolder extends RecyclerView.ViewHolder {
        private TextView day_number;
        private TextView month;
        private TextView day_name;
        private TextView year;
        private TextView distance;
        private TextView duration;
        private TextView calories;
        private TextView bpm;

        public WorkoutsViewHolder(@NonNull View itemView) {
            super(itemView);
            day_number = itemView.findViewById(R.id.day_number);
            month = itemView.findViewById(R.id.month);
            day_name = itemView.findViewById(R.id.day_name);
            year = itemView.findViewById(R.id.year);
            distance = itemView.findViewById(R.id.distance);
            duration = itemView.findViewById(R.id.duration);
            calories = itemView.findViewById(R.id.calories);
            bpm = itemView.findViewById(R.id.bpm);
        }

        @SuppressLint("DefaultLocale")
        public void bind(Workout workout) throws ParseException {
            SimpleDateFormat dateFormatReceived = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MMM-dd");
            Date date = dateFormatReceived.parse(workout.getDate());
            String dateStrNeeded = dateFormatNeeded.format(date);
            String[] dateParsed = dateStrNeeded.split("-"); //parse data to char(-)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(dateFormatNeeded.parse(dateStrNeeded)));
            String[] days = new String[]{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
            String dayNameStr = days[calendar.get(Calendar.DAY_OF_WEEK) - 1];

            day_number.setText(dateParsed[2]);
            month.setText(dateParsed[1]);
            day_name.setText(dayNameStr);
            year.setText(dateParsed[0]);
            distance.setText(String.format("%.1f", workout.getDistance()));
            duration.setText(String.format("%.1f", workout.getDuration()));
            calories.setText(String.format("%.1f", workout.getCalories_burned()));
            bpm.setText(String.valueOf(workout.getBpm()));
        }
    }
}
