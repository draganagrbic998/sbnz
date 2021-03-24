package com.example.demo.rules;

public class BillResponse {

	private boolean valid;
	private String message;
	private Double nks;
	private Integer points;
	private Double eks;
	private Integer reward;
	
	public BillResponse() {
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

	public Double getNks() {
		return nks;
	}

	public void setNks(Double nks) {
		this.nks = nks;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Double getEks() {
		return eks;
	}

	public void setEks(Double eks) {
		this.eks = eks;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}
	
}
