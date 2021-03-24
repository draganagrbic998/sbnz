package com.example.demo.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;

@SuppressWarnings("serial")
@Role(Role.Type.EVENT)
@Expires("24h")
public class IncreaseBillEvent implements Serializable, Event {

	private Transaction transaction;

	public IncreaseBillEvent() {
		super();
	}

	public IncreaseBillEvent(Transaction transaction) {
		super();
		this.transaction = transaction;
	}

	@Override
	public Account getAccount() {
		return this.transaction.getBill().getAccount();
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
}
