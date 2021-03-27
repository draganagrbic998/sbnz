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
import com.example.demo.rules.RenewalResponse;
import com.example.demo.model.Account;
import com.example.demo.model.Bill;
import com.example.demo.model.BillType;
import com.example.demo.utils.Constants;

public class TestEKS {

	private static KieContainer kieContainer;
	private static KieSession kieSession;

	private Account account;
	private Bill bill;
	private RenewalResponse response;
	private int amount;
	
	@BeforeClass
	public static void beforeClass() {
		KieServices kieService = KieServices.Factory.get();
		kieContainer = kieService.newKieContainer(kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, "0.0.1-SNAPSHOT"));
		kieSession = kieContainer.newKieSession(Constants.RENEW_RULES);
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
		this.response = new RenewalResponse();
		this.account.getBills().add(this.bill);
		this.bill.setAccount(this.account);
	}

	public void runAndAssert(double interestUpdate) {
		kieSession.insert(this.bill);
		kieSession.insert(this.amount);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.RENEW_RULES).setFocus();
		kieSession.fireAllRules();
		
		assertTrue(this.response.isValid());
		assertNull(this.response.getMessage());
		assertEquals(this.response.getInterestUpdate(), Double.valueOf(interestUpdate));
	}
	
	@Test
	public void testRule1pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(200001);
		this.bill.setInterest(1);
		this.amount = 17;
		this.runAndAssert(0.025 * 1);
	}

	@Test
	public void testRule1pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(270001);
		this.bill.setInterest(1);
		this.amount = 16;
		this.runAndAssert(0.025 * 1);
	}

	@Test
	public void testRule1pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(2701);
		this.bill.setInterest(1);
		this.amount = 16;
		this.runAndAssert(0.025 * 1);
	}

	@Test
	public void testRule2pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(200001);
		this.bill.setInterest(1);
		this.bill.getRenewals().add(ObjectFactory.getRenewal(16));
		this.amount = 16;
		this.runAndAssert(0.025 * 1);
	}
	
	@Test
	public void testRule2pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(2001);
		this.bill.setInterest(1);
		this.bill.getRenewals().add(ObjectFactory.getRenewal(15));
		this.amount = 15;
		this.runAndAssert(0.025 * 1);
	}
	
	@Test
	public void testRule3pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(200001);
		this.bill.setInterest(1);
		this.amount = 14;
		this.runAndAssert(0.02 * 1);
	}

	@Test
	public void testRule3pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(220001);
		this.bill.setInterest(1);
		this.amount = 13;
		this.runAndAssert(0.02 * 1);
	}

	@Test
	public void testRule3pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(2201);
		this.bill.setInterest(1);
		this.amount = 13;
		this.runAndAssert(0.02 * 1);
	}

	@Test
	public void testRule4pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(200001);
		this.bill.setInterest(1);
		this.bill.getRenewals().add(ObjectFactory.getRenewal(13));
		this.amount = 13;
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule4pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(2001);
		this.bill.setInterest(1);
		this.bill.getRenewals().add(ObjectFactory.getRenewal(12));
		this.amount = 12;
		this.runAndAssert(0.02 * 1);
	}
	
	@Test
	public void testRule5pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(150001);
		this.bill.setInterest(1);
		this.amount = 10;
		this.runAndAssert(0.015 * 1);
	}

	@Test
	public void testRule5pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(170001);
		this.bill.setInterest(1);
		this.amount = 9;
		this.runAndAssert(0.015 * 1);
	}

	@Test
	public void testRule5pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(1701);
		this.bill.setInterest(1);
		this.amount = 9;
		this.runAndAssert(0.015 * 1);
	}

	@Test
	public void testRule6pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(150001);
		this.bill.setInterest(1);
		this.bill.getRenewals().add(ObjectFactory.getRenewal(9));
		this.amount = 9;
		this.runAndAssert(0.015 * 1);
	}
	
	@Test
	public void testRule6pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(1501);
		this.bill.setInterest(1);
		this.bill.getRenewals().add(ObjectFactory.getRenewal(8));
		this.amount = 8;
		this.runAndAssert(0.015 * 1);
	}
	
	@Test
	public void testRule7pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(75001);
		this.bill.setInterest(1);
		this.amount = 6;
		this.runAndAssert(0.01 * 1);
	}

	@Test
	public void testRule7pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(120001);
		this.bill.setInterest(1);
		this.amount = 5;
		this.runAndAssert(0.01 * 1);
	}

	@Test
	public void testRule7pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(1201);
		this.bill.setInterest(1);
		this.amount = 5;
		this.runAndAssert(0.01 * 1);
	}

	@Test
	public void testRule8pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(75001);
		this.bill.setInterest(1);
		this.bill.getRenewals().add(ObjectFactory.getRenewal(5));
		this.amount = 5;
		this.runAndAssert(0.01 * 1);
	}
	
	@Test
	public void testRule8pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(751);
		this.bill.setInterest(1);
		this.bill.getRenewals().add(ObjectFactory.getRenewal(4));
		this.amount = 4;
		this.runAndAssert(0.01 * 1);
	}
	
	@Test
	public void testRule9() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(75001);
		this.bill.setInterest(1);
		this.amount = 5;
		this.runAndAssert(0.005 * 1);
	}

}
