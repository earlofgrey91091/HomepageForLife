package com.example.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ExpandableListView;
import android.app.ExpandableListActivity;

import java.util.ArrayList;

public class EventDetails extends Activity {
	DbHandler db;
	CalendarEvent event;
	
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		ExpandableListView mExpandableList;
		db = new DbHandler(this);
		setContentView(R.layout.activity_event_details);
		Intent data = getIntent();
		String theDate = (String) data.getSerializableExtra("Date");
		event = db.getEvent(theDate);
	
		TextView name = (TextView) findViewById(R.id.name);
		name.setText("Name:" + event.getName());
		
		TextView date = (TextView) findViewById(R.id.date);
		date.setText("Date: " + event.getDate());
	
		TextView location = (TextView) findViewById(R.id.location);
		location.setText("Locaction" + event.getLocation());
		
		mExpandableList = (ExpandableListView)findViewById(R.id.expandable_list);
		 
        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<String> arrayChildren = new ArrayList<String>();
 
        //here we set the parents and the children
        for (int i = 0; i < 10; i++){
            //for each "i" create a new Parent object to set the title and the children
            Parent parent = new Parent();
            parent.setTitle("Parent " + i);
            arrayChildren.add("Child " + i);
            parent.setArrayChildren(arrayChildren);
 
            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
            arrayParents.add(parent);
        }
 
        //sets the adapter that provides data to the list.
        mExpandableList.setAdapter(new MyCustomAdapter(EventDetails.this,arrayParents));
		
	}
	
	public void removeEvent(View view) {
		db.deleteEvent(event);
		finish();
		Intent intent = new Intent(this, ViewEvents.class);
		startActivity(intent);
    }
	
	public void editEvent(View view){
		
		
		
	}
	
}
