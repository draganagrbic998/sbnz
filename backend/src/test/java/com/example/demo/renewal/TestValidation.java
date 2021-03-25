package com.example.demo.renewal;

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

import com.example.demo.rules.RenewalResponse;
import com.example.demo.model.Account;
import com.example.demo.model.Bill;
import com.example.demo.model.BillType;
import com.example.demo.model.Renewal;
import com.example.demo.utils.Constants;

public class TestValidation {

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

	public void runAndAssert(String message) {
		kieSession.insert(this.bill);
		kieSession.insert(this.amount);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.RENEW_RULES).setFocus();
		kieSession.fireAllRules();
		
		assertFalse(this.response.isValid());
		assertEquals(this.response.getMessage(), message);
		assertNull(this.response.getInterestUpdate());
	}
	
	@Test
	public void testRule1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now());
		this.amount = 2;
		this.runAndAssert("You can't renew bill for less than 3 months.");
	}

	@Test
	public void testRule2() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now());
		this.amount = 25;
		this.runAndAssert("You can't renew bill for more than 24 months.");
	}

	@Test
	public void testRule3() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(4));
		this.amount = 12;
		this.runAndAssert("You can't renew RSD bill with duration of 4 months.");
	}
	
	@Test
	public void testRule4() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(42));
		this.amount = 12;
		this.runAndAssert("You can't renew RSD bill with duration of at least 42 months.");
	}

	@Test
	public void testRule5() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(74999);
		this.amount = 12;
		this.runAndAssert("You can't renew RSD bill with base less than 75.000 RSD.");
	}
	
	@Test
	public void testRule6() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(2));
		this.amount = 12;
		this.runAndAssert("You can't renew foreign bill with duration of 2 months.");
	}
	
	@Test
	public void testRule7() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(36));
		this.amount = 12;
		this.runAndAssert("You can't renew foreign bill with duration of at least 36 months.");
	}

	@Test
	public void testRule8() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(749);
		this.amount = 12;
		this.runAndAssert("You can't renew foreign bill with base less than 750 currency units.");
	}
	
	@Test
	public void testRule9pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(249999);
		this.amount = 19;
		this.runAndAssert("You can't renew bill for more than 18 months.");
	}
	
	@Test
	public void testRule9pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(2499);
		this.amount = 19;
		this.runAndAssert("You can't renew bill for more than 18 months.");
	}

	@Test
	public void testRule10() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(250001);
		this.bill.getRenewals().add(new Renewal());
		this.amount = 19;
		this.runAndAssert("You can't renew bill for more than 18 months.");
	}

	@Test
	public void testRule11pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(199999);
		this.amount = 13;
		this.runAndAssert("You can't renew bill for more than 12 months.");
	}
	
	@Test
	public void testRule11pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(1999);
		this.amount = 13;
		this.runAndAssert("You can't renew bill for more than 12 months.");
	}

	@Test
	public void testRule12() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(200001);
		this.bill.getRenewals().add(new Renewal());
		this.bill.getRenewals().add(new Renewal());
		this.amount = 13;
		this.runAndAssert("You can't renew bill for more than 12 months.");
	}
	
	@Test
	public void testRule13pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(149999);
		this.amount = 10;
		this.runAndAssert("You can't renew bill for more than 9 months.");
	}
	
	@Test
	public void testRule13pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(1499);
		this.amount = 10;
		this.runAndAssert("You can't renew bill for more than 9 months.");
	}

	@Test
	public void testRule14() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(150001);
		this.bill.getRenewals().add(new Renewal());
		this.bill.getRenewals().add(new Renewal());
		this.bill.getRenewals().add(new Renewal());
		this.amount = 10;
		this.runAndAssert("You can't renew bill for more than 9 months.");
	}
	
	@Test
	public void testRule15pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(99999);
		this.amount = 7;
		this.runAndAssert("You can't renew bill for more than 6 months.");
	}
	
	@Test
	public void testRule15pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(19));
		this.bill.setBase(999);
		this.amount = 7;
		this.runAndAssert("You can't renew bill for more than 6 months.");
	}

	@Test
	public void testRule16() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(23));
		this.bill.setBase(100001);
		this.bill.getRenewals().add(new Renewal());
		this.bill.getRenewals().add(new Renewal());
		this.bill.getRenewals().add(new Renewal());
		this.bill.getRenewals().add(new Renewal());
		this.amount = 7;
		this.runAndAssert("You can't renew bill for more than 6 months.");
	}
	
}
