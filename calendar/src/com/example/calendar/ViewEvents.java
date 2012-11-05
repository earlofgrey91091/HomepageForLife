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
					event_list = db.getAllEvents();
					event_list.add(db.getEvent(event.getDate()));
					Toast.makeText(getApplicationContext(), 
							"event added", Toast.LENGTH_LONG).show();
				} break;
			case VIEW_EVENT: 
				if (resultCode == RESULT_OK) {
					CalendarEvent event = (CalendarEvent) data.getSerializableExtra("event");
					event_list = db.getAllEvents();
					event_list.add(db.getEvent(event.getDate()));
					Toast.makeText(getApplicationContext(), 
							"event added", Toast.LENGTH_LONG).show();
						
			}	break;
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHandler(this);
        setContentView(R.layout.activity_view_events);
        event_list = db.getAllEvents();
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.view_layout);
        linearLayout.removeAllViews();
        for(int i=0; i<event_list.size(); i++) {
        	Button btn = new Button(this); 
            btn.setText(event_list.get(i).getName());
            linearLayout.addView(btn); 
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_events, menu);
        return true;
    }
    
    public void add(View view) {
    	LinearLayout linearLayout = (LinearLayout)findViewById(R.id.view_layout);
        Button btn = new Button(this); 
        String name = "Event";
        if(num!=0)
        	name+=" "+num;
        btn.setText(name);
        num++;
        linearLayout.addView(btn); 
    }
}
