package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Notification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationDTO {

	private long id;
	private Date date;
	private String message;

	public NotificationDTO(Notification notification) {
		super();
		this.id = notification.getId();
		this.date = notification.getDate();
		this.message = notification.getMessage();
	}

}
