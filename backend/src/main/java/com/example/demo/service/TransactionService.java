package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;

@Service
@Transactional(readOnly = true)
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Transactional(readOnly = false)
	public Transaction save(Transaction transaction) {
		return this.transactionRepository.save(transaction);
	}
	
}
