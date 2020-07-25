package com.example.sportsclubmanagementapp.screens.club_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Notification;
import com.example.sportsclubmanagementapp.data.models.Role;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.EventAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnEventItemListener;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;
import com.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClubPageActivity extends AppCompatActivity implements OnEventItemListener {

    List<Notification> notification = new ArrayList<>();
    private List<Drawable> avatars; //for TESTS

    //for events list recycler
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;

    //for members list recycler
    private List<User> usersList = new ArrayList<>();
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    private Club club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);

        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getClubFromLastActivity(); //get club object pressed in the last screen
        setTheCouchDetails();

        avatars = Utils.getAvatars(getBaseContext()); //for TESTS
        displayAvatar();

        setUpNotifications();
        setUpUsersRecyclerView(); //for users recycler
        setUpEventsRecyclerView(); //for events recycler
        //values for TESTS
        prepareEventData();
        prepareUsersData();
    }

    private void setTheCouchDetails() {
        /*TextView name = findViewById(R.id.name);
        name.setText(club.getName());
        TextView role = findViewById(R.id.role);*/
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setUpNotifications() {
        //for TESTS
        notification.add(new Notification("2 min ago", "Coach", "John Down", "invited you in", "Running Club"));

        ImageView notificationIcon = findViewById(R.id.notificationImageView);
        if (notification.isEmpty())
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar, null));
        else
            notificationIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notifications_toolbar_news, null));
    }

    public void goToNotificationsScreen(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_view_on_click));
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void displayAvatar() {
        Glide.with(this)
                .load(avatars.get(new Random().nextInt(5)))
                .apply(new RequestOptions().circleCrop())
                .into((ImageView) findViewById(R.id.avatar));
    }

    private void getClubFromLastActivity() {
        club = (Club) getIntent().
                getSerializableExtra("CLUB_EXTRA_SESSION_ID"); //get the club object from the last screen
    }

    private void setUpUsersRecyclerView() {
        recyclerViewUsers = findViewById(R.id.members_recycler_view);
        userAdapter = new UserAdapter(usersList, this, UserAdapter.MEMBER_BAR_WITHOUT_CHECK_BOX);
        RecyclerView.LayoutManager usersLayoutManager =
                new LinearLayoutManager(userAdapter.getContext());
        recyclerViewUsers.setLayoutManager(usersLayoutManager);
        recyclerViewUsers.setAdapter(userAdapter);
    }

    private void setUpEventsRecyclerView() {
        recyclerViewEvents = findViewById(R.id.events_recycler_view);
        eventAdapter = new EventAdapter(eventList, this, 2, this);
        RecyclerView.LayoutManager eventLayoutManager =
                new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(eventLayoutManager);
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    @Override
    public void onEventsClick(Event event) {

    }

    @Override
    public void onEventsJoinClick(Event event) {

    }

    private void prepareEventData() {
        eventList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        eventList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));

        eventAdapter.notifyDataSetChanged();
    }

    private void prepareUsersData() {
        usersList.add(new User(1, "Brandom Wilson", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(2, "Nelsol Cooper", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(3, "Mihai Icon", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(4, "Ron Shit", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));

        userAdapter.notifyDataSetChanged();
    }
}