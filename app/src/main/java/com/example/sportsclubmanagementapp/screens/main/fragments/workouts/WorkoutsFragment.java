package com.example.sportsclubmanagementapp.screens.main.fragments.workouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Workouts;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.WorkoutsAdapter;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

import java.util.ArrayList;
import java.util.List;


public class WorkoutsFragment extends Fragment {

    //for workouts recycler
    private List<Workouts> workoutsList = new ArrayList<>();
    private RecyclerView recyclerViewWorkouts;
    private WorkoutsAdapter WorkoutsAdapter;

    public static WorkoutsFragment newInstance() {
        return new WorkoutsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Workouts");
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.my_profile_toolbar, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //for workouts recycler
        recyclerViewWorkouts = (RecyclerView) view.findViewById(R.id.workouts_recycler_view);
        WorkoutsAdapter = new WorkoutsAdapter(workoutsList, getContext());
        RecyclerView.LayoutManager workoutsLayoutManager = new LinearLayoutManager(WorkoutsAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewWorkouts.setLayoutManager(workoutsLayoutManager);
        recyclerViewWorkouts.setAdapter(WorkoutsAdapter);

        prepareWorkoutsData();
    }

    private void prepareWorkoutsData() {
        workoutsList.add(new Workouts(1, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        workoutsList.add(new Workouts(2, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        workoutsList.add(new Workouts(3, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));

        WorkoutsAdapter.notifyDataSetChanged();
    }

}