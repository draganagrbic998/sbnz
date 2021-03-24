package com.example.demo.dto.rules;

import com.example.demo.model.BillType;
import com.sun.istack.NotNull;

public class BillRequest {
	
	@NotNull
	private BillType type;
	private double base;
	private int months;
	
	public BillRequest() {
		super();
	}

	public BillType getType() {
		return type;
	}

	public void setType(BillType type) {
		this.type = type;
	}

	public double getBase() {
		return base;
	}

	public void setBase(double base) {
		this.base = base;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

}
