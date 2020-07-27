package com.example.sportsclubmanagementapp.screens.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Notification;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    List<Notification> notification = new ArrayList<>();
    CalendarView calendar;
    String selectedDate;
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
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setUpNotifications() {
        //for TESTS
        notification.add(new Notification("2 min ago", "Coach", "John Down", "invited you in", "Running Club"));

        ImageView notificationIcon = findViewById(R.id.notificationImageView);
        if( notification.isEmpty() ) notificationIcon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_notifications_toolbar, null));
        else notificationIcon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_notifications_toolbar_news, null));
    }

    private void setOnClickListenerCalendar() {
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "." + (month + 1) + "." + year; //(m+1) fix the problem with the calendar (1 month behind)
            //select the events for the selected date
            findEventsForSelectedDate();
        });
    }

    private void findEventsForSelectedDate(){
        currentClubList.clear();
        currentEventList.clear();
        List<Event> eventsTmp; //store the events for each club

        boolean atLeastOneEvent = false; //find at least one event for selected date for a club (if not, the club is hidden)
        for (int i = 0; i < allClubList.size(); i++) {
            eventsTmp = new ArrayList<>(); //reset the list for the next club
            for (int j = 0; j < allEventList.get(i).size(); j++) {
                if (allEventList.get(i).get(j).getDate().equals(selectedDate)) { //the event date is the same with the selected one
                    eventsTmp.add(allEventList.get(i).get(j));
                    atLeastOneEvent = true;
                }
            }
            if(!eventsTmp.isEmpty()) { //add the clubs with at least one event in the selected date
                currentClubList.add(allClubList.get(i));
                currentEventList.add(eventsTmp);
            }
        }
        //hide the recycler view if there are no events in the selected date
        if(atLeastOneEvent)
            recyclerViewParent.setVisibility(View.VISIBLE);
        else
            recyclerViewParent.setVisibility(View.GONE);
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

    public void prepareEventData() {
        allClubList.add(new Club(1, 1, "Running", "Description", 1, 2, 3));
        allClubList.add(new Club(2, 1, "Football", "Description", 1, 2, 3));
        allClubList.add(new Club(3, 1, "Biking", "Description", 1, 2, 3));
        /*
        List<Event> events = new ArrayList<>();
        events.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(2, 2, "Running for Life", "Description", "Suceava", "21.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "21.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "16.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "16.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "24.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "24.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "24.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "24.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "25.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        events = new ArrayList<>();
        events.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "25.7.2020", "10", "Running", 2, 3, 1));
        allEventList.add(events);

        eventParentAdapter.notifyDataSetChanged();

         */
    }
}