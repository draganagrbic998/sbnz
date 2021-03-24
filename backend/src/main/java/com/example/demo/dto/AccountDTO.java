package com.example.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.model.Account;

public class AccountDTO {
	
	private Long id;
	private LocalDate date;
	private double balance;
	private int billsCount;

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String jmbg;
	
	@NotNull
	private LocalDate birthDate;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String zipCode;
	
	@Email
	@NotBlank
	private String email;
			
	public AccountDTO() {
		super();
	}

	public AccountDTO(Account account) {
		super();
		this.id = account.getId();
		this.date = account.getDate();
		this.balance = account.getBalance();
		this.billsCount = account.getBills().size();
		this.firstName = account.getUser().getFirstName();
		this.lastName = account.getUser().getLastName();
		this.jmbg = account.getJmbg();
		this.birthDate = account.getBirthDate();
		this.address = account.getAddress();
		this.city = account.getCity();
		this.zipCode = account.getZipCode();
		this.email = account.getUser().getEmail();
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

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getBillsCount() {
		return billsCount;
	}

	public void setBillsCount(int billsCount) {
		this.billsCount = billsCount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
