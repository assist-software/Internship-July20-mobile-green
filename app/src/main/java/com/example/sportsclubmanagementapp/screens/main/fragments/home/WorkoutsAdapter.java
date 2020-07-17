package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Workouts;

import java.util.List;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutsViewHolder> {

    private List<Workouts> workouts;
    private Context context;

    public WorkoutsAdapter(List<Workouts> workouts, Context context) {
        this.workouts = workouts;
        this.context = context;
    }

    public class WorkoutsViewHolder extends RecyclerView.ViewHolder {
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

        public void bind(Workouts workouts) {
            day_number.setText("20");
            month.setText("July");
            day_name.setText("Monday");
            year.setText("2020");
            distance.setText(String.format("%.2f", workouts.getDistance()));
            duration.setText(String.valueOf(workouts.getDuration()));
            calories.setText(String.format("%.2f", workouts.getCalories_burned()));
            bpm.setText("150");
        }
    }

    @NonNull
    @Override
    public WorkoutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workouts, parent, false);
        return new WorkoutsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsViewHolder holder, int position) {
            holder.bind(workouts.get(position));
    }

    @Override
    public int getItemCount() {
        return this.workouts.size();
    }

    public Context getContext(){
        return this.context;
    }
}
