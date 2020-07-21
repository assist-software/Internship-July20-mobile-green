package com.example.sportsclubmanagementapp.EventDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.screens.addworkout.AddWorkoutActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EventDetailsActivity extends AppCompatActivity {

    private Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        setToolbar();
        setEvent();
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
            button.setText("join");
            button.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.btn_rounded_8dp_black,null));
            button.setTextColor(getResources().getColor(R.color.colorAccent));
        } else{
            button.setText("joined");
            button.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.btn_rounded_8dp_light_gray,null));
            button.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}