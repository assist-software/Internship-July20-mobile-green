package com.example.sportsclubmanagementapp.screens.main.fragments.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Workouts;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.EventAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.WorkoutsAdapter;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;
import com.example.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
        setTodayWorkout();
    }

    private void initRecycle(View view) {
        recyclerViewWorkouts = view.findViewById(R.id.workouts_recycler_view);
    }

    private void getApiWorkouts() {
        Call<List<Workouts>> call = ApiHelper.getApi().getWorkouts(getToken());
        call.enqueue(new Callback<List<Workouts>>() {
            @Override
            public void onResponse(@NotNull Call<List<Workouts>> call, @NotNull Response<List<Workouts>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                } else {
                    initWorkoutsAdapter(response.body(), recyclerViewWorkouts);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Workouts>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), "no token");
    }

    private void initWorkoutsAdapter(List<Workouts> workouts, RecyclerView recyclerView) {
        WorkoutsAdapter adapter = new WorkoutsAdapter(workouts, getContext());
        RecyclerView.LayoutManager workoutsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(workoutsLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("DefaultLocale")
    private void setTodayWorkout() {
        Date currentDate = Calendar.getInstance().getTime(); //get current date
        SimpleDateFormat dateFormated = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()); //format current date
        String[] dateParsed = dateFormated.format(currentDate).split("-"); //parse data to char(-)

        //get day name
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        String[] days = new String[]{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        String dayNameStr = days[calendar.get(Calendar.DAY_OF_WEEK) - 1];

        TextView day_number = Objects.requireNonNull(getActivity()).findViewById(R.id.dayNumberTodayWorkout);
        day_number.setText(dateParsed[0]);
        TextView month = getActivity().findViewById(R.id.monthTodayWorkout);
        month.setText(dateParsed[1]);
        TextView day_name = getActivity().findViewById(R.id.dayNameTodayWorkout);
        day_name.setText(dayNameStr);
        TextView year = getActivity().findViewById(R.id.yearTodayWorkout);
        year.setText(dateParsed[2]);
        TextView distance = getActivity().findViewById(R.id.distanceTodayWorkout);
        distance.setText("20.00");
        TextView duration = getActivity().findViewById(R.id.durationTodayWorkout);
        duration.setText("20.00");
        TextView calories = getActivity().findViewById(R.id.caloriesTodayWorkout);
        calories.setText("20.00");
        TextView bpm = getActivity().findViewById(R.id.bpmTodayWorkout);
        bpm.setText(String.valueOf(120));
    }
}