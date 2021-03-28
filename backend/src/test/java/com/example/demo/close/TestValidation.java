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

import com.example.demo.ObjectFactory;
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
		kieContainer = kieService.newKieContainer(kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, "0.0.1-SNAPSHOT"));
		kieSession = kieContainer.newKieSession(Constants.CLOSE_RULES);
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
		this.response = new CloseResponse();
		this.account.getBills().add(this.bill);
		this.bill.setAccount(this.account);
	}

	public void runAndAssert(boolean valid, String message) {
		kieSession.insert(this.bill);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.CLOSE_RULES).setFocus();
		kieSession.fireAllRules();
		
		assertEquals(this.response.isValid(), valid);
		assertEquals(this.response.getMessage(), message);
	}
/*
	@Test
	public void testRule1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now());
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.runAndAssert(false, "You can't close bill with passed time less than 20%.");
	}
*/
	@Test
	public void testRule2() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now());
		this.runAndAssert(false, "You can't close bill with passed time more than 80%.");
	}
	
	@Test
	public void testRule3() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.bill.setBase(19999);
		this.runAndAssert(false, "You can't close RSD bill with base less than 20.000 RSD.");
	}

	@Test
	public void testRule4() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.bill.setBase(20001);
		this.runAndAssert(false, "You can't close the only active RSD bill.");
	}

	@Test
	public void testRule5() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.bill.setBase(20001);
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.RSD));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.RSD));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.RSD));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.RSD));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.RSD));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.RSD));
		this.runAndAssert(false, "You can't close more than 6 RSD bills.");
	}
	
	@Test
	public void testRule6() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.bill.setBase(199);
		this.runAndAssert(false, "You can't close foreign bill with base less than 200 currency units.");
	}

	@Test
	public void testRule7() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.bill.setBase(201);
		this.runAndAssert(false, "You can't close the only active foreign bill.");
	}

	@Test
	public void testRule8() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.bill.setBase(201);
		this.account.getBills().add(ObjectFactory.getBill(BillType.EUR));
		this.account.getBills().add(ObjectFactory.getBill(BillType.EUR));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.EUR));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.EUR));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.EUR));
		this.account.getBills().add(ObjectFactory.getBill(BillStatus.ABORTED, BillType.EUR));
		this.runAndAssert(false, "You can't close more than 4 foreign bills.");
	}
	
	@Test
	public void testRule9pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.bill.setBase(20001);
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD));
		this.runAndAssert(true, null);
	}
	
	@Test
	public void testRule9pt2() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.now().minusMonths(1));
		this.bill.setEndDate(LocalDate.now().plusMonths(1));
		this.bill.setBase(201);
		this.account.getBills().add(ObjectFactory.getBill(BillType.EUR));
		this.account.getBills().add(ObjectFactory.getBill(BillType.EUR));
		this.runAndAssert(true, null);
	}

}
