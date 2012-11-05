package com.example.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetails extends Activity {

	public CalendarEvent event;
	DbHandler db;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        db = new DbHandler(this);
        setContentView(R.layout.activity_event_details);
		event = db.getEvent(eventId);
		
        TextView name = (TextView) findViewById(R.id.name);
        String formatName = new String("Name: ");
//        formatName.concat(event.getName());
		name.setText(formatName);
        
		TextView date = (TextView) findViewById(R.id.date);
		String formatDate = new String("Date: ");
//        formatDate.concat(event.getDate());
		date.setText(formatDate);

        TextView location = (TextView) findViewById(R.id.location);
        String formatLocation = new String("Location: ");
//        formatLocation.concat(event.getLocation());
		location.setText(formatLocation);
		
	}
	
}