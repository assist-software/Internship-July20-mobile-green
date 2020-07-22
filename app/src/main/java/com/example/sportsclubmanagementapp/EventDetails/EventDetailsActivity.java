package com.example.sportsclubmanagementapp.EventDetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Role;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.screens.addworkout.AddWorkoutActivity;
import com.example.sportsclubmanagementapp.screens.club_page.UserAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.HomeFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Event event;
    private GoogleMap mMap;

    //for members list recycler
    private List<User> usersList = new ArrayList<>();
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(EventDetailsActivity.this);

        setUpUsersRecyclerView(); //for users recycler
        prepareUsersData();
        TextView title = findViewById(R.id.eventParticipantsTextView);
        title.setText("Participants (" + String.valueOf(usersList.size()) + ")");

        setToolbar();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpChart();

    }

    private void setUpChart() {
        barChart = (BarChart) findViewById(R.id.barChart);

        BarData barData = new BarData();
        List<BarEntry> list = new ArrayList<>();

        list.add(new BarEntry(1, 1));
        BarDataSet dataSet = new BarDataSet(list, "");
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextSize(15f);
        barData.addDataSet(dataSet);

        list = new ArrayList<>();
        list.add(new BarEntry(2, 1));
        dataSet = new BarDataSet(list, "");
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextSize(15f);
        barData.addDataSet(dataSet);

        list = new ArrayList<>();
        list.add(new BarEntry(3, 1));
        dataSet = new BarDataSet(list, "");
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextSize(15f);
        barData.addDataSet(dataSet);

        final ArrayList<String> xAxisLabel = new ArrayList<>();

        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Wed");

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setTextSize(14);
        barChart.setExtraOffsets(0,0,20,12);
        barChart.getAxisLeft().setTextSize(14);

        barChart.setData(barData);
        barChart.invalidate();
    }

    private void setUpUsersRecyclerView(){
        recyclerViewUsers = (RecyclerView) findViewById(R.id.members_recycler_view);
        userAdapter = new UserAdapter(usersList, this, UserAdapter.MEMBER_BAR_WITH_CHECK_BOX);
        RecyclerView.LayoutManager usersLayoutManager = new LinearLayoutManager(userAdapter.getContext());
        recyclerViewUsers.setLayoutManager(usersLayoutManager);
        recyclerViewUsers.setAdapter(userAdapter);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.eventDetailsActivityToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setEvent(){
        if(getIntent().getExtras() != null) {
            event = (Event) getIntent().getSerializableExtra("eventObject");
            setContent();
        }
    }

    private void setContent(){
        TextView title = findViewById(R.id.eventTitleTextView);
        TextView date = findViewById(R.id.dateEventTextView);
        TextView time = findViewById(R.id.timeEventTextView);
        TextView location = findViewById(R.id.eventLocationTextView);
        TextView description1 = findViewById(R.id.eventDescription1TextView);
        TextView description2 = findViewById(R.id.eventDescription2TextView);

        title.setText(event.getName());
        date.setText(event.getDate());
        time.setText(event.getDate());
        location.setText(event.getLocation());
        description1.setText(event.getDescription());
        description2.setText(event.getDescription());
    }

    public void goToAddWorkoutScreen(View view){
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        startActivity(intent);
    }

    public void changeEventStatus(View view){
        Button button = findViewById(R.id.eventStatusButton);
        String buttonText = button.getText().toString();
        if (buttonText.toLowerCase().equals("joined")){
            button.setText(getResources().getText(R.string.join_txt));
            button.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.btn_rounded_8dp_black,null));
            button.setTextColor(getResources().getColor(R.color.colorAccent));
        } else{
            button.setText(getResources().getText(R.string.joined_txt));
            button.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.btn_rounded_8dp_light_gray,null));
            button.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng assist = new LatLng(47.640121, 26.259330);
        mMap.addMarker(new MarkerOptions().position(assist).title("ASSIST"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(assist, 17f));
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(assist)
                .radius(100)
                .strokeColor(Color.argb(128, 0, 0, 255))
                .strokeWidth(5f)
                .fillColor(Color.argb(80, 0, 0, 255)));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    private void prepareUsersData(){
        usersList.add(new User(1, "Brandom Wilson", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(2, "Nelsol Cooper", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(3, "Mihai Icon", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(4, "Ron Shit", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));

        userAdapter.notifyDataSetChanged();
    }
}