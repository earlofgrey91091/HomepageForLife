package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String TABLE_NAME = "Events";
	private static final String DATABASE_NAME = "Events";
	private static final String date = "Date";
	private static final String name = "Name";
	private static final String location = "Location";

	// This is the commandline to be used in the lower call of execSQL(). This
	// is where the schema for the db is determined
	private static final String DICTIONARY_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_NAME + " (" + date + " TEXT, " + name + " TEXT, "
			+ location + " TEXT);";

	// Proposed alternative would be just having event_id as the key and and
	// event object as the second column

	public OpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DICTIONARY_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

		onCreate(db);
	}

	// Should be able to make an equivalent addEvent that gets passed an event
	// object once i have access
	public void addEvent(String newDate, String newName, String newLocation) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(date, newDate);
		cv.put(name, newName);
		cv.put(location, newLocation);

		db.insert(TABLE_NAME, null, cv);
	}

	Cursor viewEvent(String eventDate) {// returns a cursor containing the query
										// for events on eventDate
		SQLiteDatabase db = getReadableDatabase();

		Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " WHERE  date='" + eventDate + "'", null);
		return cur;
	}

	Cursor allEvents() {// returns all events in calendar
		SQLiteDatabase db = getReadableDatabase();

		Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		return cur;
	}

}