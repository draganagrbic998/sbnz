package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;

@Service
@Transactional(readOnly = true)
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
		
	public Page<Notification> findAll(Pageable pageable) {
		return this.notificationRepository.findAll(pageable);
	}

	@Transactional(readOnly = false)
	public void save(Notification notification) {
		this.notificationRepository.save(notification);
	}
	
	@Transactional(readOnly = false)
	public void delete(long id) {
		this.notificationRepository.deleteById(id);
	}
	
}
