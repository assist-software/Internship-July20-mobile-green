package com.example.sportsclubmanagementapp.screens.main.fragments.events;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.FutureEvents;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.FutureEventsAdapter;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    //for past events recycler
    private List<Event> pastEventsList = new ArrayList<>();
    private RecyclerView recyclerViewPasEvents;
    private FutureEventsAdapter pastEventsAdapter;


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
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.my_profile_toolbar, null));
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
    /*
        //for future events recycler
        recyclerViewPasEvents = (RecyclerView) view.findViewById(R.id.pastEventsRecyclerView]);
        pastEventsAdapter = new PastEventsAdapter(pastEventsList, getContext());
        RecyclerView.LayoutManager futureEventsLayoutManager = new LinearLayoutManager(pastEventsAdapter.getContext());
        recyclerViewPasEvents.setLayoutManager(futureEventsLayoutManager);
        recyclerViewPasEvents.setAdapter(pastEventsAdapter);
        super.onViewCreated(view, savedInstanceState);
        */

    }
}