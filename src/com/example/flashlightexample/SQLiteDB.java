package com.example.flashlightexample;

public class SQLiteDB {
private int _id;
private String dateTime;
private String action;
private String content;
private String serialNumber;
public SQLiteDB() {
	// TODO Auto-generated constructor stub
}
public SQLiteDB(int _id, String dateTime, String action, String content,
		String serialNumber) {
	super();
	this._id = _id;
	this.dateTime = dateTime;
	this.action = action;
	this.content = content;
	this.serialNumber = serialNumber;
}
public int get_id() {
	return _id;
}
public void set_id(int _id) {
	this._id = _id;
}
public String getDateTime() {
	return dateTime;
}
public void setDateTime(String dateTime) {
	this.dateTime = dateTime;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getSerialNumber() {
	return serialNumber;
}
public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
}


}
