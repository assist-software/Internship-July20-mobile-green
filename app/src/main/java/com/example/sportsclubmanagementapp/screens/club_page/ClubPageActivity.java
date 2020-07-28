package com.example.sportsclubmanagementapp.screens.club_page;

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
import com.example.sportsclubmanagementapp.data.models.Coach;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Notification;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
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
    private Coach coach;
    private Club club;

    //for members list recycler
    private TextView membersTextView;
    private List<User> usersList = new ArrayList<>();
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    //for events list recycler
    private TextView eventTextView;
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;

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
        getApiCoach();
        //setTheCouchDetails();
        displayAvatar();
        setUpNotifications();
        initComponents();
        setUpUsersRecyclerView(); //for users recycler
        setUpEventsRecyclerView(); //for events recycler
        prepareUsersData(); //values for TESTS
    }

    private void initComponents() {
        membersTextView = findViewById(R.id.members);
        eventTextView = findViewById(R.id.events);
    }

    private void checkMembersRecyclerViewEmpty() {
        if (usersList.isEmpty()) {
            membersTextView.setText(getResources().getText(R.string.no_members));
            recyclerViewUsers.setVisibility(View.GONE);
        } else {
            membersTextView.setText(getResources().getText(R.string.members_txt));
            recyclerViewUsers.setVisibility(View.VISIBLE);
        }
    }

    private void checkEventsRecyclerViewEmpty() {
        if (eventList.isEmpty()) {
            eventTextView.setText(getResources().getText(R.string.no_events));
            recyclerViewEvents.setVisibility(View.GONE);
        } else {
            eventTextView.setText(getResources().getText(R.string.events));
            recyclerViewEvents.setVisibility(View.VISIBLE);
        }
    }

    private void setTheCouchDetails() {
        TextView name = findViewById(R.id.name);
        //name.setText(usersList.get(0).getFirst_and_last_name());
        TextView role = findViewById(R.id.role);
        //role.setText(usersList.get(0).getRole().getRoleString());
        TextView age = findViewById(R.id.age);
        //age.setText(String.valueOf(usersList.get(0).getAge()));
        TextView clubsOwned = findViewById(R.id.owned_clubs);
        //clubsOwned.setText(usersList.get(0).getSecondarySport());
    }

    private void getApiCoach() {
        Call<Coach> call = ApiHelper.getApi().getClubDetails(getToken(), 1);
        call.enqueue(new Callback<Coach>() {
            @Override
            public void onResponse(@NotNull Call<Coach> call, @NotNull Response<Coach> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                } else {
                    coach = response.body();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Coach> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void prepareUsersData() {
        //usersList.add(new User(1, "Brandom Wilson", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        //usersList.add(new User(2, "Nelsol Cooper", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        //usersList.add(new User(3, "Mihai Icon", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        //usersList.add(new User(4, "Ron Shit", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        userAdapter.notifyDataSetChanged();
        checkMembersRecyclerViewEmpty();
        checkEventsRecyclerViewEmpty();
        setTheCouchDetails();
    }
}