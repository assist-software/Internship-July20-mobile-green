package com.example.sportsclubmanagementapp.screens.main.fragments.home;

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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Workouts;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.EventDetails.EventDetailsActivity;
import com.example.sportsclubmanagementapp.screens.club_page.ClubPageActivity;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements OnClubItemListener, OnEventItemListener {

    boolean userHasPendingOrJoinedClubs = false;

    //for events list recycler
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;
    //for first club recycler
    private RecyclerView recyclerViewFirstClubs;
    private ClubsAdapter firstClubsAdapter;
    //for clubs recycler
    private RecyclerView recyclerViewClubs;
    private ClubsAdapter clubsAdapter;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpAllRecyclerViews(view); //set up all recycler view and create adapters for each
        getApiPendingClubs();
        getApiJoinedClubs();
        checkIfJoinedOrPendingFirstClub(); //check if user is joined or pending in a club
        displayAvatar(); //display avatar as circle view

        //data for TESTS
        prepareEventData();
        prepareFutureEventsData();
        prepareWorkoutsData();
    }

    private void checkIfJoinedOrPendingFirstClub() {
        if (this.userHasPendingOrJoinedClubs) {
            setVisibilityFirstClub(false);
        } else {
            setVisibilityFirstClub(true);
        }
        getApiClubs();
    }

    private void setVisibilityFirstClub(boolean isVisible) {
        if (isVisible)
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_club).setVisibility(View.GONE);
        else
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_club).setVisibility(View.VISIBLE);
    }


    private void getApiPendingClubs() {
        Call<List<Club>> call = ApiHelper.getApi().getUnJoinedClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_LONG).show();
                else {
                    if (response.body() != null) userHasPendingOrJoinedClubs = true;
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getApiJoinedClubs() {
        Call<List<Club>> call = ApiHelper.getApi().getUnJoinedClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_LONG).show();
                else {
                    if (response.body() != null) userHasPendingOrJoinedClubs = true;
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getApiClubs() {
        Call<List<Club>> call = ApiHelper.getApi().getUnJoinedClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_LONG).show();
                else {
                    List<Club> clubs = response.body();
                    clubsAdapter = initAdapter(clubs, recyclerViewClubs, 1);
                    if (userHasPendingOrJoinedClubs) {
                        firstClubsAdapter = initAdapter(clubs, recyclerViewFirstClubs, 1);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpAllRecyclerViews(View view) {
        recyclerViewEvents = view.findViewById(R.id.events_recycler_view);
        setupUpEventsRecyclerView(); //hardcoded
        recyclerViewFirstClubs = view.findViewById(R.id.first_club_recycler_view);
        recyclerViewClubs = view.findViewById(R.id.join_clubs_recycler_view);
        recyclerViewFutureEvents = view.findViewById(R.id.future_events_recycler_view);
        setupUpFutureEventsRecyclerView(); //hardcoded
        recyclerViewWorkouts = view.findViewById(R.id.workouts_recycler_view);
        setupUpWorkoutsRecyclerView(); //hardcoded
    }

    private void setToolbar() {
        CircleImageView avatar_toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.avatar_toolbar);
        avatar_toolbar.setVisibility(View.GONE);
        avatar_toolbar.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_default_avatar));
        TextView fragment_title = getActivity().findViewById(R.id.fragment_title);
        fragment_title.setText(getResources().getText(R.string.home));

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("");

        //set left side drawer for toolbar
        DrawerLayout drawer = getActivity().findViewById(R.id.drawerLayout);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void displayAvatar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        Objects.requireNonNull(mainActivity).setAvatar(Utils.getAvatars(getContext()).get(new Random().nextInt(5)));
        Utils.setCircleAvatar(getActivity(), mainActivity.getAvatar(), Objects.requireNonNull(getActivity()).findViewById(R.id.avatar));
    }

    private void setupUpEventsRecyclerView() {
        eventAdapter = new EventAdapter(eventList, getContext(), EventAdapter.HORIZONTAL_BTN_EVENT, this);
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(eventLayoutManager);
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    private void setupUpFutureEventsRecyclerView() {
        futureEventsAdapter = new EventAdapter(futureEventsList, getContext(), EventAdapter.VERTICAL_BTN_EVENT, this);
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
    public void onClubsClick(Club club) {
        Intent intent = new Intent(getActivity(), ClubPageActivity.class);
        intent.putExtra("CLUB_EXTRA_SESSION_ID", club); //send the clicked club to the next activity (its page)
        startActivity(intent);
    }

    @Override
    public void onClubsJoinClick(Club club) {
        clubJoinApi(club);
    }

    private void clubJoinApi(Club club) {
        Call<Void> call = ApiHelper.getApi().createPostUserJoinClub(getToken(), club.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), getString(R.string.api_response_not_successful) + response.code(), Toast.LENGTH_SHORT).show();
                else {
                    if (!userHasPendingOrJoinedClubs) {
                        firstClubsAdapter.removeClub(club);
                    }
                    clubsAdapter.removeClub(club);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        getActivity();
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), "no token");
    }

    @Override
    public void onEventsClick(Event event) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra("eventObject", event);
        startActivity(intent);
    }

    @Override
    public void onEventsJoinClick(Event event) {

    }

    private void filterFutureEvents() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        String nowStr = sdformat.format(now);
        Iterator<Event> it = futureEventsList.iterator();

        while (it.hasNext()) {
            Event e = it.next();
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

    private ClubsAdapter initAdapter(List<Club> clubs, RecyclerView recyclerView, int layout) {
        ClubsAdapter adapter = new ClubsAdapter(clubs, getContext(), layout, this);
        RecyclerView.LayoutManager clubsLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(clubsLayoutManager);
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void prepareEventData() {
        eventList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventAdapter.notifyDataSetChanged();
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