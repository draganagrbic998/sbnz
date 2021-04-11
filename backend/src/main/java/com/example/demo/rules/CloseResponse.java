package com.example.demo.rules;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CloseResponse {

	private boolean valid;
	private String message;
	
}
