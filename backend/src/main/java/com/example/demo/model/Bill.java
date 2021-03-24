package com.example.demo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bill_table")
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "status")
	private BillStatus status;

	@NotNull
	@Column(name = "type")
	private BillType type;
	
	@NotNull
	@Column(name = "start_date")
	private LocalDate startDate;

	@NotNull
	@Column(name = "end_date")
	private LocalDate endDate;

	@NotNull
	@Column(name = "base")
	private double base;

	@NotNull
	@Column(name = "interest")
	private double interest;

	@NotNull
	@Column(name = "balance")
	private double balance;

	@NotNull
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bill", cascade = CascadeType.REMOVE)
	private Set<Transaction> transactions = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bill", cascade = CascadeType.REMOVE)
	private Set<Renewal> renewals = new HashSet<>();

	public Bill() {
		super();
		this.status = BillStatus.ACTIVE;
		this.startDate = LocalDate.now();
	}
	
	public Bill(Account account, BillType type, int months, double base, double interest, double balance) {
		this();
		this.account = account;
		this.type = type;
		this.endDate = LocalDate.now().plusMonths(months);
		this.base = base;
		this.interest = interest;
		this.balance = balance;		
	}
	
	public double close() {
		this.status = BillStatus.ABORTED;
		this.endDate = LocalDate.now();
		return this.base;
	}

	public long months() {
		return Math.abs(ChronoUnit.MONTHS.between(this.startDate, this.endDate));
	}
	
	public long passedMonths() {
		return Math.abs(ChronoUnit.MONTHS.between(this.startDate, LocalDate.now()));
	}

	public double passedTime() {
		return Math.abs((double) ChronoUnit.DAYS.between(this.startDate, LocalDate.now()) / ChronoUnit.DAYS.between(this.startDate, this.endDate));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Set<Renewal> getRenewals() {
		return renewals;
	}

	public void setRenewals(Set<Renewal> renewals) {
		this.renewals = renewals;
	}
	
}
