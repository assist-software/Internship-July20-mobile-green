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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutsViewHolder> {

    private List<Workouts> workouts;
    private Context context;

    public WorkoutsAdapter(List<Workouts> workouts, Context context) {
        this.workouts = workouts;
        this.context = context;
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

        public void bind(Workouts workouts) {
            Date currentDate = Calendar.getInstance().getTime(); //get current date
            SimpleDateFormat dateFormated = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()); //format current date
            String[] dateParsed = dateFormated.format(currentDate).split("-"); //parse data to char(-)

            //get day name
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            String[] days = new String[] { "SATURDAY", "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" };
            String dayNameStr = days[calendar.get(Calendar.DAY_OF_WEEK)];

            day_number.setText(dateParsed[0]);
            month.setText(dateParsed[1]);
            day_name.setText(dayNameStr);
            year.setText(dateParsed[2]);
            distance.setText(String.format("%.2f", workouts.getDistance()));
            duration.setText(String.format("%.2f", workouts.getDuration()));
            calories.setText(String.format("%.2f", workouts.getCalories_burned()));
            bpm.setText(String.valueOf(120));
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
