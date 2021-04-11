package com.example.demo.rules;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RenewalResponse {
	
	private boolean valid;
	private String message;
	private Double interestUpdate;

}
