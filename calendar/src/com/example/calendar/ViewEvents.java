package com.example.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ViewEvents extends Activity {

	private int num = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
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
