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
		ExpandableListView contactList;
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
		location.setText("Location: " + event.getLocation());
 
        //here we set the parents and the children
        contactList = (ExpandableListView)findViewById(R.id.contact_list);
		Parent contactParent = new Parent();
		ArrayList<Parent> arrayParentsContact = new ArrayList<Parent>();
		contactParent.setTitle("Contacts");
		ArrayList<String> arrayContacts = new ArrayList<String>();
		for(int i=1;i<=3;i++) {
            arrayContacts.add("Contact "+i);
        }
		contactParent.setArrayChildren(arrayContacts);
		arrayParentsContact.add(contactParent);
		contactList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsContact));
		
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
