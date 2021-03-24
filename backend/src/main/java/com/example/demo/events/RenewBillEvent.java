package com.example.demo.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.example.demo.model.Account;
import com.example.demo.model.Renewal;

@SuppressWarnings("serial")
@Role(Role.Type.EVENT)
@Expires("24h")
public class RenewBillEvent implements Serializable, Event {

	private Renewal renewal;

	public RenewBillEvent() {
		super();
	}

	public RenewBillEvent(Renewal renewal) {
		super();
		this.renewal = renewal;
	}

	@Override
	public Account getAccount() {
		return this.renewal.getBill().getAccount();
	}

	public Renewal getRenewal() {
		return renewal;
	}

	public void setRenewal(Renewal renewal) {
		this.renewal = renewal;
	}
	
}
