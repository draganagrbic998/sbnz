package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.Notification;

public class NotificationDTO {

	private long id;
	private LocalDate date;
	private String message;
	
	public NotificationDTO() {
		super();
	}

	public NotificationDTO(Notification notification) {
		super();
		this.id = notification.getId();
		this.date = notification.getDate();
		this.message = notification.getMessage();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
