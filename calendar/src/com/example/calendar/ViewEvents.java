package com.example.calendar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class ViewEvents extends Activity {

	private ArrayList<CalendarEvent> event_list = new ArrayList<CalendarEvent>();
	private int num = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        Intent intent = getIntent();
        event_list = (ArrayList<CalendarEvent>)intent.getSerializableExtra("events");
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.view_layout);
        linearLayout.removeAllViews();
        for(int i=0; i<event_list.size(); i++) {
        	Button btn = new Button(this); 
            btn.setText(event_list.get(i).name);
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
