package com.example.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewEvent extends Activity {

	OpenHelper dbHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_event, menu);
        return true;
    }
    
    public void add(View view) {
    	EditText date = (EditText)findViewById(R.id.date_message);
    	String dateMessage = date.getText().toString();
    	EditText name = (EditText)findViewById(R.id.name_message);
    	String nameMessage = name.getText().toString();
    	EditText location = (EditText)findViewById(R.id.location_message);
    	String locationMessage = location.getText().toString();
    	CalendarEvent newEvent = new CalendarEvent(dateMessage,nameMessage,locationMessage);
    	//dbHelper.addEvent(dateMessage,nameMessage,locationMessage);
    	Intent i = new Intent();
    	i.putExtra("event", newEvent);
    	setResult(RESULT_OK, i);
    	finish();
    }
}
