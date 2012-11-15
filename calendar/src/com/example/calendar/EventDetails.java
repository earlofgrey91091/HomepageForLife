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
		ExpandableListView fileList;
		ExpandableListView noteList;
		db = new DbHandler(this);
		setContentView(R.layout.activity_event_details);
		Intent data = getIntent();
		String theDate = (String) data.getSerializableExtra("Date");
		event = db.getEvent(theDate);
	
		TextView name = (TextView) findViewById(R.id.name);
		name.setText("Name: " + event.getName());
		
		TextView date = (TextView) findViewById(R.id.date);
		date.setText("Date: " + event.getDate());
	
		TextView location = (TextView) findViewById(R.id.location);
		location.setText("Location: " + event.getLocation());
 
        //contact list
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
		contactList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsContact,0));

		//file list
		fileList = (ExpandableListView)findViewById(R.id.file_list);
		Parent fileParent = new Parent();
		ArrayList<Parent> arrayParentsFile = new ArrayList<Parent>();
		fileParent.setTitle("Files");
		ArrayList<String> arrayFiles = new ArrayList<String>();
		for(int i=1;i<=3;i++) {
            arrayFiles.add("File "+i);
        }
		fileParent.setArrayChildren(arrayFiles);
		arrayParentsFile.add(fileParent);
		fileList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsFile,1));
		
		//apps should go here
		
		//note list
		noteList = (ExpandableListView)findViewById(R.id.note_list);
		Parent noteParent = new Parent();
		ArrayList<Parent> arrayParentsNote = new ArrayList<Parent>();
		noteParent.setTitle("Notes");
		ArrayList<String> arrayNotes = new ArrayList<String>();
		for(int i=1;i<=3;i++) {
            arrayNotes.add("Note "+i);
        }
		noteParent.setArrayChildren(arrayNotes);
		arrayParentsNote.add(noteParent);
		noteList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsNote,2));
		//notes.setText("Notes: \n" + event.getNotes());
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
