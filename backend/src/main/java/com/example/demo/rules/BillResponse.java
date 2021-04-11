package com.example.demo.rules;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillResponse {

	private boolean valid;
	private String message;
	private Double nks;
	private Integer points;
	private Double eks;
	private Integer reward;
	
}
