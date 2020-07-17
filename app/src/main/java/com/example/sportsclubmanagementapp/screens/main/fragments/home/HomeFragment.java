package com.example.sportsclubmanagementapp.screens.main.fragments.home;
import android.os.Bundle;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.FutureEvents;
import com.example.sportsclubmanagementapp.data.models.Workouts;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    //for events list recycler
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;

    //for first club recycler
    private List<Clubs> firstClubList = new ArrayList<>();
    private RecyclerView recyclerViewFirstClub;
    private ClubsAdapter firstClubAdapter;

    //for clubs recycler
    private List<Clubs> clubsList = new ArrayList<>();
    private RecyclerView recyclerViewClubs;
    private ClubsAdapter ClubsAdapter;

    //for future events recycler
    private List<FutureEvents> futureEventsList = new ArrayList<>();
    private RecyclerView recyclerViewFutureEvents;
    private FutureEventsAdapter futureEventsAdapter;

    //for workouts recycler
    private List<Workouts> workoutsList = new ArrayList<>();
    private RecyclerView recyclerViewWorkouts;
    private WorkoutsAdapter WorkoutsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static HomeFragment newInstance() {
        return  new HomeFragment();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        DrawerLayout drawer = getActivity().findViewById(R.id.drawerLayout);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((AppCompatActivity)getActivity(),drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        displayAvatar();

        //for events recycler
        recyclerViewEvents = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        eventAdapter = new EventAdapter(eventList, getContext());
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(eventLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvents.setAdapter(eventAdapter);

        //for first club recycler
        recyclerViewFirstClub = (RecyclerView) view.findViewById(R.id.first_club_recycler_view);
        firstClubAdapter = new ClubsAdapter(firstClubList, getContext(), R.layout.item_club_join);
        RecyclerView.LayoutManager firstClubLayoutManager = new LinearLayoutManager(firstClubAdapter.getContext());
        recyclerViewFirstClub.setLayoutManager(firstClubLayoutManager);
        recyclerViewFirstClub.setAdapter(firstClubAdapter);

        //for clubs recycler
        recyclerViewClubs = (RecyclerView) view.findViewById(R.id.join_clubs_recycler_view);
        ClubsAdapter = new ClubsAdapter(clubsList, getContext(), R.layout.item_club_join);
        RecyclerView.LayoutManager ClubsLayoutManager = new LinearLayoutManager(ClubsAdapter.getContext());
        recyclerViewClubs.setLayoutManager(ClubsLayoutManager);
        recyclerViewClubs.setAdapter(ClubsAdapter);

        //for future events recycler
        recyclerViewFutureEvents = (RecyclerView) view.findViewById(R.id.future_events_recycler_view);
        futureEventsAdapter = new FutureEventsAdapter(futureEventsList, getContext());
        RecyclerView.LayoutManager futureEventsLayoutManager = new LinearLayoutManager(futureEventsAdapter.getContext());
        recyclerViewFutureEvents.setLayoutManager(futureEventsLayoutManager);
        recyclerViewFutureEvents.setAdapter(futureEventsAdapter);

        //for workouts recycler
        recyclerViewWorkouts = (RecyclerView) view.findViewById(R.id.workouts_recycler_view);
        WorkoutsAdapter = new WorkoutsAdapter(workoutsList, getContext());
        RecyclerView.LayoutManager workoutsLayoutManager = new LinearLayoutManager(WorkoutsAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewWorkouts.setLayoutManager(workoutsLayoutManager);
        recyclerViewWorkouts.setAdapter(WorkoutsAdapter);

        prepareEventData();
        prepareFirstClubsData();
        prepareClubsData();
        prepareFutureEventsData();
        prepareWorkoutsData();
    }

    private void displayAvatar() {
        ImageView avatar_icon = (ImageView) getView().findViewById(R.id.avatar);
        //Glide.with(this).load(R.drawable.ic_default_avatar).circleCrop().into(avatar_icon);
        Glide.with(this).load(R.drawable.ic_avatar).apply(RequestOptions.circleCropTransform()).into(avatar_icon);
    }

    private void prepareEventData() {
        eventList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        eventAdapter.notifyDataSetChanged();
    }

    private void prepareClubsData() {
        firstClubList.add(new Clubs(1, 1, "Running", "Description", 1, 2, 3));
        firstClubList.add(new Clubs(2, 1, "Tennis", "Description", 1, 2, 3));

        firstClubAdapter.notifyDataSetChanged();
    }

    private void prepareFirstClubsData() {
        clubsList.add(new Clubs(1, 1, "Biking", "Description", 1, 2, 3));
        clubsList.add(new Clubs(2, 1, "Running", "Description", 1, 2, 3));
        clubsList.add(new Clubs(3, 1, "Tennis", "Description", 1, 2, 3));

        ClubsAdapter.notifyDataSetChanged();
    }

    private void prepareFutureEventsData() {
        futureEventsList.add(new FutureEvents(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        futureEventsList.add(new FutureEvents(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        futureEventsList.add(new FutureEvents(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        futureEventsAdapter.notifyDataSetChanged();
    }

    private void prepareWorkoutsData() {
        workoutsList.add(new Workouts(1, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true ));
        workoutsList.add(new Workouts(2, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true ));
        workoutsList.add(new Workouts(3, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true ));

        WorkoutsAdapter.notifyDataSetChanged();
    }
}