package com.example.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetails extends Activity {

//	public CalendarEvent event;
//	DbHandler db;
//	int eventId = 1; //temporarily set to 1 until I figure out how to pass into this activity
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
//        db = new DbHandler(this);
        setContentView(R.layout.activity_event_details);
//		  event = db.getEvent(eventId);
		
        TextView name = (TextView) findViewById(R.id.name);
//        name.setText(event.getName());
		name.setText("Birthday");
        
		TextView date = (TextView) findViewById(R.id.date);
//        date.setText(event.getDate());
        date.setText("10/10/2013");

        TextView location = (TextView) findViewById(R.id.location);
//        location.setText(event.getLocation());
        location.setText("Ur moms house");
		
	}
	
}