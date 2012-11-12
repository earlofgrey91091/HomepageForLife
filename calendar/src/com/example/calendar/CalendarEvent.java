package com.example.calendar;

import java.io.Serializable;
import java.util.ArrayList;

public class CalendarEvent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2638026792002779950L;
	int _id;
	String _date;
	String _name;
	String _location;
	ArrayList<EventFile> _files;
	String _notes;
	
	public CalendarEvent() {
		this(-1,"","","",new ArrayList<EventFile>());
	}
	public CalendarEvent(String date) {
		this(-1, date,"","",new ArrayList<EventFile>());
	}
	
	public CalendarEvent(String date, String name) {
		this(-1, date,name,"",new ArrayList<EventFile>());
	}
	
	public CalendarEvent(String date, String name, String location) {
		this(-1, date,name,location,new ArrayList<EventFile>());
	}
	
	public CalendarEvent(int id, String date, String name, String location) {
		this(id,date,name,location,new ArrayList<EventFile>());
	}
	
	public CalendarEvent(String date, String name, String location, ArrayList<EventFile> files) {
		this(-1, date,name,location,files);
	}
	
	public CalendarEvent(int id, String date, String name, String location, ArrayList<EventFile> files) {
		this._id = id;
		this._date = date;
		this._name = name;
		this._location = location;
		_files = files;
	}
	
	public CalendarEvent(int id, String date, String name, String location, ArrayList<EventFile> files, String notes) {
		this._id = id;
		this._date = date;
		this._name = name;
		this._location = location;
		this._files = files;
		this._notes = notes;
	}
	
	public CalendarEvent(String date, String name, String location, ArrayList<EventFile> files, String notes) {
		this(-1,date,name,location,files,notes);
	}
	
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting location
	public String getDate(){
		return this._date;
	}
	
	// setting location
	public void setDate(String date){
		this._date = date;
	}

	// getting location
	public String getLocation(){
		return this._location;
	}
	
	// setting location
	public void setLocation(String location){
		this._location = location;
	}
	
	public ArrayList<EventFile> getFiles() {
		return _files;
	}
	
	public void setFiles(ArrayList<EventFile> files) {
		_files = files;
	}
	
	public void setNotes(String notes) {
		_notes = notes;
	}
	
	public String getNotes() {
		return _notes;
	}
}
