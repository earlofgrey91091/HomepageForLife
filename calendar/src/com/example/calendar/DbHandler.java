package com.example.calendar;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 6;
	
	// Database Name
	private static final String DATABASE_NAME = "HPL";
	
	// Table Name(s)
	//Event table
	private static final String EVENT_TABLE = "Events";
	private static final String KEY_ID = "Id";
	private static final String KEY_DATE = "Date";
	private static final String KEY_NAME = "Name";
	private static final String KEY_LOC = "Location";
	//Contact table
	private static final String CONTACT_TABLE= "Contacts";
	private static final String KEY_EVENT_ID= "EventId";
	private static final String KEY_CONTACT_VALUE= "ContactValue";
	//File table
	private static final String FILE_TABLE= "Files";
	private static final String KEY_FILE= "File";
	//Links table
	private static final String LINK_TABLE= "Links";
	private static final String KEY_LINK_NAME= "Link_Name";
	private static final String KEY_LINK_URL= "Link_url";
	//Notes
	private static final String NOTE_TABLE="Notes";
	private static final String KEY_NOTE= "Note";
	//App table
	private static final String APP_TABLE="Apps";
	private static final String KEY_APP_NAME= "AppName";
	
	// This is the commandline to be used in the lower call of execSQL(). This
	// is where the schema for the db is determined

	// Proposed alternative would be just having event_id as the key and and
	// event object as the second column
	public DbHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String DICTIONARY_TABLE_CREATE = "CREATE TABLE "
				+ EVENT_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DATE + " TEXT, " + KEY_NAME + " TEXT, "
				+ KEY_LOC +" TEXT);";
		String CONTACT_TABLE_CREATE= "CREATE TABLE "
				+ CONTACT_TABLE + " (" + KEY_EVENT_ID + " INTEGER, " + KEY_CONTACT_VALUE + " TEXT);";
		String FILE_TABLE_CREATE= "CREATE TABLE "
				+ FILE_TABLE + " (" + KEY_EVENT_ID + " INTEGER, " + KEY_FILE + " TEXT);";
		String LINK_TABLE_CREATE= "CREATE TABLE "
				+ LINK_TABLE + " (" + KEY_EVENT_ID + " INTEGER, " + KEY_LINK_URL + " TEXT, " + KEY_LINK_NAME + " TEXT);";
		String NOTE_TABLE_CREATE= "CREATE TABLE "
				+ NOTE_TABLE + " (" + KEY_EVENT_ID + " INTEGER, " + KEY_NOTE + " TEXT);";
		String APP_TABLE_CREATE= "CREATE TABLE "
				+ APP_TABLE + " (" + KEY_EVENT_ID + " INTEGER, " + KEY_APP_NAME + " TEXT);";
		db.execSQL(DICTIONARY_TABLE_CREATE);
		db.execSQL(CONTACT_TABLE_CREATE);
		db.execSQL(FILE_TABLE_CREATE);
		db.execSQL(LINK_TABLE_CREATE);
		db.execSQL(NOTE_TABLE_CREATE);
		db.execSQL(APP_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + FILE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + LINK_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE);
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new Event
	public int addEvent(CalendarEvent event) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_DATE, event.getDate());
		cv.put(KEY_NAME, event.getName());
		cv.put(KEY_LOC, event.getLocation());
		
		// Inserting Row
		int rowId = (int)db.insert(EVENT_TABLE, null, cv);
		db.close(); // Closing database connection
		return rowId;
	}
	

	
	CalendarEvent getEvent(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Log.d("Dbhandler", "attempting to get event");
		Cursor cur = db.query(EVENT_TABLE, new String[] { KEY_ID, KEY_DATE,
				KEY_NAME, KEY_LOC }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cur != null)
			cur.moveToFirst();

		CalendarEvent event = new CalendarEvent(cur.getInt(0),
				cur.getString(1), cur.getString(2), cur.getString(3));
		Log.d("Dbhandler", "got event and returning");
		db.close();
		// return event
		return event;
	}
	
	// Getting All Contacts
	public ArrayList<CalendarEvent> getAllEvents() {
		ArrayList<CalendarEvent> eventList = new ArrayList<CalendarEvent>();
		
		// Select All Query
		String selectQuery = "SELECT  * FROM " + EVENT_TABLE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				CalendarEvent e = new CalendarEvent();
				e.setID(cursor.getInt(0));
				e.setDate(cursor.getString(1));
				e.setName(cursor.getString(2));
				e.setLocation(cursor.getString(3));
				// Adding contact to list
				eventList.add(e);
			} while (cursor.moveToNext());
		}

		db.close();
		// return contact list
		return eventList;
	}

	// Updating single Event
	public int updateEvent(CalendarEvent event) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, event.getID());
		values.put(KEY_NAME, event.getName());
		values.put(KEY_DATE, event.getDate());
		values.put(KEY_LOC, event.getLocation());

		// updating row
		int rowid= db.update(EVENT_TABLE, values, KEY_ID + " = ?",
				new String[] {  String.valueOf(event.getID()) });
		db.close();
		return rowid;
	}

	// Deleting single event
	public void deleteEvent(CalendarEvent event) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(EVENT_TABLE, KEY_ID + " = ?",
				new String[] { String.valueOf(event.getID()) });
		db.close();
	}


	// Getting Event Count
	public int getEventsCount() {
		String countQuery = "SELECT  * FROM " + EVENT_TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		db.close();
		// return count
		return cursor.getCount();
	}

	public void addContact(int eventId, String theContact) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_EVENT_ID, eventId);
		cv.put(KEY_CONTACT_VALUE, theContact);		
		// Inserting Row
		db.insert(CONTACT_TABLE, null, cv);
		db.close(); // Closing database connection
	}

	// Getting single Event
	ArrayList<String> getContacts(int eventId) 
	{
		
		ArrayList<String> contactList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(CONTACT_TABLE, new String[]{KEY_EVENT_ID, KEY_CONTACT_VALUE}, KEY_EVENT_ID + " = ?", 
				new String[]{String.valueOf(eventId)}, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			//Log.d("DbHandler", "event is id " + String.valueOf(eventId));
			do {
				//Log.d("DbHandler", "output at row is " + cursor.getString(0) +" and " + cursor.getString(1));
				contactList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		return contactList;
	}

	// Deleting single Contact
	public void deleteContact(int eventId, String theContact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(CONTACT_TABLE, KEY_EVENT_ID + " = ? AND " + KEY_CONTACT_VALUE + " = ?",
				new String[] { String.valueOf(eventId), theContact });
		Log.d("DbHandler", "deleted a contact");
		db.close();
	}
	
	public void addFile(int eventId, String theFile) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_EVENT_ID, eventId);
		cv.put(KEY_FILE, theFile);		
		// Inserting Row
		db.insert(FILE_TABLE, null, cv);
		db.close(); // Closing database connection
	}

	// Getting single Event
	ArrayList<String> getFiles(int eventId) 
	{
		
		ArrayList<String> fileList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(FILE_TABLE, new String[]{KEY_EVENT_ID, KEY_FILE}, KEY_EVENT_ID + " = ?", 
				new String[]{String.valueOf(eventId)}, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			//Log.d("DbHandler", "event is id " + String.valueOf(eventId));
			do {
				Log.d("DbHandler", "output at row is " + cursor.getString(0) +" and " + cursor.getString(1));
				fileList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		return fileList;
	}

	// Deleting single file
	public void deleteFile(int eventId, String theFile) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(FILE_TABLE, KEY_EVENT_ID + " = ? AND " + KEY_FILE + " = ?",
				new String[] { String.valueOf(eventId), theFile});
		Log.d("DbHandler", "deleted a file");
		db.close();
	}
	
	public void addApp(int eventId, String theApp) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_EVENT_ID, eventId);
		cv.put(KEY_APP_NAME, theApp);		
		// Inserting Row
		db.insert(APP_TABLE, null, cv);
		db.close(); // Closing database connection
	}

	// Getting all apps
	ArrayList<String> getApps(int eventId) 
	{
		
		ArrayList<String> appList = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(APP_TABLE, new String[]{KEY_EVENT_ID, KEY_APP_NAME}, KEY_EVENT_ID + " = ?", 
				new String[]{String.valueOf(eventId)}, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			//Log.d("DbHandler", "event is id " + String.valueOf(eventId));
			do {
				//Log.d("DbHandler", "output at row is " + cursor.getString(0) +" and " + cursor.getString(1));
				appList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		return appList;
	}

	// Deleting single app
	public void deleteApp(int eventId, String theApp) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(APP_TABLE, KEY_EVENT_ID + " = ? AND " + KEY_APP_NAME + " = ?",
				new String[] { String.valueOf(eventId), theApp});
		Log.d("DbHandler", "deleted an app");
		db.close();
	}
	public void addNote(int eventId, String theNote) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_EVENT_ID, eventId);
		cv.put(KEY_NOTE, theNote);		
		// Inserting Row
		db.insert(NOTE_TABLE, null, cv);
		db.close(); // Closing database connection
	}

	// Getting all notes
	ArrayList<String> getNotes(int eventId) 
	{
		
		ArrayList<String> noteList = new ArrayList<String>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(NOTE_TABLE, new String[]{KEY_EVENT_ID, KEY_NOTE}, KEY_EVENT_ID + " = ?", 
					new String[]{String.valueOf(eventId)}, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			//Log.d("DbHandler", "event is id " + String.valueOf(eventId));
			do {
				//Log.d("DbHandler", "output at row is " + cursor.getString(0) +" and " + cursor.getString(1));
				noteList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		return noteList;
	}

	// Deleting single note
	public void deleteNote(int eventId, String theNote) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(NOTE_TABLE, KEY_EVENT_ID + " = ? AND " + KEY_NOTE + " = ?",
				new String[] { String.valueOf(eventId), theNote});
		Log.d("DbHandler", "deleted a note");
		db.close();
	}
	
	//Add a link to the event
	public void addLink(int eventId, String theLink, String theName) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_EVENT_ID, eventId);
		cv.put(KEY_LINK_URL, theLink);
		cv.put(KEY_LINK_NAME, theName);
		// Inserting Row
		db.insert(LINK_TABLE, null, cv);
		db.close(); // Closing database connection
	}

	// Getting all links for an event
	ArrayList<String> getLinks(int eventId) 
	{
		ArrayList<String> linkList = new ArrayList<String>();
		// Select All Query
		//String selectQuery = "SELECT * FROM " + LINK_TABLE + " WHERE " + KEY_EVENT_ID + " = " + eventId;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(LINK_TABLE, new String[]{KEY_EVENT_ID, KEY_LINK_URL, KEY_LINK_NAME}, KEY_EVENT_ID + " = ?", 
					new String[]{String.valueOf(eventId)}, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			Log.d("DbHandler", "event is id " + String.valueOf(eventId));

			do {
				Log.d("DbHandler", "link output at row is " + cursor.getString(0) +" and " + cursor.getString(1));
				linkList.add(cursor.getString(1) + "\n" + cursor.getString(2));
			} while (cursor.moveToNext());
		}

		db.close(); // Closing database connection
		// return link list
		return linkList;

	}

	// Deleting single note
	public void deleteLink(int eventId, String theLink) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(LINK_TABLE, KEY_EVENT_ID + " = ? AND " + KEY_LINK_URL + " = ?",
				new String[] { String.valueOf(eventId), theLink});
		Log.d("DbHandler", "deleted a link");
		db.close();
	}
	
	
	
}
