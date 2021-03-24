package com.example.demo.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.example.demo.model.Account;
import com.example.demo.model.Bill;

@SuppressWarnings("serial")
@Role(Role.Type.EVENT)
@Expires("24h")
public class CloseBillEvent implements Serializable, Event {

	private Bill bill;

	public CloseBillEvent() {
		super();
	}

	public CloseBillEvent(Bill bill) {
		super();
		this.bill = bill;
	}

	@Override
	public Account getAccount() {
		return this.bill.getAccount();
	}
	
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
}
