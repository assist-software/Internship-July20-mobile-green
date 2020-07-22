package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.sportsclubmanagementapp.screens.EventDetails.EventDetailsActivity;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Workouts;
import com.example.sportsclubmanagementapp.screens.club_page.ClubPageActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        displayAvatar(); //display avatar as circle view

        setUpAllRecyclerViews(view); //set up all recycler view and create adapters for each

        //data for TESTS
        prepareEventData();
        prepareFirstClubsData();
        prepareClubsData();
        prepareFutureEventsData();
        prepareWorkoutsData();
    }

    private void setUpAllRecyclerViews(View view) {
        //for events recycler
        recyclerViewEvents = view.findViewById(R.id.events_recycler_view);
        setupUpEventsRecyclerView();

        //for first club recycler
        recyclerViewFirstClub = (RecyclerView) view.findViewById(R.id.first_club_recycler_view);
        setupUpFirstClubRecyclerView();

        //for clubs recycler
        recyclerViewClubs = (RecyclerView) view.findViewById(R.id.join_clubs_recycler_view);
        setupUpClubsRecyclerView();

        //for future events recycler
        recyclerViewFutureEvents = (RecyclerView) view.findViewById(R.id.future_events_recycler_view);
        setupUpFutureEventsRecyclerView();

        //for workouts recycler
        recyclerViewWorkouts = (RecyclerView) view.findViewById(R.id.workouts_recycler_view);
        setupUpWorkoutsRecyclerView();
    }

    private void setToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.home)); //set toolbar name from strings

        //set left side drawer for toolbar
        DrawerLayout drawer = getActivity().findViewById(R.id.drawerLayout);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void displayAvatar() {
        Glide.with(this).load(R.mipmap.ic_default_avatar).centerCrop().into((CircleImageView) Objects.requireNonNull(getView()).findViewById(R.id.avatar));
    }

    private void setupUpEventsRecyclerView() {
        eventAdapter = new EventAdapter(eventList, getContext(), EventAdapter.HORIZONTAL_BTN_EVENT, this);
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(eventLayoutManager);
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    private void setupUpFirstClubRecyclerView() {
        firstClubAdapter = new ClubsAdapter(firstClubList, getContext(), ClubsAdapter.JOIN_CLUB_LAYOUT, this);
        RecyclerView.LayoutManager firstClubLayoutManager = new LinearLayoutManager(firstClubAdapter.getContext());
        recyclerViewFirstClub.setLayoutManager(firstClubLayoutManager);
        recyclerViewFirstClub.setAdapter(firstClubAdapter);
    }

    private void setupUpClubsRecyclerView() {
        ClubsAdapter = new ClubsAdapter(clubsList, getContext(), ClubsAdapter.JOIN_CLUB_LAYOUT, this);
        RecyclerView.LayoutManager ClubsLayoutManager = new LinearLayoutManager(ClubsAdapter.getContext());
        recyclerViewClubs.setLayoutManager(ClubsLayoutManager);
        recyclerViewClubs.setAdapter(ClubsAdapter);
    }

    private void setupUpFutureEventsRecyclerView() {
        futureEventsAdapter = new EventAdapter(futureEventsList, getContext(), EventAdapter.HORIZONTAL_BTN_EVENT, this);
        RecyclerView.LayoutManager futureEventsLayoutManager = new LinearLayoutManager(futureEventsAdapter.getContext());
        recyclerViewFutureEvents.setLayoutManager(futureEventsLayoutManager);
        recyclerViewFutureEvents.setAdapter(futureEventsAdapter);
    }

    private void setupUpWorkoutsRecyclerView() {
        WorkoutsAdapter = new WorkoutsAdapter(workoutsList, getContext());
        RecyclerView.LayoutManager workoutsLayoutManager = new LinearLayoutManager(WorkoutsAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewWorkouts.setLayoutManager(workoutsLayoutManager);
        recyclerViewWorkouts.setAdapter(WorkoutsAdapter);
    }

    @Override
    public void onClubsClick(Clubs club) {
        Intent intent = new Intent(getActivity(), ClubPageActivity.class);
        intent.putExtra("CLUB_EXTRA_SESSION_ID", club); //send the clicked club to the next activity (its page)
        startActivity(intent);
    }

    @Override
    public void onClubsJoinClick(Clubs club) {
        //remove the joined club from the list
        firstClubList.remove(club);
        clubsList.remove(club);

        //hide recycler header if the list is empty
        if (firstClubList.isEmpty())
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_club).setVisibility(View.GONE);
        else
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_club).setVisibility(View.VISIBLE);
        if (clubsList.isEmpty())
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_clubs).setVisibility(View.GONE);
        else
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_clubs).setVisibility(View.VISIBLE);

        //notify adapters to delete the club from recyclers
        firstClubAdapter.notifyDataSetChanged();
        ClubsAdapter.notifyDataSetChanged();

        //show message to user with the joined club
        Toast.makeText(getActivity(), "Joined to " + club.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventsClick(Event event) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra("eventObject", event);
        startActivity(intent);
    }

    private void filterFutureEvents() {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        String nowStr = sdformat.format(now);
        Iterator it = futureEventsList.iterator();

        while (it.hasNext()) {
            Event e = (Event) it.next();
            String date = e.getDate();
            try {
                Date d1 = sdformat.parse(date);
                Date d2 = sdformat.parse(nowStr);
                assert d1 != null;
                if (d1.compareTo(d2) < 0) {
                    it.remove();
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private void prepareEventData() {
        eventList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));

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
        futureEventsList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "23-07-2020", "10", "Running", 2, 3, 1));
        futureEventsList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "24-07-2020", "10", "Running", 2, 3, 1));
        futureEventsList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "25-07-2020", "10", "Running", 2, 3, 1));
        filterFutureEvents();
        futureEventsAdapter.notifyDataSetChanged();
    }

    private void prepareWorkoutsData() {
        workoutsList.add(new Workouts(1, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        workoutsList.add(new Workouts(2, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));
        workoutsList.add(new Workouts(3, 1, "Running", "Description", "Running", "Suceava", 10f, 2, 2.2f, 1.5f, 2.2f, 2.2f, true));

        WorkoutsAdapter.notifyDataSetChanged();
    }
}