package com.example.sportsclubmanagementapp.screens.calendar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Notification;
import com.example.sportsclubmanagementapp.screens.club_page.ClubPageActivity;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    List<Notification> notification = new ArrayList<>();

    CalendarView calendar;
    String selectedDate;

    //for parent list recycler
    private List<Clubs> allClubsList = new ArrayList<>();
    private List<Clubs> currentClubsList = new ArrayList<>();
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
        //initialize the current date
        selectedDate = new SimpleDateFormat("dd.M.yyyy", Locale.getDefault()).format(new Date(calendar.getDate()));
        //set up calendar buttons
        setOnClickListenerCalendar();

        setUpEventsRecyclerView();
        prepareEventData();
        //show events for the current day without performing any click on calendar
        findEventsForSelectedDate();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.calendarToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpNotifications() {
        notification.add(new Notification("2 min ago", "Coach", "John Down", "invited you in", "Running Club"));
        ImageView notificationIcon = findViewById(R.id.notificationImageView);
        if( notification.isEmpty() ) notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar, null));
        else notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar_news, null));
    }

    private void setOnClickListenerCalendar() {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "." + (month + 1) + "." + year;
                //select the events for the selected date
                findEventsForSelectedDate();
            }
        });
    }

    private void findEventsForSelectedDate(){
        currentClubsList.clear();
        currentEventList.clear();
        List<Event> eventsTmp;
        boolean atLeastOneEvent = false;
        for (int i = 0; i < allClubsList.size(); i++) {
            eventsTmp = new ArrayList<>();
            for (int j = 0; j < allEventList.get(i).size(); j++) {
                if (allEventList.get(i).get(j).getDate().equals(selectedDate)) {
                    eventsTmp.add(allEventList.get(i).get(j));
                    atLeastOneEvent = true;
                }
            }
            if(!eventsTmp.isEmpty()) {
                currentClubsList.add(allClubsList.get(i));
                currentEventList.add(eventsTmp);
            }
        }
        if( atLeastOneEvent)
            recyclerViewParent.setVisibility(View.VISIBLE);
        else
            recyclerViewParent.setVisibility(View.GONE);
        eventParentAdapter.notifyDataSetChanged(); //show events for selected date
    }

    private void setUpEventsRecyclerView() {
        //for events recycler
        recyclerViewParent = (RecyclerView) findViewById(R.id.club_events_parent_recycler_view);
        eventParentAdapter = new EventParentAdapter(currentClubsList, currentEventList, this);
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventParentAdapter.getActivity());
        recyclerViewParent.setLayoutManager(eventLayoutManager);
        recyclerViewParent.setAdapter(eventParentAdapter);
    }

    public void goToNotificationsScreen(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void prepareEventData() {
        allClubsList.add(new Clubs(1, 1, "Running", "Description", 1, 2, 3));
        allClubsList.add(new Clubs(2, 1, "Football", "Description", 1, 2, 3));
        allClubsList.add(new Clubs(3, 1, "Biking", "Description", 1, 2, 3));

        List<Event> events = new ArrayList<>();
        events.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(2, 2, "Running for Life", "Description", "Suceava", "21.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "21.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        eventParentAdapter.notifyDataSetChanged();
    }
}