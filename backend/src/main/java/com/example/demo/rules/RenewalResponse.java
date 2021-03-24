package com.example.demo.rules;

public class RenewalResponse {
	
	private boolean valid;
	private String message;
	private Double interestUpdate;
	
	public RenewalResponse() {
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

	public Double getInterestUpdate() {
		return interestUpdate;
	}

	public void setInterestUpdate(Double interestUpdate) {
		this.interestUpdate = interestUpdate;
	}

}
