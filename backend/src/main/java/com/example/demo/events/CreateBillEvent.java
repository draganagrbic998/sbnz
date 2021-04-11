package com.example.demo.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.example.demo.model.Account;
import com.example.demo.model.Bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Expires("24h")
public class CreateBillEvent implements Serializable, Event {

	private Bill bill;

	@Override
	public Account getAccount() {
		return this.bill.getAccount();
	}
	
}
