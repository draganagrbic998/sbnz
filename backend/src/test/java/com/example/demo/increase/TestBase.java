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

public class TestBase {

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

	public void runAndAssert(double baseUpdate) {
		kieSession.insert(this.bill);
		kieSession.insert(this.amount);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.INCREASE_RULES).setFocus();
		kieSession.fireAllRules();
		
		assertTrue(this.response.isValid());
		assertNull(this.response.getMessage());
		assertEquals(this.response.getBaseUpdate(), Double.valueOf(baseUpdate));
		assertNotNull(this.response.getInterestUpdate());
	}
	
	@Test
	public void testRule1pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.amount = 75001 * 0.4 + 1;
		this.runAndAssert(0.9 * this.amount);		
	}
	
	@Test
	public void testRule1pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(300001);
		this.bill.setBalance(300001);
		this.amount = 0.1 * 300001 + 1;
		this.runAndAssert(0.9 * this.amount);
	}

	@Test
	public void testRule1pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(3001);
		this.bill.setBalance(3001);
		this.amount = 0.1 * 3001 + 1;
		this.runAndAssert(0.9 * this.amount);
	}
	
	@Test
	public void testRule2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.amount = 0.1 * 75001 + 1;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.5 * 75001 + 1));
		this.runAndAssert(0.9 * this.amount);
	}
	
	@Test
	public void testRule3pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.amount = 75001 * 0.35 + 1;
		this.runAndAssert(0.8 * this.amount);		
	}
	
	@Test
	public void testRule3pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(250000);
		this.bill.setBalance(250001);
		this.amount = 0.1 * 250001 + 1;
		this.runAndAssert(0.8 * this.amount);		
	}

	@Test
	public void testRule3pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(2501);
		this.bill.setBalance(2501);
		this.amount = 0.1 * 2501 + 1;
		this.runAndAssert(0.8 * this.amount);		
	}
	
	@Test
	public void testRule4() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.amount = 0.1 * 75001 + 1;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.45 * 75001 + 1));
		this.runAndAssert(0.8 * this.amount);		
	}
	
	@Test
	public void testRule5pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.amount = 75001 * 0.3 + 1;
		this.runAndAssert(0.7 * this.amount);		
	}
	
	@Test
	public void testRule5pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(200001);
		this.bill.setBalance(200001);
		this.amount = 0.1 * 200001 + 1;
		this.runAndAssert(0.7 * this.amount);		
	}

	@Test
	public void testRule5pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(2001);
		this.bill.setBalance(2001);
		this.amount = 0.1 * 2001 + 1;
		this.runAndAssert(0.7 * this.amount);		
	}
	
	@Test
	public void testRule6() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.amount = 0.1 * 75001 + 1;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.4 * 75001 + 1));
		this.runAndAssert(0.7 * this.amount);		
	}
	
	@Test
	public void testRule7pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.amount = 75001 * 0.25 + 1;
		this.runAndAssert(0.6 * this.amount);		
	}
	
	@Test
	public void testRule7pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(150001);
		this.bill.setBalance(150001);
		this.amount = 0.1 * 150001 + 1;
		this.runAndAssert(0.6 * this.amount);		
	}

	@Test
	public void testRule7pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(1501);
		this.bill.setBalance(1501);
		this.amount = 0.1 * 1501 + 1;
		this.runAndAssert(0.6 * this.amount);		
	}
	
	@Test
	public void testRule8() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(75001);
		this.amount = 0.1 * 75001 + 1;
		this.bill.getTransactions().add(ObjectFactory.getTransaction(0.35 * 75001 + 1));
		this.runAndAssert(0.6 * this.amount);		
	}
	
	@Test
	public void testRule9() {
		this.bill.setType(BillType.RSD);
		this.bill.setBase(75001);
		this.bill.setBalance(100);
		this.amount = 100 * 0.2;
		this.runAndAssert(0.5 * this.amount);
	}
	
}
