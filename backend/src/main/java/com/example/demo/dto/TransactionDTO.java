package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

	private long id;
	private TransactionType type;
	private LocalDate date;
	private double amount;

	public TransactionDTO(Transaction transaction) {
		super();
		this.id = transaction.getId();
		this.type = transaction.getType();
		this.date = transaction.getDate();
		this.amount = transaction.getAmount();
	}

}
