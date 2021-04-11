package com.example.demo.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.example.demo.model.Account;
import com.example.demo.model.Renewal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Expires("24h")
public class RenewBillEvent implements Serializable, Event {

	private Renewal renewal;

	@Override
	public Account getAccount() {
		return this.renewal.getBill().getAccount();
	}

}
