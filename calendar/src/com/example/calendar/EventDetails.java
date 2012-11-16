package com.example.calendar;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetails extends Activity {
	DbHandler db;
	CalendarEvent event;
	ArrayList<String> arrayContacts;
	ArrayList<String> arrayFiles;
	ArrayList<String> arrayApps;
	ArrayList<String> arrayLinks;
	ArrayList<String> arrayNotes;
	private ArrayList<CalendarEvent> event_list = new ArrayList<CalendarEvent>();
	private final static int EDIT_EVENT=1;
	
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
		event = db.getEvent(data.getIntExtra("ID", -1));
		Log.d("EVENTDETAILS", "event.returned ID is " + event.getID());
		

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
		ArrayList<String> arrayContactNames = new ArrayList<String>();
		arrayContacts = db.getContacts(event.getID());
		Log.d("EVENTDETAILS", "arrayContacts.returned size is " + arrayContacts.size());
		for (int i = 0; i<arrayContacts.size(); i++)
		{
			Log.d("EVENTDETAILS", "added contact " + arrayContacts.get(i));
			String[] projection = {Phone.DISPLAY_NAME };
			Cursor cursor = getContentResolver().query(Uri.parse(arrayContacts.get(i)),
					projection, null, null, null);
			cursor.moveToFirst();

			int column = cursor.getColumnIndex(Phone.DISPLAY_NAME);
			arrayContactNames.add(cursor.getString(column));
		}
		contactParent.setArrayChildren(arrayContactNames);
		arrayParentsContact.add(contactParent);
		contactList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsContact,ContactCustomAdapter.CONTACT,arrayContacts));

		//file list
		fileList = (ExpandableListView)findViewById(R.id.file_list);
		Parent fileParent = new Parent();
		ArrayList<Parent> arrayParentsFile = new ArrayList<Parent>();
		fileParent.setTitle("Files");
		arrayFiles = db.getFiles(event.getID());
		Log.d("EVENTDETAILS", "arrayFiles.returned size is " + arrayFiles.size());
		ArrayList<String> arrayListNames = new ArrayList<String>();
		for(int i = 0; i<arrayFiles.size(); i++) {
			Log.d("EVENTDETAILS", "added files " + arrayFiles.get(i));
			int name_loc = arrayFiles.get(i).lastIndexOf("/");
			arrayListNames.add(arrayFiles.get(i).substring(name_loc + 1));
		}
		Log.d("EVENTDETAILS", "for file arrayListNames.returned size is " + arrayListNames.size());

		fileParent.setArrayChildren(arrayListNames);
		arrayParentsFile.add(fileParent);
		fileList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsFile,ContactCustomAdapter.FILE,arrayFiles));



	
		//apps should go here
		appList = (ExpandableListView)findViewById(R.id.app_list);
		Parent appParent = new Parent();
		ArrayList<Parent> arrayParentsApp = new ArrayList<Parent>();
		appParent.setTitle("Apps");

		arrayApps = db.getApps(event.getID());
		Log.d("EVENTDETAILS", "arrayApps.returned size is " + arrayApps.size());

		appParent.setArrayChildren(arrayApps);
		arrayParentsApp.add(appParent);
		appList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsApp,ContactCustomAdapter.APP,arrayApps));
		// to run app, given the name

		//final PackageManager pm = getPackageManager();
		//startActivity(pm.getLaunchIntentForPackage(arrayApps.get(0)));

		// links should go here
		linkList = (ExpandableListView)findViewById(R.id.link_list);
		Parent linkParent = new Parent();
		ArrayList<Parent> arrayParentsLink = new ArrayList<Parent>();
		linkParent.setTitle("Links");
		
		ArrayList<String> arrayLinkNames = new ArrayList<String>();
		ArrayList<String> arrayLinkURLs = new ArrayList<String>();
		arrayLinks = db.getLinks(event.getID());
		Log.d("EVENTDETAILS", "arraylinks.returned size is " + arrayLinks.size());

		for (int i = 0; i<arrayLinks.size(); i++)
		{
			StringTokenizer stk = new StringTokenizer(arrayLinks.get(i), "\n");
			arrayLinkURLs.add(stk.nextToken());
			arrayLinkNames.add(stk.nextToken());
		}
		
		linkParent.setArrayChildren(arrayLinkNames);
		arrayParentsLink.add(linkParent);
		linkList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsLink,ContactCustomAdapter.LINK,arrayLinkURLs));
		
		//note list
		noteList = (ExpandableListView)findViewById(R.id.note_list);
		Parent noteParent = new Parent();
		ArrayList<Parent> arrayParentsNote = new ArrayList<Parent>();
		noteParent.setTitle("Note");

		arrayNotes = db.getNotes(event.getID());
		Log.d("EVENTDETAILS", "arraynotes.returned size is " + arrayNotes.size());

		noteParent.setArrayChildren(arrayNotes);
		arrayParentsNote.add(noteParent);
		noteList.setAdapter(new ContactCustomAdapter(EventDetails.this,arrayParentsNote,ContactCustomAdapter.NOTE,null));
		//notes.setText("Notes: \n" + event.getNotes());
		 
		}
	@Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			CalendarEvent event = (CalendarEvent)data.getSerializableExtra("Event");
			ArrayList<String> files = (ArrayList<String>) data.getStringArrayListExtra("files");
			ArrayList<String> apps = (ArrayList<String>) data.getStringArrayListExtra("apps");
			ArrayList<String> contacts = (ArrayList<String>) data.getStringArrayListExtra("contacts");
			ArrayList<String> links = (ArrayList<String>) data.getStringArrayListExtra("links");
			String notes = (String) data.getStringExtra("notes");
			for(String theFile : files)
			{
				db.addFile(event.getID(), theFile);
			}
			for(String theApp : apps)
			{
				db.addApp(event.getID(), theApp);
			}
			for(String theContact : contacts)
			{
				db.addContact(event.getID(), theContact);
			}
			for(String theLink : links)
			{
				StringTokenizer st = new StringTokenizer(theLink, "\n");
				db.addLink(event.getID(), st.nextToken(), st.nextToken());
			}
			if(!notes.equals("")) db.addNote(event.getID(), notes);
			Toast.makeText(getApplicationContext(), 
					"Event added", Toast.LENGTH_LONG).show();
			db.updateEvent(event);
			
			removeEvent(findViewById(android.R.id.content));
			//This should delete the old version of the event you just edited and return you to calendar
		}
		
	}
	
	public void removeEvent(View view) {
		for(String contact: arrayContacts)
		{
			db.deleteContact(event.getID(), contact);
		}
		for(String link: arrayLinks)
		{
			StringTokenizer st = new StringTokenizer(link, "\n");
			db.deleteLink(event.getID(), st.nextToken());
		}
		for(String note: arrayNotes)
		{
			db.deleteNote(event.getID(), note);
		}
		for(String file: arrayFiles)
		{
			db.deleteFile(event.getID(), file);
		}
		for(String app: arrayApps)
		{
			db.deleteApp(event.getID(), app);
		}
		
		db.deleteEvent(event);
		finish();
		Intent intent = new Intent(this, Calendar.class);
		startActivity(intent);
    }
	
	public void editEvent(View view){
		Log.d("EventDetails", "You pushed edit");
		Intent i = new Intent(this, EditEvent.class);
		i.putExtra("ID", event.getID());
	    startActivityForResult(i, EDIT_EVENT);
	}
	
}
