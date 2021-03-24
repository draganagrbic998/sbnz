package com.example.demo.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@SuppressWarnings("serial")
@Role(Role.Type.EVENT)
@Expires("72h")
public class SuspiciousClientEvent implements Serializable {
	
}
