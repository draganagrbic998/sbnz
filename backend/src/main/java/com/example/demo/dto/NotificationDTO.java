package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.Notification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationDTO {

	private long id;
	private LocalDate date;
	private String message;

	public NotificationDTO(Notification notification) {
		super();
		this.id = notification.getId();
		this.date = notification.getDate();
		this.message = notification.getMessage();
	}

}
