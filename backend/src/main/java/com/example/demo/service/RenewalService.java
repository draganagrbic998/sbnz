package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Renewal;
import com.example.demo.repository.RenewalRepository;

@Service
@Transactional(readOnly = true)
public class RenewalService {

	@Autowired
	private RenewalRepository renewalRepository;
	
	@Transactional(readOnly = false)
	public Renewal save(Renewal renewal) {
		return this.renewalRepository.save(renewal);
	}
	
}
