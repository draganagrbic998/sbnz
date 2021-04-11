package com.example.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.model.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {
	
	private Long id;
	private LocalDate date;
	private double balance;
	private int billsCount;

	@NotNull(message = "Birth date cannot be null")
	private LocalDate birthDate;

	@NotBlank(message = "JMBG cannot be blank")
	@Size(min = 13, max = 13, message = "JMBG must have 13 characters")
	private String jmbg;

	@Email(message = "Email must be valid")
	@NotBlank(message = "Email cannot be blank")
	private String email;

	@NotBlank(message = "First name cannot be blank")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be blank")
	private String lastName;
			
	@NotBlank(message = "Address cannot be blank")
	private String address;
	
	@NotBlank(message = "City cannot be blank")
	private String city;
	
	@NotBlank(message = "Zip code cannot be blank")
	private String zipCode;

	public AccountDTO(Account account) {
		super();
		this.id = account.getId();
		this.date = account.getDate();
		this.balance = account.getBalance();
		this.billsCount = account.getBills().size();
		this.birthDate = account.getBirthDate();
		this.jmbg = account.getJmbg();
		this.email = account.getUser().getEmail();
		this.firstName = account.getUser().getFirstName();
		this.lastName = account.getUser().getLastName();
		this.address = account.getAddress();
		this.city = account.getCity();
		this.zipCode = account.getZipCode();
	}

}
