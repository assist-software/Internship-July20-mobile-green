package com.example.sportsclubmanagementapp.screens.main.fragments.events;

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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.eventdetails.EventDetailsActivity;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.EventAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnEventItemListener;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;
import com.example.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment implements OnEventItemListener {
    private RecyclerView recyclerViewPastEvents;
    private RecyclerView recyclerViewJoinedEvents;
    private RecyclerView recyclerViewPendingEvents;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    private void setToolbar() {
        setToolbarAvatar();
        setToolbarTitle();
        setToolbarIconNavigation();
    }

    private void setToolbarAvatar() {
        CircleImageView avatar_toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.avatar_toolbar);
        avatar_toolbar.setVisibility(View.VISIBLE); //set the avatar visible (because is hidden for home fragment)
        Utils.setCircleAvatar(getActivity(), Objects.requireNonNull((MainActivity) getActivity()).getAvatar(), avatar_toolbar);
    }

    private void setToolbarTitle() {
        TextView fragment_title = Objects.requireNonNull(getActivity()).findViewById(R.id.fragment_title);
        fragment_title.setText(getResources().getText(R.string.events_txt));
    }

    private void setToolbarIconNavigation() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getActivity(), MyProfileActivity.class)));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpAllRecycleViews(view);
        getApiEvents();
    }

    private void setUpAllRecycleViews(View view) {
        recyclerViewPastEvents = view.findViewById(R.id.pastEventsRecyclerView);
        recyclerViewJoinedEvents = view.findViewById(R.id.joinedEvetsRecyclerView);
        recyclerViewPendingEvents = view.findViewById(R.id.pendingEventsRecyclerView);
    }

    private void getApiEvents() {
        Call<List<Event>> call = ApiHelper.getApi().getEvents(getToken());
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NotNull Call<List<Event>> call, @NotNull Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                } else {
                    assert response.body() != null;
                    List<Event> pastEvents = new ArrayList<>(response.body());
                    List<Event> joinedEvents = pastEvents.stream().filter(event -> event.getStatus() != null && event.getStatus()[0] == 1).collect(Collectors.toList());
                    List<Event> pendingEvents = pastEvents.stream().filter(event -> event.getStatus() != null && event.getStatus()[0] == 0).collect(Collectors.toList());
                    filterPastEventsList(pastEvents);
                    initEventsAdapter(joinedEvents, recyclerViewJoinedEvents, 2);
                    initEventsAdapter(pendingEvents, recyclerViewPendingEvents, 4);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Event>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), getString(R.string.no_token_prefs));
    }

    private void filterPastEventsList(List<Event> events) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String nowStr = sdformat.format(now);
        Iterator it = events.iterator();
        try {
            now = sdformat.parse(nowStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (it.hasNext()) {
            Event e = (Event) it.next();
            String date = e.getDate();
            try {
                Date d1 = sdformat.parse(date);
                assert d1 != null;
                if (d1.compareTo(now) >= 0) {
                    it.remove();
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        initEventsAdapter(events, recyclerViewPastEvents, 2);
    }

    private void initEventsAdapter(List<Event> events, RecyclerView recyclerView, int layout) {
        EventAdapter adapter = new EventAdapter(events, getContext(), layout, this);
        RecyclerView.LayoutManager eventsLayoutManager;
        if (layout == 2) {
            eventsLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            eventsLayoutManager = new LinearLayoutManager(this.getContext());
        }
        recyclerView.setLayoutManager(eventsLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEventsClick(Event event) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra(getString(R.string.event_id), event.getId());
        startActivity(intent);
    }

    @Override
    public void onEventsJoinClick(Event event) {

    }
}
