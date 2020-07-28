package com.example.sportsclubmanagementapp.screens.club_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.models.ClubDetails;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Notification;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.eventdetails.EventDetailsActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.EventAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnEventItemListener;
import com.example.sportsclubmanagementapp.screens.notification.NotificationActivity;
import com.example.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubPageActivity extends AppCompatActivity implements OnEventItemListener {

    private List<Notification> notification = new ArrayList<>();
    private ClubDetails clubDetails;
    private Club club;

    //for members list recycler
    private TextView membersTextView;
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    //for events list recycler
    private TextView eventTextView;
    private RecyclerView recyclerViewEvents;

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
        displayAvatar();
        setUpNotifications();
        initComponents();
        getApiCoach(club.getId());
    }

    private void initComponents() {
        membersTextView = findViewById(R.id.members);
        eventTextView = findViewById(R.id.events);
        recyclerViewUsers = findViewById(R.id.members_recycler_view);
        recyclerViewEvents = findViewById(R.id.events_recycler_view);
    }

    private void checkMembersRecyclerViewEmpty() {
        if (clubDetails == null || clubDetails.getMembers().isEmpty()) {
            membersTextView.setText(getResources().getText(R.string.no_members));
            recyclerViewUsers.setVisibility(View.GONE);
        } else {
            membersTextView.setText(getResources().getText(R.string.members_txt));
            recyclerViewUsers.setVisibility(View.VISIBLE);
        }
    }

    private void checkEventsRecyclerViewEmpty() {
        if (clubDetails == null || clubDetails.getEvents().isEmpty()) {
            eventTextView.setText(getResources().getText(R.string.no_events));
            recyclerViewEvents.setVisibility(View.GONE);
        } else {
            eventTextView.setText(getResources().getText(R.string.events));
            recyclerViewEvents.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTheCouchDetails() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(clubDetails.getName());
        TextView name = findViewById(R.id.name);
        name.setText(clubDetails.getCoach().getFirst_name() + " " + clubDetails.getCoach().getLast_name());
        TextView role = findViewById(R.id.role);
        role.setText(getResources().getText(R.string.coach));
        TextView age = findViewById(R.id.age);
        age.setText(String.valueOf(clubDetails.getCoach().getAge()));
        TextView clubsOwned = findViewById(R.id.owned_clubs);
        StringBuilder clubs = new StringBuilder(clubDetails.getCoach().getClubs()[0]);
        for(int i=1; i<clubDetails.getCoach().getClubs().length; i++)
            clubs.append(", ").append(clubDetails.getCoach().getClubs()[i]);
        clubsOwned.setText(clubs.toString());
    }

    private void getApiCoach(int id) {
        Call<ClubDetails> call = ApiHelper.getApi().getClubDetails(getToken(), id);
        call.enqueue(new Callback<ClubDetails>() {
            @Override
            public void onResponse(@NotNull Call<ClubDetails> call, @NotNull Response<ClubDetails> response) {
                if (!response.isSuccessful()) {
                    checkMembersRecyclerViewEmpty();
                    checkEventsRecyclerViewEmpty();
                    Toast.makeText(getBaseContext(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                }
                else {
                    clubDetails = response.body();
                    setTheCouchDetails();
                    setUpUsersRecyclerView(); //for users recycler
                    setUpEventsRecyclerView(); //for events recycler
                    checkMembersRecyclerViewEmpty();
                    checkEventsRecyclerViewEmpty();
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClubDetails> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
                checkMembersRecyclerViewEmpty();
                checkEventsRecyclerViewEmpty();
            }
        });
    }

    private String getToken() {
        SharedPreferences prefs = Objects.requireNonNull(getBaseContext()).getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), getString(R.string.no_token_prefs));
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
                .load(Utils.getAvatars(getBaseContext()).get(new Random().nextInt(5)))
                .apply(new RequestOptions().circleCrop())
                .into((ImageView) findViewById(R.id.avatar));
    }

    private void getClubFromLastActivity() {
        club = (Club) getIntent().
                getSerializableExtra("CLUB_EXTRA_SESSION_ID"); //get the club object from the last screen
    }

    private void setUpUsersRecyclerView() {
        userAdapter = new UserAdapter(clubDetails.getMembers(), this, UserAdapter.MEMBER_BAR_WITHOUT_CHECK_BOX);
        RecyclerView.LayoutManager usersLayoutManager =
                new LinearLayoutManager(userAdapter.getContext());
        recyclerViewUsers.setLayoutManager(usersLayoutManager);
        recyclerViewUsers.setAdapter(userAdapter);
    }

    private void setUpEventsRecyclerView() {
        EventAdapter eventAdapter = new EventAdapter(clubDetails.getEvents(), this, 2, this);
        RecyclerView.LayoutManager eventLayoutManager =
                new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(eventLayoutManager);
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    @Override
    public void onEventsClick(Event event) {
        Intent intent = new Intent(getBaseContext(), EventDetailsActivity.class);
        intent.putExtra(getString(R.string.event_id), event.getId());
        intent.putExtra(getString(R.string.event_status), event.getStatus());
        startActivity(intent);
    }

    @Override
    public void onEventsJoinClick(Event event) {
        //no join button for these events
    }
}