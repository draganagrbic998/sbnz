package com.example.demo.rules;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IncreaseResponse {
	
	private boolean valid;
	private String message;
	private Double baseUpdate;
	private Double interestUpdate;

}
