package com.example.sportsclubmanagementapp.screens.calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Notification;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {

    private List<Notification> notification = new ArrayList<>();
    private TextView noEventsTextView;
    private CalendarView calendar;
    private String selectedDate;
    private boolean readyToGetEvents = true;
    private boolean readyToGetClubs = true;
    //for parent list recycler
    private List<Club> allClubList = new ArrayList<>();
    private List<Club> currentClubList = new ArrayList<>();
    private List<List<Event>> allEventList = new ArrayList<>();
    private List<List<Event>> currentEventList = new ArrayList<>();
    private RecyclerView recyclerViewParent;
    private EventParentAdapter eventParentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setToolbar();
        setUpNotifications();
    }

    @Override
    protected void onStart() {
        super.onStart();
        calendar = findViewById(R.id.calendar);
        noEventsTextView = findViewById(R.id.noEventsTextView);
        setUpEventsRecyclerView();
        //initialize the current date
        selectedDate = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(new Date(calendar.getDate()));
        setOnClickListenerCalendar(); //set up calendar buttons
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApiClubs();
        getApiEvents();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.calendarToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setUpNotifications() {
        //for TESTS
        notification.add(new Notification("2 min ago", "Coach", "John Down", "invited you in", "Running Club"));

        ImageView notificationIcon = findViewById(R.id.notificationImageView);
        if (notification.isEmpty()) notificationIcon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_notifications_toolbar, null));
        else notificationIcon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_notifications_toolbar_news, null));
    }

    private void setOnClickListenerCalendar() {
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-";
            if(month < 10) selectedDate = selectedDate + "0" + (month + 1) + "-" + dayOfMonth; //(m+1) fix the problem with the calendar (1 month behind)
            //select the events for the selected date
            getApiEvents();
            findEventsForSelectedDate();
        });
    }

    private void getApiClubs() {
        if(!readyToGetClubs) return;
        readyToGetClubs = false;
        Call<List<Club>> call = ApiHelper.getApi().getClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful()) {
                    noEventsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(getBaseContext(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                }
                else {
                    assert response.body() != null;
                    allClubList = new ArrayList<>(response.body());
                    if(allClubList.isEmpty()) noEventsTextView.setVisibility(View.VISIBLE);
                    else noEventsTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                noEventsTextView.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        readyToGetClubs = true;
    }

    private void getApiEvents() {
        if(!readyToGetEvents) return;
        readyToGetEvents = false;
        Call<List<Event>> call = ApiHelper.getApi().getEvents(getToken());
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NotNull Call<List<Event>> call, @NotNull Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    noEventsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(getBaseContext(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                }
                else {
                    assert response.body() != null;
                    List<Event> events = new ArrayList<>(response.body());
                    if(events.isEmpty()) noEventsTextView.setVisibility(View.VISIBLE);
                    else noEventsTextView.setVisibility(View.GONE);
                    prepareEventListForEveryClub(events);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Event>> call, @NotNull Throwable t) {
                noEventsTextView.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        readyToGetEvents = true;
    }

    private String getToken() {
        SharedPreferences prefs = Objects.requireNonNull(getBaseContext()).getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), getString(R.string.no_token_prefs));
    }

    private void findEventsForSelectedDate() {
        currentClubList.clear();
        currentEventList.clear();
        List<Event> eventsTmp; //store the events for each club

        boolean atLeastOneEvent = false; //find at least one event for selected date for a club (if not, the club is hidden)
        for (int i = 0; i < allEventList.size(); i++) {
            eventsTmp = new ArrayList<>();
            for (int j = 0; j < allEventList.get(i).size(); j++) {
                if (allEventList.get(i).get(j).getDate().equals(selectedDate)) { //the event date is the same with the selected one
                    eventsTmp.add(allEventList.get(i).get(j));
                    atLeastOneEvent = true;
                }
            }
            if (!eventsTmp.isEmpty()) { //add the clubs with at least one event in the selected date
                currentClubList.add(allClubList.get(new Random().nextInt(allClubList.size())));
                currentEventList.add(eventsTmp);
            }
        }
        //hide the recycler view if there are no events in the selected date
        if (atLeastOneEvent) {
            noEventsTextView.setVisibility(View.GONE);
            recyclerViewParent.setVisibility(View.VISIBLE);
        }
        else {
            noEventsTextView.setVisibility(View.VISIBLE);
            recyclerViewParent.setVisibility(View.GONE);
        }
        eventParentAdapter.notifyDataSetChanged(); //show events for selected date
    }

    private void setUpEventsRecyclerView() {
        //for events recycler
        recyclerViewParent = findViewById(R.id.club_events_parent_recycler_view);
        eventParentAdapter = new EventParentAdapter(currentClubList, currentEventList, this);
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventParentAdapter.getActivity());
        recyclerViewParent.setLayoutManager(eventLayoutManager);
        recyclerViewParent.setAdapter(eventParentAdapter);
    }

    public void goToNotificationsScreen(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void prepareEventListForEveryClub(List<Event> events) {
        if(!allClubList.isEmpty()) {
            allEventList.clear();
            for (int i = 0; i < allClubList.size(); i++)
                allEventList.add(i, new ArrayList<>()); //for every club is created a list
            //set events to random clubs for TESTS
            for (int i = 0; i < events.size(); i++) {
                allEventList.get(new Random().nextInt(allClubList.size())).add(events.get(i));
            }
            findEventsForSelectedDate();
        }
    }
}