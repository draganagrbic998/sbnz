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

import com.example.demo.rules.IncreaseResponse;
import com.example.demo.ObjectFactory;
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
	private int amount;

	@BeforeClass
	public static void beforeClass() {
		KieServices kieService = KieServices.Factory.get();
		kieContainer = kieService.newKieContainer(
				kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, "0.0.1-SNAPSHOT"));
		kieSession = kieContainer.newKieSession(Constants.INCREASE_RULES);
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

	public void runAndAssert(double baseUpdate) {
		kieSession.insert(this.bill);
		kieSession.insert(this.amount);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.INCREASE_RULES).setFocus();
		kieSession.fireAllRules();

		assertTrue(this.response.isValid());
		assertNull(this.response.getMessage());
		assertEquals(this.response.getBaseUpdate(), Double.valueOf(baseUpdate));
	}

	@Test
	public void testRule1Pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(100001);
		this.bill.setBase(99999);
		this.amount = 45000;
		this.runAndAssert(0.9 * 99999);
	}

	@Test
	public void testRule1Pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(300001);
		this.bill.setBase(300001);
		this.amount = 40000;
		this.runAndAssert(0.9 * 300001);
	}

	@Test
	public void testRule1Pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(3001);
		this.bill.setBase(3001);
		this.amount = 400;
		this.runAndAssert(0.9 * 3001);
	}

	@Test
	public void testRule2() {
		this.bill.setBalance(299999);
		this.bill.setBase(300001);
		this.amount = 40000;

		for (int i = 0; i < 4; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(35000, 4));

		this.runAndAssert(0.9 * 300001);
	}

	@Test
	public void testRule3Pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(100001);
		this.bill.setBase(99999);
		this.amount = 35000;
		this.runAndAssert(0.8 * 99999);
	}

	@Test
	public void testRule3Pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(250001);
		this.bill.setBase(250001);
		this.amount = 30000;
		this.runAndAssert(0.8 * 250001);
	}

	@Test
	public void testRule3Pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(2501);
		this.bill.setBase(2501);
		this.amount = 300;
		this.runAndAssert(0.8 * 2501);
	}

	@Test
	public void testRule4() {
		this.bill.setBalance(249999);
		this.bill.setBase(250001);
		this.amount = 30000;

		for (int i = 0; i < 3; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(35000, 5));

		this.runAndAssert(0.8 * 250001);
	}

	@Test
	public void testRule5Pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(100001);
		this.bill.setBase(99999);
		this.amount = 30000;
		this.runAndAssert(0.7 * 99999);
	}

	@Test
	public void testRule5Pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(200001);
		this.bill.setBase(200001);
		this.amount = 50000;
		this.runAndAssert(0.7 * 200001);
	}

	@Test
	public void testRule5Pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(2001);
		this.bill.setBase(2001);
		this.amount = 500;
		this.runAndAssert(0.7 * 2001);
	}

	@Test
	public void testRule6() {
		this.bill.setBalance(199999);
		this.bill.setBase(200001);
		this.amount = 50000;

		for (int i = 0; i < 3; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(25000, 6));

		this.runAndAssert(0.7 * 200001);
	}

	@Test
	public void testRule7Pt1() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(149999);
		this.bill.setBase(150001);
		this.amount = 40000;
		this.runAndAssert(0.6 * 150001);
	}

	@Test
	public void testRule7Pt2() {
		this.bill.setType(BillType.RSD);
		this.bill.setBalance(150001);
		this.bill.setBase(150001);
		this.amount = 30000;
		this.runAndAssert(0.6 * 150001);
	}

	@Test
	public void testRule7Pt3() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(1501);
		this.bill.setBase(1501);
		this.amount = 300;
		this.runAndAssert(0.6 * 1501);
	}

	@Test
	public void testRule8() {
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(1499);
		this.bill.setBase(1501);
		this.amount = 300;

		for (int i = 0; i < 2; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(250, 7));

		this.runAndAssert(0.6 * 1501);
	}

	@Test
	public void testRule9() {
		this.bill.setBalance(149999);
		this.bill.setBase(150001);
		this.amount = 20000;
		this.runAndAssert(0.5 * 150001);
	}
}
