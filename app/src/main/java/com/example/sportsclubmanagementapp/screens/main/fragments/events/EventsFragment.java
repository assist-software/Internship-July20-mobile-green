package com.example.sportsclubmanagementapp.screens.main.fragments.events;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.EventAdapter;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {


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

    public static EventsFragment newInstance() {
        return  new EventsFragment();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Events");
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_default_avatar, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //for future events recycler
        recyclerViewPastEvents = (RecyclerView) view.findViewById(R.id.pastEventsRecyclerView);
        pastEventsAdapter = new EventAdapter(pastEventsList, getContext(),2);
        RecyclerView.LayoutManager pastEventsLayoutManager = new LinearLayoutManager(pastEventsAdapter.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPastEvents.setLayoutManager(pastEventsLayoutManager);
        recyclerViewPastEvents.setAdapter(pastEventsAdapter);
        preparePastEventsData();

        //for joined events recycler
        recyclerViewJoinedEvents = (RecyclerView) view.findViewById(R.id.joinedEvetsRecyclerView);
        joinedEventsAdapter = new EventAdapter(joinedEventsList, getContext(),2);
        RecyclerView.LayoutManager joinedEventsLayoutManager = new LinearLayoutManager(joinedEventsAdapter.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewJoinedEvents.setLayoutManager(joinedEventsLayoutManager);
        recyclerViewJoinedEvents.setAdapter(joinedEventsAdapter);
        prepareJoinedEventsData();

        //for pending events recycler
        recyclerViewPendingEvents = (RecyclerView) view.findViewById(R.id.pendingEventsRecyclerView);
        pendingEventsAdapter = new EventAdapter(pendingEventsList, getContext(),4);
        RecyclerView.LayoutManager pendingEventsLayoutManager = new LinearLayoutManager(pendingEventsAdapter.getContext());
        recyclerViewPendingEvents.setLayoutManager(pendingEventsLayoutManager);
        recyclerViewPendingEvents.setAdapter(pendingEventsAdapter);
        preparePendingEventsData();

    }

    private void preparePastEventsData() {
        pastEventsList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        pastEventsList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        pastEventsList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        pastEventsList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        pastEventsAdapter.notifyDataSetChanged();
    }

    private void prepareJoinedEventsData() {
        joinedEventsList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        joinedEventsList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        joinedEventsList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        joinedEventsList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        joinedEventsAdapter.notifyDataSetChanged();
    }

    private void preparePendingEventsData() {
        pendingEventsList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        pendingEventsList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        pendingEventsList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        pendingEventsList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        pendingEventsAdapter.notifyDataSetChanged();
    }
}
