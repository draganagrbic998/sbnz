package com.example.demo.dto.rules;

public class CloseResponse {

	private boolean valid;
	private String message;
	
	public CloseResponse() {
		super();
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
