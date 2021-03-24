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
@Table(name = "renewal_table")
public class Renewal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "date")
	private LocalDate date;

	@NotNull
	@Column(name = "amount")
	private int amount;

	@NotNull
	@ManyToOne
	@JoinColumn(name="bill_id")
	private Bill bill;

	public Renewal() {
		super();
		this.date = LocalDate.now();
	}
	
	public Renewal(Bill bill, int amount) {
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
}
