package com.example.calendar;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class OpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Events";
    private static final String DATABASE_NAME = "Events";
    private static final String date = "Date";
    private static final String name = "Name";
    private static final String location = "Location";
    
    
    
    //This is the commandline to be used in the lower call of execSQL(). This is where the schema for the db is determined
    private static final String DICTIONARY_TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                date + " TEXT, " +
                name + " TEXT, " +
                location + " TEXT);";
    //Proposed alternative would be just having event_id as the key and and event object as the second column

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}