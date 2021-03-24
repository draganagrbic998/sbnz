package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionType;

public class TransactionDTO {

	private long id;
	private TransactionType type;
	private LocalDate date;
	private double amount;
	
	public TransactionDTO() {
		super();
	}

	public TransactionDTO(Transaction transaction) {
		super();
		this.id = transaction.getId();
		this.type = transaction.getType();
		this.date = transaction.getDate();
		this.amount = transaction.getAmount();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
