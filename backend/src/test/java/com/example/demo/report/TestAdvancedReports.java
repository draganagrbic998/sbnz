package com.example.demo.report;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.example.demo.ObjectFactory;
import com.example.demo.model.Account;
import com.example.demo.model.Bill;
import com.example.demo.model.BillType;
import com.example.demo.utils.Constants;

public class TestAdvancedReports {

	private static KieContainer kieContainer;
	private static KieSession kieSession;

	private List<Account> accounts = new ArrayList<>();
	
	@BeforeClass
	public static void beforeClass() {
		KieServices kieService = KieServices.Factory.get();
		kieContainer = kieService.newKieContainer(kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, "0.0.1-SNAPSHOT"));
		kieSession = kieContainer.newKieSession(Constants.REPORT_RULES);
	}
	
	@AfterClass
	public static void afterClass() {
		kieSession.dispose();
		kieSession.destroy();		
	}
	
	@Before
	public void before() {
		for (FactHandle fh: kieSession.getFactHandles()) {
			kieSession.delete(fh);
		}
		this.accounts.clear();
	}

	@Test
	public void testReport1() {
		Account account1 = new Account();
		Account account2 = new Account();
		this.accounts.add(account1);
		this.accounts.add(account2);
		
		account1.setId(1l);
		account1.setDate(LocalDate.now().minusYears(5).minusDays(1));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
		account1.getBills().add(ObjectFactory.getBill(910000));
				
		kieSession.insert(this.accounts);
		kieSession.getAgenda().getAgendaGroup(Constants.FIRST_REPORT).setFocus();
		kieSession.fireAllRules();

		assertEquals(this.accounts.size(), 1);
		assertEquals(this.accounts.get(0).getId(), Long.valueOf(1));
	}

	@Test
	public void testReport2() {
		Account account1 = new Account();
		Account account2 = new Account();
		this.accounts.add(account1);
		this.accounts.add(account2);

		account1.setId(1l);
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));
		account1.getBills().add(ObjectFactory.getBill(BillType.EUR, 9100, 9));		
		
		kieSession.insert(this.accounts);
		kieSession.getAgenda().getAgendaGroup(Constants.SECOND_REPORT).setFocus();
		kieSession.fireAllRules();
		
		assertEquals(this.accounts.size(), 1);
		assertEquals(this.accounts.get(0).getId(), Long.valueOf(1));
	}
	
	@Test
	public void testReport3() {
		Account account1 = new Account();
		Account account2 = new Account();
		this.accounts.add(account1);
		this.accounts.add(account2);

		account1.setId(1l);
		Bill bill = ObjectFactory.getBill(BillType.RSD);
		account1.getBills().add(bill);
		bill.getTransactions().add(ObjectFactory.getTransaction(bill, 500000));
		bill.getTransactions().add(ObjectFactory.getTransaction(bill, 500000));
		bill.getTransactions().add(ObjectFactory.getTransaction(bill, 500001));
		bill.getRenewals().add(ObjectFactory.getRenewal(6));
		bill.getRenewals().add(ObjectFactory.getRenewal(6));
		bill.getRenewals().add(ObjectFactory.getRenewal(6));
		bill.getRenewals().add(ObjectFactory.getRenewal(6));
		bill.getRenewals().add(ObjectFactory.getRenewal(7));
		
		kieSession.insert(this.accounts);
		kieSession.getAgenda().getAgendaGroup(Constants.THIRD_REPORT).setFocus();
		kieSession.fireAllRules();
		
		assertEquals(this.accounts.size(), 1);
		assertEquals(this.accounts.get(0).getId(), Long.valueOf(1));
	}

}
