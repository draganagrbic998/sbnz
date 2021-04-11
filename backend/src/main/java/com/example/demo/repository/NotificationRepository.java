package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	public Page<Notification> findByAccountIsNull(Pageable pageable);
	public Page<Notification> findByAccountId(Pageable pageable, long id);
	
}
