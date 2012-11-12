package com.example.calendar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewEvent extends Activity {

	final static int ADD_FILE = 3;
	final static int ADD_APP = 4;

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
			
			case ADD_APP:
				if (resultCode == RESULT_OK) {
					/*LinearLayout linearLayout = (LinearLayout)findViewById(R.id.file_layout);
					EventFile f = (EventFile) data.getSerializableExtra("file");
					files.add(f);
					TextView name = new TextView(this);
					name.setText(f.getName());
					linearLayout.addView(name);*/
				}
				break;
		}
	}

	public void addFile(View view) {
		Intent intent = new Intent(this, AndroidExplorer.class);
		startActivityForResult(intent, ADD_FILE);
	}
	
	public void addApp(View view)
	{
		//shamelessly stolen from http://stackoverflow.com/questions/2695746/how-to-get-a-list-of-installed-android-applications-and-pick-one-to-run
		
		/*final PackageManager pm = getPackageManager();
		//get a list of installed apps.
        List<ApplicationInfo> packages = pm
                .getInstalledApplications(PackageManager.GET_META_DATA);
    	List<CharSequence> packagenames = new ArrayList<CharSequence>();

        for (ApplicationInfo packageInfo : packages) {
        	packagenames.add(packageInfo.packageName);
            //pm.getLaunchIntentForPackage(packageInfo.packageName)); 
        }// the getLaunchIntentForPackage returns an intent that you can use with startActivity()
        //should print out name of app
        final CharSequence [] items = packagenames.toArray(new CharSequence[packagenames.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options for App choice");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
            }
        }).show();
        
        
   	 Attach a button to call this and it will show any app that has a ACTION_MAIN
   	 So it shows any app*/
      	Intent intent = new Intent (Intent.ACTION_MAIN);
      	//	title is set as "Choose app"
      	String title = "Choose an App";
      	Intent chooser = Intent.createChooser(intent, title);
      	
      	PackageManager packageManager = getPackageManager();
      	List<ResolveInfo> activities = packageManager.queryIntentActivities(chooser, 0);
      	boolean isIntentSafe = activities.size() > 0;
      	  
      	// Start an activity if it's safe
      	if (isIntentSafe) {
      	    startActivity(chooser);
      	}
   	 
        
	}
	private boolean isSystemPackage(ResolveInfo ri){
	    return (ri.activityInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)!=0;

	}

	public void add(View view) {
		EditText date = (EditText) findViewById(R.id.date_message);
		String dateMessage = date.getText().toString();
		EditText name = (EditText) findViewById(R.id.name_message);
		String nameMessage = name.getText().toString();
		EditText location = (EditText) findViewById(R.id.location_message);
		String locationMessage = location.getText().toString();
		EditText notes = (EditText) findViewById(R.id.note_message);
		String notesMessage = notes.getText().toString();
		CalendarEvent newEvent = new CalendarEvent(dateMessage, nameMessage,
				locationMessage,files,notesMessage);
		db.addEvent(newEvent);
		Intent i = new Intent();
		i.putExtra("event", newEvent);
		setResult(RESULT_OK, i);
		finish();
	}
}
