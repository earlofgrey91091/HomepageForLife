package com.example.calendar;

import java.io.Serializable;

public class CalendarEvent implements Serializable{
	public String date;
	public String name;
	public String location;
	
	public CalendarEvent(String date) {
		this(date,"","");
	}
	
	public CalendarEvent(String date, String name) {
		this(date,name,"");
	}
	
	public CalendarEvent(String date, String name, String location) {
		this.date = date;
		this.name = name;
		this.location = location;
	}
}
