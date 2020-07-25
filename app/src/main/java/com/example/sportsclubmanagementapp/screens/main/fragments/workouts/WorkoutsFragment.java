package com.example.sportsclubmanagementapp.screens.main.fragments.workouts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Workouts;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.WorkoutsAdapter;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class WorkoutsFragment extends Fragment {

    //for workouts recycler
    private List<Workouts> workoutsList = new ArrayList<>();
    private RecyclerView recyclerViewWorkouts;
    private WorkoutsAdapter WorkoutsAdapter;
    private Activity activity;

    public static WorkoutsFragment newInstance() {
        return new WorkoutsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

    private void setToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        CircleImageView avatar_toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.avatar_toolbar);
        avatar_toolbar.setVisibility(View.VISIBLE);
        assert mainActivity != null;
        Glide.with(this)
                .load(mainActivity.getAvatar())
                .apply(new RequestOptions().circleCrop())
                .into(avatar_toolbar);
        TextView fragment_title = getActivity().findViewById(R.id.fragment_title);
        fragment_title.setText(getResources().getText(R.string.workouts));

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getActivity(), MyProfileActivity.class)));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewWorkouts = view.findViewById(R.id.workouts_recycler_view);
        activity = getActivity();

        makeWorkouts();
        prepareWorkoutsData();
        setTodayWorkout();
    }

    @SuppressLint("DefaultLocale")
    private void setTodayWorkout() {
        Date currentDate = Calendar.getInstance().getTime(); //get current date
        SimpleDateFormat dateFormated = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()); //format current date
        String[] dateParsed = dateFormated.format(currentDate).split("-"); //parse data to char(-)

        //get day name
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        String[] days = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
        String dayNameStr = days[calendar.get(Calendar.DAY_OF_WEEK)-1];

        TextView day_number = activity.findViewById(R.id.dayNumberTodayWorkout);
        day_number.setText(dateParsed[0]);
        TextView month = activity.findViewById(R.id.monthTodayWorkout);
        month.setText(dateParsed[1]);
        TextView day_name = activity.findViewById(R.id.dayNameTodayWorkout);
        day_name.setText(dayNameStr);
        TextView year = activity.findViewById(R.id.yearTodayWorkout);
        year.setText(dateParsed[2]);
        TextView distance = activity.findViewById(R.id.distanceTodayWorkout);
        distance.setText(String.format("%.2f", workoutsList.get(0).getDistance()));
        TextView duration = activity.findViewById(R.id.durationTodayWorkout);
        duration.setText(String.format("%.2f", workoutsList.get(0).getDuration()));
        TextView calories = activity.findViewById(R.id.caloriesTodayWorkout);
        calories.setText(String.format("%.2f", workoutsList.get(0).getCalories_burned()));
        TextView bpm = activity.findViewById(R.id.bpmTodayWorkout);
        bpm.setText(String.valueOf(120));
    }

    private void makeWorkouts(){
        WorkoutsAdapter = new WorkoutsAdapter(workoutsList, getContext());
        RecyclerView.LayoutManager workoutsLayoutManager = new LinearLayoutManager(WorkoutsAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewWorkouts.setLayoutManager(workoutsLayoutManager);
        recyclerViewWorkouts.setAdapter(WorkoutsAdapter);
    }
    private void prepareWorkoutsData() {
        workoutsList.add(new Workouts(1, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        workoutsList.add(new Workouts(2, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        workoutsList.add(new Workouts(3, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        WorkoutsAdapter.notifyDataSetChanged();
    }

}