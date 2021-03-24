package com.example.demo.rules;

public class IncreaseResponse {
	
	private boolean valid;
	private String message;
	private Double baseUpdate;
	private Double interestUpdate;
	
	public IncreaseResponse() {
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

	public Double getBaseUpdate() {
		return baseUpdate;
	}

	public void setBaseUpdate(Double baseUpdate) {
		this.baseUpdate = baseUpdate;
	}

	public Double getInterestUpdate() {
		return interestUpdate;
	}

	public void setInterestUpdate(Double interestUpdate) {
		this.interestUpdate = interestUpdate;
	}
	
}
