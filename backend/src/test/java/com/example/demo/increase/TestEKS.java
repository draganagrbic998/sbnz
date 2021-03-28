package com.example.demo.increase;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.example.demo.ObjectFactory;
import com.example.demo.rules.IncreaseResponse;
import com.example.demo.model.Account;
import com.example.demo.model.Bill;
import com.example.demo.model.BillType;
import com.example.demo.utils.Constants;

public class TestEKS {

	private static KieContainer kieContainer;
	private static KieSession kieSession;

	private Account account;
	private Bill bill;
	private IncreaseResponse response;
	private double amount;
	
	@BeforeClass
	public static void beforeClass() {
		KieServices kieService = KieServices.Factory.get();
		kieContainer = kieService.newKieContainer(kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, "0.0.1-SNAPSHOT"));
		kieSession = kieContainer.newKieSession(Constants.INCREASE_RULES);
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
		
		this.account = new Account();
		this.bill = new Bill();
		this.response = new IncreaseResponse();
		this.account.getBills().add(this.bill);
		this.bill.setAccount(this.account);
		this.bill.setStartDate(LocalDate.now().minusMonths(3));
		this.bill.setEndDate(LocalDate.now().plusMonths(3).plusDays(1));
	}

	public void runAndAssert(double interestUpdate) {
		kieSession.insert(this.bill);
		kieSession.insert(this.amount);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.INCREASE_RULES).setFocus();
		kieSession.fireAllRules();
		
		assertTrue(this.response.isValid());
		assertNull(this.response.getMessage());
		assertNotNull(this.response.getBaseUpdate());
		assertEquals(this.response.getInterestUpdate(), Double.valueOf(interestUpdate));
	}

	@Test
	public void testRule1pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.bill.setInterest(1);
		this.amount = 0.35 * 75001 + 1;
		this.runAndAssert(0.03 * 1);
	}
	
	@Test
	public void testRule1pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(330001);
		this.bill.setBalance(330001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 330001;
		this.runAndAssert(0.03 * 1);
	}

	@Test
	public void testRule1pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(3301);
		this.bill.setBalance(3301);
		this.bill.setInterest(1);
		this.amount = 0.2 * 3301;
		this.runAndAssert(0.03 * 1);
	}
	
	@Test
	public void testRule2pt1() {
		this.account.setBalance(100);
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 75001;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.5 * 100 + 1));
		this.runAndAssert(0.03 * 1);
	}

	@Test
	public void testRule2pt2() {
		this.account.setBalance(100);
		this.bill.setType(BillType.EUR);
		this.bill.setBase(751);
		this.bill.setBalance(751);
		this.bill.setInterest(1);
		this.amount = 0.2 * 751;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.45 * 100 + 1));
		this.runAndAssert(0.03 * 1);
	}

	@Test
	public void testRule3pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.bill.setInterest(1);
		this.amount = 0.3 * 75001 + 1;
		this.runAndAssert(0.025 * 1);
	}
	
	@Test
	public void testRule3pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(270001);
		this.bill.setBalance(270001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 270001;
		this.runAndAssert(0.025 * 1);
	}

	@Test
	public void testRule3pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(2701);
		this.bill.setBalance(2701);
		this.bill.setInterest(1);
		this.amount = 0.2 * 2701;
		this.runAndAssert(0.025 * 1);
	}

	@Test
	public void testRule4pt1() {
		this.account.setBalance(100);
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 75001;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.45 * 100 + 1));
		this.runAndAssert(0.025 * 1);
	}

	@Test
	public void testRule4pt2() {
		this.account.setBalance(100);
		this.bill.setType(BillType.EUR);
		this.bill.setBase(751);
		this.bill.setBalance(751);
		this.bill.setInterest(1);
		this.amount = 0.2 * 751;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.4 * 100 + 1));
		this.runAndAssert(0.025 * 1);
	}

	@Test
	public void testRule5pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.bill.setInterest(1);
		this.amount = 0.25 * 75001 + 1;
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule5pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(220001);
		this.bill.setBalance(220001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 220001;
		this.runAndAssert(0.02 * 1);
	}

	@Test
	public void testRule5pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(2201);
		this.bill.setBalance(2201);
		this.bill.setInterest(1);
		this.amount = 0.2 * 2201;
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule6pt1() {
		this.account.setBalance(100);
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 75001;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.4 * 100 + 1));
		this.runAndAssert(0.02 * 1);
	}

	@Test
	public void testRule6pt2() {
		this.account.setBalance(100);
		this.bill.setType(BillType.EUR);
		this.bill.setBase(751);
		this.bill.setBalance(751);
		this.bill.setInterest(1);
		this.amount = 0.2 * 751;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.35 * 100 + 1));
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule7pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 75001 + 1;
		this.runAndAssert(0.015 * 1);
	}
	
	@Test
	public void testRule7pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(180001);
		this.bill.setBalance(180001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 180001;
		this.runAndAssert(0.015 * 1);
	}

	@Test
	public void testRule7pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(1801);
		this.bill.setBalance(1801);
		this.bill.setInterest(1);
		this.amount = 0.2 * 1801;
		this.runAndAssert(0.015 * 1);
	}

	@Test
	public void testRule8pt1() {
		this.account.setBalance(100);
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.bill.setInterest(1);
		this.amount = 0.2 * 75001;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.35 * 100 + 1));
		this.runAndAssert(0.015 * 1);
	}

	@Test
	public void testRule8pt2() {
		this.account.setBalance(100);
		this.bill.setType(BillType.EUR);
		this.bill.setBase(751);
		this.bill.setBalance(751);
		this.bill.setInterest(1);
		this.amount = 0.2 * 751;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.3 * 100 + 1));
		this.runAndAssert(0.015 * 1);
	}
	
	@Test
	public void testRule9() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setInterest(1);
		this.runAndAssert(0.01 * 1);
	}

}
