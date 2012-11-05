package com.example.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetails extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState);
	DbHandler db = new DbHandler(this);
	setContentView(R.layout.activity_event_details);
	Intent data = getIntent();
	String theDate = (String) data.getSerializableExtra("Date");
	CalendarEvent event = db.getEvent(theDate);
	
	TextView name = (TextView) findViewById(R.id.name);
	name.setText(event.getName());
	
	TextView date = (TextView) findViewById(R.id.date);
	date.setText(event.getDate());
	
	TextView location = (TextView) findViewById(R.id.location);
	location.setText(event.getLocation());
		
	}
	
}
