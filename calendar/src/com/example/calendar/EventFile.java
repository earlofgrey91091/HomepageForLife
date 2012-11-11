package com.example.calendar;

import java.io.Serializable;

public class EventFile implements Serializable{
	
	String name;
	String path;
	
	public EventFile () {
		this(null,null);
	}
	
	public EventFile (String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
}
