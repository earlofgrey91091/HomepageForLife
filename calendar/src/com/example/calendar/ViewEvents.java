package com.example.calendar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ViewEvents extends Activity {

	final static int VIEW_EVENT = 1;
	final static int EDIT_EVENT = 2;
	private ArrayList<CalendarEvent> event_list = new ArrayList<CalendarEvent>();
	private int num = 0;
	DbHandler db;
	
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
			case EDIT_EVENT: 
				if (resultCode == RESULT_OK) {
					CalendarEvent event = (CalendarEvent) data.getSerializableExtra("event");
					ArrayList<String> files = (ArrayList<String>) data.getStringArrayListExtra("files");
					ArrayList<String> apps = (ArrayList<String>) data.getStringArrayListExtra("apps");
					ArrayList<String> contacts = (ArrayList<String>) data.getStringArrayListExtra("contacts");
					String notes = (String) data.getStringExtra("notes");
					event_list = db.getAllEvents();
					event = db.getEvent(event.getDate());
					event_list.add(event);
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
					if(notes!="") db.addNote(event.getID(), notes);
					Toast.makeText(getApplicationContext(), 
							"event added", Toast.LENGTH_LONG).show();
					makeList();
			}	break;
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeList();
    }

	private void makeList() {
		db = new DbHandler(this);
        setContentView(R.layout.activity_view_events);
        event_list = db.getAllEvents();
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.view_layout);
        linearLayout.removeAllViews();
        for(int i=0; i<event_list.size(); i++) {
        	final Button btn = new Button(this);
        	btn.setText(event_list.get(i).getDate()+" - "+event_list.get(i).getName());
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	Button b = (Button)v;
                    String buttonText = b.getText().toString();
                    int dash = buttonText.indexOf(" - ");
                    buttonText = buttonText.substring(0, dash);
                    Intent intent = new Intent(ViewEvents.this, EventDetails.class);
            		intent.putExtra("Date", buttonText);
            		startActivity(intent);
                }
            });
            linearLayout.addView(btn); 
        }
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_events, menu);
        return true;
    }
    
    public void newEvent(View view) {
    	Intent intent = new Intent(this, NewEvent.class);
	    startActivityForResult(intent,EDIT_EVENT);
    }
}
