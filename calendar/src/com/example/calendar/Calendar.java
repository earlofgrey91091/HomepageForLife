package com.example.calendar;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Calendar extends Activity {

	final static int VIEW_EVENT = 1;
	private SharedPreferences sharedPref;
	private ArrayList<CalendarEvent> event_list = new ArrayList<CalendarEvent>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        if (event_list.isEmpty())
        {
        	//			READ FROM FILE HERE
        	event_list.add(new CalendarEvent("5/26","blah"));
        	event_list.add(new CalendarEvent("5/27","duh"));
        }
        setContentView(R.layout.activity_calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calendar, menu);
        return true;
	}

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	switch(requestCode) {
        case VIEW_EVENT: 
              if (resultCode == RESULT_OK) {
            	  CalendarEvent event = (CalendarEvent) data.getSerializableExtra("event");
                  event_list.add(event);
                  break;
              }
    	}
    }
    
	public void newEvent(View view) {
		Intent intent = new Intent(this, NewEvent.class);
	    startActivityForResult(intent,VIEW_EVENT);
	}
	
	public void viewEvents(View view) {
		Intent intent = new Intent(this, ViewEvents.class);
		intent.putExtra("events",event_list);
		startActivity(intent);
	}
}
