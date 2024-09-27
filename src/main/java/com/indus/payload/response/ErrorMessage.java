package com.indus.payload.response;


public class ErrorMessage {
	
	private String dateTime;
	private String message;
	private int status;
	private String code;
	
	public ErrorMessage() {
		
	}
	
	
	public ErrorMessage(String dateTime, String message, int status, String code) {
		super();
		this.dateTime = dateTime;
		this.message = message;
		this.status = status;
		this.code = code;
	}
	
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
