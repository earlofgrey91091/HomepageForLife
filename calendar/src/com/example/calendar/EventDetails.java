package com.example.calendar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class EventDetails extends Activity {
	DbHandler db;
	CalendarEvent event;
	
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		ExpandableListView contactList;
		ExpandableListView fileList;
		ExpandableListView appList;
		ExpandableListView linkList;
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
		contactList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsContact,ContactCustomAdapter.CONTACT,null));

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
		fileList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsFile,ContactCustomAdapter.FILE,null));
		
		//apps should go here
		appList = (ExpandableListView)findViewById(R.id.app_list);
		Parent appParent = new Parent();
		ArrayList<Parent> arrayParentsApp = new ArrayList<Parent>();
		appParent.setTitle("Apps");
		ArrayList<String> arrayApps = new ArrayList<String>();
		for(int i=1;i<=3;i++) {
            arrayApps.add("App "+i);
        }
		appParent.setArrayChildren(arrayApps);
		arrayParentsApp.add(appParent);
		appList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsApp,ContactCustomAdapter.APP,null));
		
		// links should go here
		linkList = (ExpandableListView)findViewById(R.id.link_list);
		Parent linkParent = new Parent();
		ArrayList<Parent> arrayParentsLink = new ArrayList<Parent>();
		linkParent.setTitle("Links");
		ArrayList<String> arrayLinks = new ArrayList<String>();
		for(int i=1;i<=3;i++) {
            arrayLinks.add("google");
        }
		linkParent.setArrayChildren(arrayLinks);
		arrayParentsLink.add(linkParent);								// this null should be the links
		linkList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsLink,ContactCustomAdapter.LINK,null));
		
		//note list
		noteList = (ExpandableListView)findViewById(R.id.note_list);
		Parent noteParent = new Parent();
		ArrayList<Parent> arrayParentsNote = new ArrayList<Parent>();
		noteParent.setTitle("Note");
		ArrayList<String> arrayNotes = db.getNotes(event.getID());
		noteParent.setArrayChildren(arrayNotes);
		arrayParentsNote.add(noteParent);
		noteList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsNote,ContactCustomAdapter.NOTE,null));
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
