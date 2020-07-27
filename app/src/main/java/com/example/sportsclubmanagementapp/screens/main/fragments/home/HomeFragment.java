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
import com.example.sportsclubmanagementapp.data.models.UserAccountSetup;
import com.example.sportsclubmanagementapp.data.models.Workout;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.club_page.ClubPageActivity;
import com.example.sportsclubmanagementapp.screens.eventdetails.EventDetailsActivity;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements OnClubItemListener, OnEventItemListener {

    private boolean userHasPendingOrJoinedClubs = false;
    private boolean userHasPendingOrJoinedEvents = false;
    //recyclers
    private RecyclerView recyclerViewFirstClubs;
    private RecyclerView recyclerViewClubs;
    private RecyclerView recyclerViewFirstEvents;
    private RecyclerView recyclerViewFutureEvents;
    private RecyclerView recyclerViewWorkouts;
    //adapters
    private ClubsAdapter firstClubsAdapter;
    private ClubsAdapter clubsAdapter;
    private EventAdapter firstEventsAdapter;
    private EventAdapter futureEventsAdapter;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void setToolbar() {
        disableToolbarAvatar();
        setToolbarTitle();
        setBurgerMenuForDrawer();
    }

    private void disableToolbarAvatar() {
        CircleImageView avatar_toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.avatar_toolbar);
        avatar_toolbar.setVisibility(View.GONE);
        avatar_toolbar.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_default_avatar));
    }

    private void setToolbarTitle() {
        TextView fragment_title = Objects.requireNonNull(getActivity()).findViewById(R.id.fragment_title);
        fragment_title.setText(getResources().getText(R.string.home));
    }

    private void setBurgerMenuForDrawer() {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        displayAvatar(); //display avatar as circle view
        setUpAllRecyclerViews(view); //set up all recycler view and create adapters for each
        getApiPendingClubs();
        getApiEvents();
        getApiWorkouts();
        getApiUserName();
    }

    private void displayAvatar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        Objects.requireNonNull(mainActivity).setAvatar(Utils.getAvatars(getContext()).get(new Random().nextInt(5)));
        Utils.setCircleAvatar(getActivity(), mainActivity.getAvatar(), Objects.requireNonNull(getActivity()).findViewById(R.id.avatar));
    }

    private void setUpAllRecyclerViews(View view) {
        this.recyclerViewFirstClubs = view.findViewById(R.id.first_club_recycler_view);
        this.recyclerViewFirstEvents = view.findViewById(R.id.events_recycler_view);
        this.recyclerViewClubs = view.findViewById(R.id.join_clubs_recycler_view);
        this.recyclerViewFutureEvents = view.findViewById(R.id.future_events_recycler_view);
        this.recyclerViewWorkouts = view.findViewById(R.id.workouts_recycler_view);

    }

    private void getApiPendingClubs() {
        Call<List<Club>> call = ApiHelper.getApi().getPendingClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                else {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        userHasPendingOrJoinedClubs = true;
                    }
                    getApiJoinedClubs();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getApiJoinedClubs() {
        Call<List<Club>> call = ApiHelper.getApi().getJoinedClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                else {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        userHasPendingOrJoinedClubs = true;
                    }
                    checkIfJoinedOrPendingFirstClub();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkIfJoinedOrPendingFirstClub() {
        if (this.userHasPendingOrJoinedClubs) {
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_club).setVisibility(View.GONE);
        } else {
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_club).setVisibility(View.VISIBLE);
        }
        getApiClubs();
    }

    private void getApiClubs() {
        Call<List<Club>> call = ApiHelper.getApi().getUnJoinedClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                else {
                    List<Club> clubs = response.body();
                    clubsAdapter = initClubsAdapter(clubs, recyclerViewClubs);
                    if (!userHasPendingOrJoinedClubs) {
                        firstClubsAdapter = initClubsAdapter(clubs, recyclerViewFirstClubs);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getToken() {
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), getString(R.string.no_token_prefs));
    }

    private ClubsAdapter initClubsAdapter(List<Club> clubs, RecyclerView recyclerView) {
        ClubsAdapter adapter = new ClubsAdapter(clubs, getContext(), 1, this);
        RecyclerView.LayoutManager clubsLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(clubsLayoutManager);
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    @Override
    public void onClubsClick(Club club) {
        Intent intent = new Intent(getActivity(), ClubPageActivity.class);
        intent.putExtra("CLUB_EXTRA_SESSION_ID", club); //send the clicked club to the next activity (its page)
        startActivity(intent);
    }

    @Override
    public void onClubsJoinClick(Club club) {  //process clubs locally, and api post call to join club
        clubJoinApi(club);
        Toast.makeText(getContext(), "Club: " + club.getName() + " " + getString(R.string.club_joined_successfully), Toast.LENGTH_LONG).show();
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

    private void getApiEvents() {
        Call<List<Event>> call = ApiHelper.getApi().getEvents(getToken());
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NotNull Call<List<Event>> call, @NotNull Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), getString(R.string.api_response_not_successful) + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    assert response.body() != null;
                    List<Event> events = new ArrayList<>(response.body());
                    userHasPendingOrJoinedEvents = checkIfJoinedOrPendingFirstEvent(events);
                    getUnJoinedEvents(events);
                    filterFutureEvents(events);
                    if (!userHasPendingOrJoinedEvents) {
                        firstEventsAdapter = initEventsAdapter(events, recyclerViewFirstEvents, 1);
                    }
                    futureEventsAdapter = initEventsAdapter(events, recyclerViewFutureEvents, 3);

                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Event>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUnJoinedEvents(List<Event> events) {
        ListIterator<Event> it = events.listIterator();
        while (it.hasNext()) {
            if (it.next().getStatus() != null) it.remove();
        }
    }

    private boolean checkIfJoinedOrPendingFirstEvent(List<Event> events) {
        for (Event event : events) {
            if (event.getStatus() != null) {
                hideVisibilityFirstEvent(true);
                return true;
            }
        }
        hideVisibilityFirstEvent(false);
        return false;
    }

    private void hideVisibilityFirstEvent(boolean isNotVisible) {
        if (isNotVisible) {
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_event).setVisibility(View.GONE);
        } else {
            Objects.requireNonNull(getActivity()).findViewById(R.id.join_first_event).setVisibility(View.VISIBLE);
        }
    }


    private EventAdapter initEventsAdapter(List<Event> events, RecyclerView recyclerView, int layout) {
        EventAdapter adapter = new EventAdapter(events, getContext(), layout, this);
        RecyclerView.LayoutManager eventsLayoutManager;
        if (layout == 1) {
            eventsLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            eventsLayoutManager = new LinearLayoutManager(this.getContext());
        }
        recyclerView.setLayoutManager(eventsLayoutManager);
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void filterFutureEvents(List<Event> events) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String nowStr = sdformat.format(now);
        Iterator<Event> it = events.iterator();

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

    @Override
    public void onEventsClick(Event event) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra(getString(R.string.event_id), event.getId());
        startActivity(intent);
    }

    @Override
    public void onEventsJoinClick(Event event) {
        eventJoinApi(event);
        Toast.makeText(getContext(), "Event: " + event.getName() + " " + getString(R.string.event_joined_successfully), Toast.LENGTH_LONG).show();
    }

    private void eventJoinApi(Event event) {
        Call<Void> call = ApiHelper.getApi().createPostUserJoinEvent(getToken(), event.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), getString(R.string.api_response_not_successful) + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    if (!userHasPendingOrJoinedEvents) firstEventsAdapter.removeEvent(event);
                    futureEventsAdapter.removeEvent(event);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Workout>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWorkoutsAdapter(List<Workout> workouts, RecyclerView recyclerView) {
        WorkoutsAdapter adapter = new WorkoutsAdapter(workouts, getContext());
        RecyclerView.LayoutManager workoutsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(workoutsLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getApiUserName() {
        Call<UserAccountSetup> call = ApiHelper.getApi().getUserData(getToken());
        call.enqueue(new Callback<UserAccountSetup>() {
            @Override
            public void onResponse(@NotNull Call<UserAccountSetup> call, @NotNull Response<UserAccountSetup> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                } else {
                    assert response.body() != null;
                    setUserName(response.body().getLast_name(), response.body().getFirst_name());
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserAccountSetup> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setUserName(String lastName, String firstName) {
        TextView userNameTextView = Objects.requireNonNull(getActivity()).findViewById(R.id.username);
        userNameTextView.setText(firstName + " " + lastName);
    }

}