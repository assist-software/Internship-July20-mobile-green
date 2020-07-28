package com.example.sportsclubmanagementapp.screens.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Notification;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private List<Notification> notificationList = new ArrayList<>();
    private RecyclerView recyclerViewNotification;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerViewNotification = findViewById(R.id.notification_recycler_view);
        notificationAdapter = new NotificationAdapter(notificationList, this);
        RecyclerView.LayoutManager notificationLayoutManager = new LinearLayoutManager(notificationAdapter.getContext());
        recyclerViewNotification.setLayoutManager(notificationLayoutManager);
        recyclerViewNotification.setAdapter(notificationAdapter);

        //for TESTS
        prepareNotificationData();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.notificationToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void prepareNotificationData() {
        notificationList.add(new Notification("2 mins ago", "Coach", "John Woston", "invited you to join", "Biking Club"));
        notificationList.add(new Notification("4 mins ago", "Coach", "Connie Webb", "invited you to join", "Running Club"));
        notificationList.add(new Notification("6 mins ago", "Coach", "Connie Webb", "accepted request to join", "Running Club"));
        notificationList.add(new Notification("7 mins ago", "Coach", "John Woston", "invited you to join", "Cycling Club"));
        notificationList.add(new Notification("7 mins ago", "Coach", "John Woston", "accepted request to join", "Biking Club"));
        notificationList.add(new Notification("10 mins ago", "Coach", "John Woston", "accepted request to join", "Cycling Club"));
        notificationList.add(new Notification("25 mins ago", "Coach", "Connie Webb", "invited you to join", "Tennis Club"));
        notificationList.add(new Notification("30 mins ago", "Coach", "JConnie Webb", "accepted request to join", "Tennis Club"));

        notificationAdapter.notifyDataSetChanged();
    }
}