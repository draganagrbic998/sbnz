package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('KLIJENT')")	
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationMapper notificationMapper;

	@GetMapping
	public ResponseEntity<List<NotificationDTO>> findAll(Pageable pageable, HttpServletResponse response){
		Page<Notification> notifications = this.notificationService.findAll(pageable);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE_HEADER + ", " + Constants.LAST_PAGE_HEADER);
		response.setHeader(Constants.FIRST_PAGE_HEADER, notifications.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE_HEADER, notifications.isLast() + "");
		return new ResponseEntity<>(this.notificationMapper.map(notifications.toList()), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		this.notificationService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
