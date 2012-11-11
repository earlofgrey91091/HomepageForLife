package com.example.calendar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewEvent extends Activity {

	final static int ADD_FILE = 1;
	ArrayList<EventFile> files = new ArrayList<EventFile>();
	DbHandler db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		db = new DbHandler(this);
		Intent intent = getIntent();
		String year = intent.getStringExtra("year");
		String month = intent.getStringExtra("month");
		String day = intent.getStringExtra("day");
		String date = month + "/" + day + "/" + year;
		if (year != null || month != null || day != null) {
			EditText date_field = (EditText) findViewById(R.id.date_message);
			date_field.setText(date);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_new_event, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ADD_FILE:
			if (resultCode == RESULT_OK) {
				LinearLayout linearLayout = (LinearLayout)findViewById(R.id.file_layout);
				EventFile f = (EventFile) data.getSerializableExtra("file");
				files.add(f);
				TextView name = new TextView(this);
				name.setText(f.getName());
				linearLayout.addView(name);
			}
			break;
		}
	}

	public void addFile(View view) {
		Intent intent = new Intent(this, AndroidExplorer.class);
		startActivityForResult(intent, ADD_FILE);
	}

	public void add(View view) {
		EditText date = (EditText) findViewById(R.id.date_message);
		String dateMessage = date.getText().toString();
		EditText name = (EditText) findViewById(R.id.name_message);
		String nameMessage = name.getText().toString();
		EditText location = (EditText) findViewById(R.id.location_message);
		String locationMessage = location.getText().toString();
		CalendarEvent newEvent = new CalendarEvent(dateMessage, nameMessage,
				locationMessage,files);
		db.addEvent(newEvent);
		Intent i = new Intent();
		i.putExtra("event", newEvent);
		setResult(RESULT_OK, i);
		finish();
	}
}
