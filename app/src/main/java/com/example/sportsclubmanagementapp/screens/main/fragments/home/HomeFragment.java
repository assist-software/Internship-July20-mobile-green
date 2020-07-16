package com.example.sportsclubmanagementapp.screens.main.fragments.home;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;

public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static HomeFragment newInstance() {
        return  new HomeFragment();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        DrawerLayout drawer = getActivity().findViewById(R.id.drawerLayout);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((AppCompatActivity)getActivity(),drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

}