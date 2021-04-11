package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordChangeDTO {
	
	@NotBlank(message = "Old password cannot be blank")
	private String oldPassword;
	
	@NotBlank(message = "New password cannot be blank")
	private String newPassword;

}
