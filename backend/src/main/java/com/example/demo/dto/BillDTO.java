package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.Bill;
import com.example.demo.model.BillStatus;
import com.example.demo.model.BillType;

public class BillDTO {

	private long id;
	private BillStatus status;
	private BillType type;
	private LocalDate startDate;
	private LocalDate endDate;
	private double base;
	private double interest;
	private double balance;
	private List<TransactionDTO> transactions;
	private List<RenewalDTO> renewals;
	
	public BillDTO() {
		super();
	}
	
	public BillDTO(Bill bill) {
		super();
		this.id = bill.getId();
		this.status = bill.getStatus();
		this.type = bill.getType();
		this.startDate = bill.getStartDate();
		this.endDate = bill.getEndDate();
		this.base = bill.getBase();
		this.interest = bill.getInterest();
		this.balance = bill.getBalance();
		this.transactions = bill.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toList());
		this.renewals = bill.getRenewals().stream().map(RenewalDTO::new).collect(Collectors.toList());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}

	public BillType getType() {
		return type;
	}

	public void setType(BillType type) {
		this.type = type;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getBase() {
		return base;
	}

	public void setBase(double base) {
		this.base = base;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

	public List<RenewalDTO> getRenewals() {
		return renewals;
	}

	public void setRenewals(List<RenewalDTO> renewals) {
		this.renewals = renewals;
	}
	
}
