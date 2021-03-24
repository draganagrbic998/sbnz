package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.Renewal;

public class RenewalDTO {

	private long id;
	private LocalDate date;
	private int amount;
	
	public RenewalDTO() {
		super();
	}

	public RenewalDTO(Renewal renewal) {
		super();
		this.id = renewal.getId();
		this.date = renewal.getDate();
		this.amount = renewal.getAmount();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	
}
