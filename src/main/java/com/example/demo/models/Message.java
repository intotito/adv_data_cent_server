package com.example.demo.models;

public class Message {
	private String message;
	private String timestamp;
	private String path;
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
}
