package com.example.demo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transaction_table")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "type")
	private TransactionType type;
	
	@NotNull
	@Column(name = "date")
	private LocalDate date;

	@NotNull
	@Column(name = "amount")
	private double amount;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="bill_id")
	private Bill bill;

	public Transaction() {
		super();
		this.date = LocalDate.now();
		this.type = TransactionType.INCREASE;
	}

	public Transaction(Bill bill, double amount) {
		this();
		this.bill = bill;
		this.amount = amount;
	}

	public long passedMonths() {
		return Math.abs(ChronoUnit.MONTHS.between(this.date, LocalDate.now()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
}
