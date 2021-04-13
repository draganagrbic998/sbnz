package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.events.Event;
import com.example.demo.model.Account;
import com.example.demo.utils.Constants;

@Service
public class EventService {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private KieContainer kieContainer;
	
	private Map<Long, KieSession> kieSessions = new HashMap<>();
	
	public void addEvent(Event event) {
		long key = event.getAccount().getId();
		if (!this.kieSessions.containsKey(key)) {
			this.initSession(event.getAccount());
		}
		this.kieSessions.get(key).insert(event);
	}
	
	private void initSession(Account account) {
		KieSession kieSession = this.kieContainer.newKieSession(Constants.EVENT_RULES_REALTIME);
		kieSession.setGlobal("notificationService", this.notificationService);
		kieSession.setGlobal("account", account);
        new Thread(() -> kieSession.fireUntilHalt()).start();
        this.kieSessions.put(account.getId(), kieSession);
	}
	
}
