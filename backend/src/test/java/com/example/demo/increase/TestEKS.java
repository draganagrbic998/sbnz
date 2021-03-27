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
	private int amount;
	
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
		
		this.amount = 100000;
		this.account = new Account();
		
		this.bill = new Bill();
		this.bill.setType(BillType.RSD);
		this.bill.setBase(500000);
		this.bill.setBalance(500000);
		this.bill.setStartDate(LocalDate.now().minusMonths(21));
		this.bill.setEndDate(LocalDate.now().plusMonths(21));
		
		this.response = new IncreaseResponse();
		this.account.getBills().add(this.bill);
		this.bill.setAccount(this.account);
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
	public void testRule1Pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(100001);
		this.bill.setBase(99999);
		this.bill.setInterest(1);
		this.amount = 40000;
		this.runAndAssert(0.03 * 1);
	}

	@Test
	public void testRule1Pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(330001);
		this.bill.setBase(330001);
		this.bill.setInterest(1);
		this.amount = 35000;
		this.runAndAssert(0.03 * 1);
	}
	
	@Test
	public void testRule1Pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(3301);
		this.bill.setBase(3301);
		this.bill.setInterest(1);
		this.amount = 350;
		this.runAndAssert(0.03 * 1);
	}
	
	@Test
	public void testRule2Pt1() {
		this.account.setBalance(60000);

		this.bill.setBalance(330001);
		this.bill.setBase(329999);
		this.bill.setInterest(1);
		this.bill.getTransactions().add(ObjectFactory.getTransaction(35000, 8));
		this.bill.getTransactions().add(ObjectFactory.getTransaction(35000, 8));
		this.amount = 35000;
		this.runAndAssert(0.03 * 1);
	}
	
	@Test
	public void testRule2Pt2() {
		this.account.setBalance(80000);

		this.bill.setType(BillType.EUR);
		this.bill.setBalance(3301);
		this.bill.setBase(3299);
		this.bill.setInterest(1);
		this.bill.getTransactions().add(ObjectFactory.getTransaction(350, 8));
		this.bill.getTransactions().add(ObjectFactory.getTransaction(350, 8));
		this.amount = 350;
		this.runAndAssert(0.03 * 1);
	}
	
	@Test
	public void testRule3Pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(100001);
		this.bill.setBase(99999);
		this.bill.setInterest(1);
		this.amount = 33000;
		this.runAndAssert(0.025 * 1);
	}

	@Test
	public void testRule3Pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(270001);
		this.bill.setBase(270001);
		this.bill.setInterest(1);
		this.amount = 30000;
		this.runAndAssert(0.025 * 1);
	}
	
	@Test
	public void testRule3Pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(2701);
		this.bill.setBase(2701);
		this.bill.setInterest(1);
		this.amount = 300;
		this.runAndAssert(0.025 * 1);
	}
	
	@Test
	public void testRule4Pt1() {
		this.account.setBalance(60000);

		this.bill.setBalance(270001);
		this.bill.setBase(269999);
		this.bill.setInterest(1);
		this.bill.getTransactions().add(ObjectFactory.getTransaction(30000, 8));
		this.bill.getTransactions().add(ObjectFactory.getTransaction(30000, 8));
		this.amount = 30000;
		this.runAndAssert(0.025 * 1);
	}
	
	@Test
	public void testRule4Pt2() {
		this.account.setBalance(80000);

		this.bill.setType(BillType.EUR);
		this.bill.setBalance(2701);
		this.bill.setBase(2699);
		this.bill.setInterest(1);
		this.bill.getTransactions().add(ObjectFactory.getTransaction(300, 8));
		this.bill.getTransactions().add(ObjectFactory.getTransaction(300, 8));
		this.amount = 300;
		this.runAndAssert(0.025 * 1);
	}
	
	@Test
	public void testRule5Pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(100001);
		this.bill.setBase(99999);
		this.bill.setInterest(1);
		this.amount = 27000;
		this.runAndAssert(0.02 * 1);
	}

	@Test
	public void testRule5Pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(220001);
		this.bill.setBase(220001);
		this.bill.setInterest(1);
		this.amount = 24000;
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule5Pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(2201);
		this.bill.setBase(2201);
		this.bill.setInterest(1);
		this.amount = 240;
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule6Pt1() {
		this.account.setBalance(55000);

		this.bill.setBalance(220001);
		this.bill.setBase(219999);
		this.bill.setInterest(1);
		this.bill.getTransactions().add(ObjectFactory.getTransaction(24000, 8));
		this.bill.getTransactions().add(ObjectFactory.getTransaction(24000, 8));
		this.amount = 24000;
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule6Pt2() {
		this.account.setBalance(75000);

		this.bill.setType(BillType.EUR);
		this.bill.setBalance(2201);
		this.bill.setBase(2199);
		this.bill.setInterest(1);
		this.bill.getTransactions().add(ObjectFactory.getTransaction(240, 8));
		this.bill.getTransactions().add(ObjectFactory.getTransaction(240, 8));
		this.amount = 240;
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule7Pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(100001);
		this.bill.setBase(99999);
		this.bill.setInterest(1);
		this.amount = 22000;
		this.runAndAssert(0.015 * 1);
	}

	@Test
	public void testRule7Pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(180001);
		this.bill.setBase(180001);
		this.bill.setInterest(1);
		this.amount = 20000;
		this.runAndAssert(0.015 * 1);
	}
	
	@Test
	public void testRule7Pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(1801);
		this.bill.setBase(1801);
		this.bill.setInterest(1);
		this.amount = 200;
		this.runAndAssert(0.015 * 1);
	}
	
	@Test
	public void testRule8Pt1() {
		this.account.setBalance(55000);

		this.bill.setBalance(180001);
		this.bill.setBase(179999);
		this.bill.setInterest(1);
		this.bill.getTransactions().add(ObjectFactory.getTransaction(20000, 8));
		this.bill.getTransactions().add(ObjectFactory.getTransaction(20000, 8));
		this.amount = 20000;
		this.runAndAssert(0.015 * 1);
	}
	
	@Test
	public void testRule8Pt2() {
		this.account.setBalance(70000);

		this.bill.setType(BillType.EUR);
		this.bill.setBalance(1801);
		this.bill.setBase(1799);
		this.bill.setInterest(1);
		this.bill.getTransactions().add(ObjectFactory.getTransaction(200, 8));
		this.bill.getTransactions().add(ObjectFactory.getTransaction(200, 8));
		this.amount = 200;
		this.runAndAssert(0.015 * 1);
	}
	
	@Test
	public void testRule9() {
		this.bill.setBalance(180001);
		this.bill.setBase(179999);
		this.bill.setInterest(1);
		this.amount = 20000;
		this.runAndAssert(0.01 * 1);
	}
}
