package com.example.demo.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Expires("24h")
public class IncreaseBillEvent implements Serializable, Event {

	private Transaction transaction;

	@Override
	public Account getAccount() {
		return this.transaction.getBill().getAccount();
	}
	
}
