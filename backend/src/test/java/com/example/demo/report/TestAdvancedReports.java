package com.example.demo.report;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.example.demo.ObjectFactory;
import com.example.demo.model.Account;
import com.example.demo.model.Bill;
import com.example.demo.model.BillType;
import com.example.demo.utils.Constants;

public class TestAdvancedReports {

	private KieSession kieSession;
	private List<Account> accounts;
		
	@Before
	public void before() {
		KieServices kieService = KieServices.Factory.get();
		KieContainer kieContainer = kieService.newKieContainer(kieService
				.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, Constants.KNOWLEDGE_VERSION));
		this.kieSession = kieContainer.newKieSession(Constants.REPORT_RULES);

		this.accounts = new ArrayList<>();
	}

	@After
	public void after() {
		this.kieSession.dispose();
		this.kieSession.destroy();		
	}

	@Test
	public void testReport1() {
		this.kieSession.getAgenda().getAgendaGroup(Constants.FIRST_REPORT).setFocus();

		Account account = new Account();
		account.setId(1l);
		account.setDate(LocalDate.now().minusYears(5).minusDays(1));
		account.setBills(Set.of(
			ObjectFactory.getBill(910000), ObjectFactory.getBill(910000), ObjectFactory.getBill(910000), 
			ObjectFactory.getBill(910000), ObjectFactory.getBill(910000), ObjectFactory.getBill(910000), 
			ObjectFactory.getBill(910000), ObjectFactory.getBill(910000), ObjectFactory.getBill(910000), 
			ObjectFactory.getBill(910000), ObjectFactory.getBill(910000)
		));
				
		this.accounts.addAll(List.of(account, new Account()));
		this.kieSession.insert(this.accounts);
		this.kieSession.fireAllRules();

		assertEquals(this.accounts.size(), 1);
		assertEquals(this.accounts.get(0).getId(), Long.valueOf(1));
	}

	@Test
	public void testReport2() {
		this.kieSession.getAgenda().getAgendaGroup(Constants.SECOND_REPORT).setFocus();
		
		Account account = new Account();
		account.setId(1l);
		account.setBills(Set.of(
			ObjectFactory.getBill(BillType.EUR, 9100, 9), ObjectFactory.getBill(BillType.EUR, 9100, 9), ObjectFactory.getBill(BillType.EUR, 9100, 9), 
			ObjectFactory.getBill(BillType.EUR, 9100, 9), ObjectFactory.getBill(BillType.EUR, 9100, 9), ObjectFactory.getBill(BillType.EUR, 9100, 9), 
			ObjectFactory.getBill(BillType.EUR, 9100, 9), ObjectFactory.getBill(BillType.EUR, 9100, 9), ObjectFactory.getBill(BillType.EUR, 9100, 9), 
			ObjectFactory.getBill(BillType.EUR, 9100, 9), ObjectFactory.getBill(BillType.EUR, 9100, 9)
		));
		
		this.accounts.addAll(List.of(account, new Account()));
		this.kieSession.insert(this.accounts);
		this.kieSession.fireAllRules();
		
		assertEquals(this.accounts.size(), 1);
		assertEquals(this.accounts.get(0).getId(), Long.valueOf(1));
	}
	
	@Test
	public void testReport3() {
		this.kieSession.getAgenda().getAgendaGroup(Constants.THIRD_REPORT).setFocus();
		
		Account account = new Account();
		account.setId(1l);
		Bill bill = ObjectFactory.getBill(BillType.RSD);
		account.setBills(Set.of(bill));
		
		bill.setTransactions(Set.of(
			ObjectFactory.getTransaction(bill, 500000), ObjectFactory.getTransaction(bill, 500000),
			ObjectFactory.getTransaction(bill, 500001)
		));
		bill.setRenewals(Set.of(
			ObjectFactory.getRenewal(6), ObjectFactory.getRenewal(6), ObjectFactory.getRenewal(6), 
			ObjectFactory.getRenewal(6), ObjectFactory.getRenewal(7)
		));
		
		this.accounts.addAll(List.of(account, new Account()));
		this.kieSession.insert(this.accounts);
		this.kieSession.fireAllRules();
		
		assertEquals(this.accounts.size(), 1);
		assertEquals(this.accounts.get(0).getId(), Long.valueOf(1));
	}

}
