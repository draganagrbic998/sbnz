package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import com.example.demo.model.User;

public class UserDTO {
	
	private Long id;
	
	@NotBlank
	private String role;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(User user) {
		super();
		this.id = user.getId();
		this.role = user.getAuthority().getName();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
}
