package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

public class PasswordChangeDTO {
	
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
	public PasswordChangeDTO() {
		super();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
