package com.example.sportsclubmanagementapp.screens.main.fragments.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Workout;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.WorkoutsAdapter;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;
import com.example.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WorkoutsFragment extends Fragment {
    //recycler
    private RecyclerView recyclerViewWorkouts;

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
        setToolbarAvatar();
        setToolbarTitle();
        setToolbarIconNavigation();
    }

    private void setToolbarAvatar() {
        CircleImageView avatar_toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.avatar_toolbar);
        avatar_toolbar.setVisibility(View.VISIBLE); //set the avatar visible (because is hidden for home fragment)
        Utils.setCircleAvatar(getActivity(), Objects.requireNonNull((MainActivity) getActivity()).getAvatar(), avatar_toolbar);
    }

    private void setToolbarTitle() {
        TextView fragment_title = Objects.requireNonNull(getActivity()).findViewById(R.id.fragment_title);
        fragment_title.setText(getResources().getText(R.string.events_txt));
    }

    private void setToolbarIconNavigation() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getActivity(), MyProfileActivity.class)));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycle(view);
        getApiWorkouts();
    }

    private void initRecycle(View view) {
        recyclerViewWorkouts = view.findViewById(R.id.workouts_recycler_view);
    }

    private void getApiWorkouts() {
        Call<List<Workout>> call = ApiHelper.getApi().getWorkouts(getToken());
        call.enqueue(new Callback<List<Workout>>() {
            @Override
            public void onResponse(@NotNull Call<List<Workout>> call, @NotNull Response<List<Workout>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                } else {
                    initWorkoutsAdapter(response.body(), recyclerViewWorkouts);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    assert response.body() != null;
                    try {
                        checkWorkout(response.body().stream().filter(workouts -> workouts.getDate().equals(dateFormatter.format(date))).collect(Collectors.toList()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Workout>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), "no token");
    }

    private void initWorkoutsAdapter(List<Workout> workouts, RecyclerView recyclerView) {
        WorkoutsAdapter adapter = new WorkoutsAdapter(workouts, getContext());
        RecyclerView.LayoutManager workoutsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(workoutsLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void checkWorkout(List<Workout> workouts) throws ParseException {
        LinearLayout linearLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.todayWorkoutView);
        if (workouts.size() == 0) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            Workout workout = workouts.get(0);
            SimpleDateFormat dateFormatReceived = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MMM-dd");
            Date date = dateFormatReceived.parse(workout.getDate());
            String dateStrNeeded = dateFormatNeeded.format(date);
            String[] dateParsed = dateStrNeeded.split("-"); //parse data to char(-)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(dateFormatNeeded.parse(dateStrNeeded)));
            String[] days = new String[]{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
            String dayNameStr = days[calendar.get(Calendar.DAY_OF_WEEK) - 1];

            setWorkout(workout, dateParsed[0], dateParsed[1], dateParsed[2], dayNameStr);
        }
    }

    private void setWorkout(Workout workout, String year, String month, String day, String dayNameStr) {
        TextView day_number = Objects.requireNonNull(getActivity()).findViewById(R.id.dayNumberTodayWorkout);
        TextView monthWorkout = getActivity().findViewById(R.id.monthTodayWorkout);
        TextView dayWorkout = getActivity().findViewById(R.id.dayNameTodayWorkout);
        TextView yearWorkout = getActivity().findViewById(R.id.yearTodayWorkout);
        TextView distance = getActivity().findViewById(R.id.distanceTodayWorkout);
        TextView duration = getActivity().findViewById(R.id.durationTodayWorkout);
        TextView calories = getActivity().findViewById(R.id.caloriesTodayWorkout);
        TextView bpm = getActivity().findViewById(R.id.bpmTodayWorkout);

        day_number.setText(day);
        monthWorkout.setText(month);
        dayWorkout.setText(dayNameStr);
        yearWorkout.setText(year);
        distance.setText(String.valueOf(workout.getDistance()));
        duration.setText(String.valueOf(workout.getDuration()));
        calories.setText(String.valueOf(workout.getCalories_burned()));
        bpm.setText(String.valueOf(workout.getBpm()));
    }
}