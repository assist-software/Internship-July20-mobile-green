package com.example.sportsclubmanagementapp.screens.main.fragments.clubs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

public class ClubsFragment extends Fragment {

    public static ClubsFragment newInstance() {
        return new ClubsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_clubs, container, false);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Clubs");
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.my_profile_toolbar, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}