package com.example.calendar;

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

	private SharedPreferences sharedPref;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        setContentView(R.layout.activity_calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calendar, menu);
        return true;
	}

	public void newEvent(View view) {
		Intent intent = new Intent(this, NewEvent.class);
		startActivity(intent);
		String date = intent.getStringExtra("date");
		Button bt = (Button)findViewById(R.id.new_event);
		bt.setText(date);
		Toast msg = Toast.makeText(Calendar.this, date, Toast.LENGTH_LONG);
		msg.show();
	}
	
	public void viewEvents(View view) {
		Intent intent = new Intent(this, ViewEvents.class);
		startActivity(intent);
	}
}
