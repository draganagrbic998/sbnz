package com.example.demo.close;

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

import com.example.demo.rules.CloseResponse;
import com.example.demo.model.Account;
import com.example.demo.model.Bill;
import com.example.demo.model.BillStatus;
import com.example.demo.model.BillType;
import com.example.demo.utils.Constants;

public class TestValidation {

	private static KieContainer kieContainer;
	private static KieSession kieSession;

	private Account account;
	private Bill bill;
	private CloseResponse response;

	@BeforeClass
	public static void beforeClass() {
		KieServices kieService = KieServices.Factory.get();
		kieContainer = kieService.newKieContainer(
				kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, "0.0.1-SNAPSHOT"));
		kieSession = kieContainer.newKieSession(Constants.CLOSE_RULES);
	}

	@AfterClass
	public static void afterClass() {
		kieSession.dispose();
		kieSession.destroy();
	}

	@Before
	public void before() {
		for (FactHandle fh : kieSession.getFactHandles()) {
			kieSession.delete(fh);
		}

		this.account = new Account();
		
		this.bill = new Bill();
		this.bill.setStartDate(LocalDate.now().minusMonths(21));
		this.bill.setEndDate(LocalDate.now().plusMonths(21));
		this.bill.setType(BillType.RSD);
		this.bill.setBase(30000);
		
		this.response = new CloseResponse();
		this.account.getBills().add(this.bill);
		this.bill.setAccount(this.account);
	}

	public void runAndAssert(String message) {
		kieSession.insert(this.bill);
		kieSession.insert(this.account);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.CLOSE_RULES).setFocus();
		kieSession.fireAllRules();

		assertFalse(this.response.isValid());
		assertEquals(this.response.getMessage(), message);
	}

	@Test
	public void testRule1() {
		this.bill.setStartDate(LocalDate.of(2021, 1, 1));
		this.bill.setEndDate(LocalDate.of(2023, 1, 1));
		this.runAndAssert("You can't prematurely close a bill with less than 20% of period passed.");
	}

	@Test
	public void testRule2() {
		this.bill.setStartDate(LocalDate.of(2018, 1, 1));
		this.bill.setEndDate(LocalDate.of(2021, 6, 1));
		this.runAndAssert("You can't prematurely close a bill with more than 80% of period passed.");
	}

	@Test
	public void testRule3() {
		this.bill.setBase(10000);
		this.runAndAssert("You can't prematurely close a RSD bill with base lower than 20.000 RSD.");
	}
	
	@Test
	public void testRule4() {
		this.runAndAssert("You can't prematurely close the only active RSD bill you own.");
	}
	
	@Test
	public void testRule5() {
		for (int i = 0; i < 6; i++) {
			Bill b = new Bill();
			b.setType(BillType.RSD);
			b.setStatus(BillStatus.ABORTED);
			this.account.getBills().add(b);
		}
		
		Bill b = new Bill();
		b.setType(BillType.RSD);
		b.setStatus(BillStatus.ACTIVE);
		this.account.getBills().add(b);
		
		this.runAndAssert("You can't prematurely close anymore RSD bills as you have already closed 6 or more.");
	}
	
	@Test
	public void testRule6() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(100);
		this.runAndAssert("You can't prematurely close a foreign bill with base lower than 200 units.");
	}
	
	@Test
	public void testRule7() {
		this.bill.setType(BillType.EUR);
		this.runAndAssert("You can't prematurely close the only active foreign bill you own.");
	}
	
	@Test
	public void testRule8() {
		for (int i = 0; i < 4; i++) {
			Bill b = new Bill();
			b.setType(BillType.EUR);
			b.setStatus(BillStatus.ABORTED);
			this.account.getBills().add(b);
		}
		
		Bill b = new Bill();
		b.setType(BillType.EUR);
		b.setStatus(BillStatus.ACTIVE);
		this.account.getBills().add(b);
		
		this.bill.setType(BillType.EUR);
		this.runAndAssert("You can't prematurely close anymore foreign bills as you have already closed 4 or more.");
	}
}
