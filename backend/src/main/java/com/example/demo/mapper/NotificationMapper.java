package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.model.Notification;

@Component
public class NotificationMapper {

	public List<NotificationDTO> map(List<Notification> notifications){
		return notifications.stream().map(NotificationDTO::new).collect(Collectors.toList());
	}
	
}
