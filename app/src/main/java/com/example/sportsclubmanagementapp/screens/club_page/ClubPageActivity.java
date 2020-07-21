package com.example.sportsclubmanagementapp.screens.club_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.EventAdapter;
import com.example.sportsclubmanagementapp.data.models.Role;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClubPageActivity extends AppCompatActivity {

    //for events list recycler
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;
    private Clubs club;

    //for members list recycler
    private List<User> usersList = new ArrayList<>();
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);

        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        displayAvatar();

        getClubFromLastActivity(); //get club object pressed in the last screen

        setUpUsersRecyclerView(); //for users recycler
        setUpEventsRecyclerView(); //for events recycler

        //values for tests
        prepareEventData();
        prepareUsersData();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void goToNotificationsScreen(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void displayAvatar() {
        Glide.with(this).load(R.mipmap.ic_default_avatar).centerCrop().into( (CircleImageView) findViewById(R.id.avatar) );
    }

    private void getClubFromLastActivity(){
        club = (Clubs) getIntent().getSerializableExtra("CLUB_EXTRA_SESSION_ID");
        Toast.makeText(getBaseContext(), String.valueOf(club.getId()), Toast.LENGTH_SHORT).show();
    }

    private void setUpUsersRecyclerView(){
        recyclerViewUsers = (RecyclerView) findViewById(R.id.members_recycler_view);
        userAdapter = new UserAdapter(usersList, this, R.layout.item_member);
        RecyclerView.LayoutManager usersLayoutManager = new LinearLayoutManager(userAdapter.getContext());
        recyclerViewUsers.setLayoutManager(usersLayoutManager);
        recyclerViewUsers.setAdapter(userAdapter);
    }

    private void setUpEventsRecyclerView(){
        recyclerViewEvents = (RecyclerView) findViewById(R.id.events_recycler_view);
        eventAdapter = new EventAdapter(eventList, this, 2);
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(eventLayoutManager);
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    private void prepareEventData() {
        eventList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        eventAdapter.notifyDataSetChanged();
    }

    private void prepareUsersData(){
        usersList.add(new User(1, "Brandom Wilson", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(2, "Nelsol Cooper", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(3, "Mihai Icon", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(4, "Ron Shit", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));

        userAdapter.notifyDataSetChanged();
    }
}