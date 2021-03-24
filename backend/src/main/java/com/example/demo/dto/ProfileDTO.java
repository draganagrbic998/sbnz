package com.example.demo.dto;

import com.example.demo.model.User;

public class ProfileDTO {

	private long id;
	private String token;
	private String role;
	private String email;
	private String firstName;
	private String lastName;
	
	public ProfileDTO() {
		super();
	}

	public ProfileDTO(User user, String token) {
		super();
		this.id = user.getId();
		this.token = token;
		this.role = user.getAuthority().getName();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
