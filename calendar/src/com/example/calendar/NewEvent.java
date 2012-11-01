package com.example.calendar;

import android.app.Activity;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewEvent extends Activity {

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
    	String message = date.getText().toString();
    	Intent i = new Intent();
    	i.putExtra("date", message);
    	setResult(1, i);
    	finish();
    }
}
