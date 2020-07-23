package com.example.sportsclubmanagementapp.screens.main.fragments.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.EventAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnEventItemListener;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment implements OnEventItemListener {

    //for past events recycler
    private List<Event> pastEventsList = new ArrayList<>();
    private RecyclerView recyclerViewPastEvents;
    private EventAdapter pastEventsAdapter;

    //for joined events recycler
    private List<Event> joinedEventsList = new ArrayList<>();
    private RecyclerView recyclerViewJoinedEvents;
    private EventAdapter joinedEventsAdapter;

    //for pending events recycler
    private List<Event> pendingEventsList = new ArrayList<>();
    private RecyclerView recyclerViewPendingEvents;
    private EventAdapter pendingEventsAdapter;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_events, container, false);

    }

    private void setToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.events)); //set toolbar name from strings

        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.my_profile_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewPastEvents = view.findViewById(R.id.pastEventsRecyclerView);
        makePastEvents();
        recyclerViewJoinedEvents = view.findViewById(R.id.joinedEvetsRecyclerView);
        makeJoinedEvents();
        recyclerViewPendingEvents = view.findViewById(R.id.pendingEventsRecyclerView);
        makePendingEvents();
    }

    private void preparePastEventsData() {
        pastEventsList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "28-07-2020", "10", "Running", 2, 3, 1));
        pastEventsList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16-07-2020", "10", "Running", 2, 3, 1));
        pastEventsList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16-07-2020", "10", "Running", 2, 3, 1));
        pastEventsList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16-07-2020", "10", "Running", 2, 3, 1));
        filterPastEventsList();
        pastEventsAdapter.notifyDataSetChanged();
    }

    private void prepareJoinedEventsData() {
        joinedEventsList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        joinedEventsList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        joinedEventsList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        joinedEventsList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));

        joinedEventsAdapter.notifyDataSetChanged();
    }

    private void preparePendingEventsData() {
        pendingEventsList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        pendingEventsList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        pendingEventsList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));
        pendingEventsList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", "10", "Running", 2, 3, 1));

        pendingEventsAdapter.notifyDataSetChanged();
    }

    private void makePastEvents() {
        pastEventsAdapter = new EventAdapter(pastEventsList, getContext(), EventAdapter.HORIZONTAL_NO_BTN_EVENT, this);
        RecyclerView.LayoutManager pastEventsLayoutManager =
                new LinearLayoutManager(pastEventsAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPastEvents.setLayoutManager(pastEventsLayoutManager);
        recyclerViewPastEvents.setAdapter(pastEventsAdapter);

        preparePastEventsData();
    }

    private void makeJoinedEvents() {
        joinedEventsAdapter = new EventAdapter(joinedEventsList, getContext(), EventAdapter.HORIZONTAL_NO_BTN_EVENT, this);
        RecyclerView.LayoutManager joinedEventsLayoutManager =
                new LinearLayoutManager(joinedEventsAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewJoinedEvents.setLayoutManager(joinedEventsLayoutManager);
        recyclerViewJoinedEvents.setAdapter(joinedEventsAdapter);

        prepareJoinedEventsData();
    }

    private void makePendingEvents() {
        pendingEventsAdapter = new EventAdapter(pendingEventsList, getContext(), EventAdapter.VERTICAL_NO_BTN_EVENT, this);
        RecyclerView.LayoutManager pendingEventsLayoutManager =
                new LinearLayoutManager(pendingEventsAdapter.getContext());
        recyclerViewPendingEvents.setLayoutManager(pendingEventsLayoutManager);
        recyclerViewPendingEvents.setAdapter(pendingEventsAdapter);

        preparePendingEventsData();
    }

    private void getAllEvents() {
        Call<List<Event>> call = ApiHelper.getApi().getEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NotNull Call<List<Event>> call, @NotNull Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error response: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //success
                setAllEvents(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<List<Event>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), "Error failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAllEvents(List<Event> l) {
        //all events
    }

    @Override
    public void onEventsClick(Event event) {

    }

    private void filterPastEventsList() {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        String nowStr = sdformat.format(now);
        Iterator it = pastEventsList.iterator();
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
    }
}
