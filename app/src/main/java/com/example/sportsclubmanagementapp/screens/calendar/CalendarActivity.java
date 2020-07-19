package com.example.sportsclubmanagementapp.screens.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    //for parent list recycler
    private List<Clubs> clubsList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerViewParent;
    private EventParentAdapter eventParentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //for events recycler
        recyclerViewParent = (RecyclerView) findViewById(R.id.club_events_parent_recycler_view);
        eventParentAdapter = new EventParentAdapter(clubsList, eventList, this);
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventParentAdapter.getActivity());
        recyclerViewParent.setLayoutManager(eventLayoutManager);
        recyclerViewParent.setAdapter(eventParentAdapter);

        prepareEventData();
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

    public void goToNotificationsScreen(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void prepareEventData() {
        clubsList.add(new Clubs(1, 1, "Running", "Description", 1, 2, 3));
        clubsList.add(new Clubs(2, 1, "Football", "Description", 1, 2, 3));
        clubsList.add(new Clubs(3, 1, "Biking", "Description", 1, 2, 3));

        eventList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(2, 2, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        //eventList.add(new Event(3, 3, "Biking for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        eventParentAdapter.notifyDataSetChanged();
    }
}