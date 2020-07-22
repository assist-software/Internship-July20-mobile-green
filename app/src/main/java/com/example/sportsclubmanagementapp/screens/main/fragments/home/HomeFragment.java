package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportsclubmanagementapp.EventDetails.EventDetailsActivity;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Workouts;
import com.example.sportsclubmanagementapp.screens.club_page.ClubPageActivity;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements OnClubItemListener, OnEventItemListener {


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
    private List<Event> futureEventsList = new ArrayList<>();
    private RecyclerView recyclerViewFutureEvents;
    private EventAdapter futureEventsAdapter;

    //for workouts recycler
    private List<Workouts> workoutsList = new ArrayList<>();
    private RecyclerView recyclerViewWorkouts;
    private WorkoutsAdapter WorkoutsAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        displayAvatar();

        //for events recycler
        setupUpEventsRecyclerView(view);
        //for first club recycler
        setupUpFirstClubRecyclerView(view);
        //for clubs recycler
        setupUpClubsRecyclerView(view);
        //for future events recycler
        setupUpFutureEventsRecyclerView(view);
        //for workouts recycler
        setupUpWorkoutsRecyclerView(view);

        prepareEventData();
        prepareFirstClubsData();
        prepareClubsData();
        prepareFutureEventsData();
        prepareWorkoutsData();
    }

    private void setToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        DrawerLayout drawer = getActivity().findViewById(R.id.drawerLayout);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void displayAvatar() {
        Glide.with(this).load(R.mipmap.ic_default_avatar).centerCrop().into((CircleImageView) Objects.requireNonNull(getView()).findViewById(R.id.avatar));
    }

    private void setupUpEventsRecyclerView(View view) {
        recyclerViewEvents = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        eventAdapter = new EventAdapter(eventList, getContext(), 1,this);
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(eventLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    private void setupUpFirstClubRecyclerView(View view) {
        recyclerViewFirstClub = (RecyclerView) view.findViewById(R.id.first_club_recycler_view);
        firstClubAdapter = new ClubsAdapter(firstClubList, getContext(), 1, this);
        RecyclerView.LayoutManager firstClubLayoutManager = new LinearLayoutManager(firstClubAdapter.getContext());
        recyclerViewFirstClub.setLayoutManager(firstClubLayoutManager);
        recyclerViewFirstClub.setAdapter(firstClubAdapter);
    }

    private void setupUpClubsRecyclerView(View view) {
        recyclerViewClubs = (RecyclerView) view.findViewById(R.id.join_clubs_recycler_view);
        ClubsAdapter = new ClubsAdapter(clubsList, getContext(), 1, this);
        RecyclerView.LayoutManager ClubsLayoutManager = new LinearLayoutManager(ClubsAdapter.getContext());
        recyclerViewClubs.setLayoutManager(ClubsLayoutManager);
        recyclerViewClubs.setAdapter(ClubsAdapter);
    }

    private void setupUpFutureEventsRecyclerView(View view) {
        recyclerViewFutureEvents = (RecyclerView) view.findViewById(R.id.future_events_recycler_view);
        futureEventsAdapter = new EventAdapter(futureEventsList, getContext(), 3,this);
        RecyclerView.LayoutManager futureEventsLayoutManager = new LinearLayoutManager(futureEventsAdapter.getContext());
        recyclerViewFutureEvents.setLayoutManager(futureEventsLayoutManager);
        recyclerViewFutureEvents.setAdapter(futureEventsAdapter);
    }

    private void setupUpWorkoutsRecyclerView(View view) {
        recyclerViewWorkouts = (RecyclerView) view.findViewById(R.id.workouts_recycler_view);
        WorkoutsAdapter = new WorkoutsAdapter(workoutsList, getContext());
        RecyclerView.LayoutManager workoutsLayoutManager = new LinearLayoutManager(WorkoutsAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewWorkouts.setLayoutManager(workoutsLayoutManager);
        recyclerViewWorkouts.setAdapter(WorkoutsAdapter);
    }

    /*public void goToClubPage() {
        startActivity(new Intent(getActivity(), ClubPageActivity.class));
    }*/

    @Override
    public void onClubsClick(Clubs club) {
        Intent intent = new Intent(getActivity(), ClubPageActivity.class);
        intent.putExtra("CLUB_EXTRA_SESSION_ID", club);
        startActivity(intent);
    }

    @Override
    public void onClubsJoinClick(Clubs club) {
        //remove the joined club from the list
        firstClubList.remove(club);
        clubsList.remove(club);

        //hide recycler header if the list is empty
        if( firstClubList.isEmpty() ) Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_club).setVisibility(View.GONE);
        else Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_club).setVisibility(View.VISIBLE);
        if( clubsList.isEmpty() ) Objects.requireNonNull(getActivity()).findViewById(R.id.join_clubs).setVisibility(View.GONE);
        else Objects.requireNonNull(getActivity()).findViewById(R.id.join_clubs).setVisibility(View.VISIBLE);

        //notify adapters to delete the club from recyclers
        firstClubAdapter.notifyDataSetChanged();
        ClubsAdapter.notifyDataSetChanged();

        //show message to user with the joined club
        Toast.makeText(getActivity(), "Joined to " + club.getName(), Toast.LENGTH_SHORT).show();
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
        futureEventsList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        futureEventsList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        futureEventsList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        futureEventsAdapter.notifyDataSetChanged();
    }

    private void prepareWorkoutsData() {
        workoutsList.add(new Workouts(1, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        workoutsList.add(new Workouts(2, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        workoutsList.add(new Workouts(3, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));

        WorkoutsAdapter.notifyDataSetChanged();
    }
    public void goToClubPage() {
        startActivity(new Intent(getActivity(), ClubPageActivity.class));
    }

    @Override
    public void onEventsClick(Event event) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra("eventObject",event);
        startActivity(intent);
    }
}