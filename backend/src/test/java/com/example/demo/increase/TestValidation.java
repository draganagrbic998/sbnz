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
import com.example.demo.model.BillStatus;
import com.example.demo.model.BillType;
import com.example.demo.utils.Constants;

public class TestValidation {

	private static KieContainer kieContainer;
	private static KieSession kieSession;

	private Account account;
	private Bill bill;
	private IncreaseResponse response;
	private double amount;

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

	public void runAndAssert(String message) {
		kieSession.insert(this.bill);
		kieSession.insert(this.amount);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.INCREASE_RULES).setFocus();
		kieSession.fireAllRules();

		assertFalse(this.response.isValid());
		assertEquals(this.response.getMessage(), message);
		assertNull(this.response.getBaseUpdate());
		assertNull(this.response.getInterestUpdate());
	}

	@Test
	public void testRule1() {
		this.bill.setStartDate(LocalDate.of(2021, 3, 1));
		this.bill.setEndDate(LocalDate.of(2023, 1, 1));
		this.runAndAssert("You can't deposit on a bill with less than 5% of period passed.");
	}

	@Test
	public void testRule2() {
		this.bill.setStartDate(LocalDate.of(2018, 1, 1));
		this.bill.setEndDate(LocalDate.of(2021, 5, 1));
		this.runAndAssert("You can't deposit on a bill with more than 95% of period passed.");
	}

	@Test
	public void testRule3() {
		this.amount = 1000;
		this.bill.setBalance(100000);
		this.runAndAssert("You can't deposit less than 10% of the bill balance.");
	}

	@Test
	public void testRule4() {
		this.amount = 70000;
		this.bill.setBalance(100000);
		this.runAndAssert("You can't deposit more than 50% of the bill balance.");
	}

	@Test
	public void testRule5() {
		this.bill.setStartDate(LocalDate.of(2021, 3, 1));
		this.bill.setEndDate(LocalDate.of(2021, 7, 1));
		this.runAndAssert("You can't deposit on a RSD bill with duration shorter than 6 months.");
	}

	@Test
	public void testRule6() {
		this.bill.setBase(70000);
		this.runAndAssert("You can't deposit on a RSD bill with base lower than 75.000 RSD.");
	}

	@Test
	public void testRule7() {
		for (int i = 0; i < 5; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(1));

		this.runAndAssert("You can't deposit on a RSD bill on which you made a deposit more than 4 times.");
	}

	@Test
	public void testRule8() {
		this.bill.setType(BillType.EUR);
		this.bill.setStartDate(LocalDate.of(2021, 3, 1));
		this.bill.setEndDate(LocalDate.of(2021, 6, 1));
		this.runAndAssert("You can't deposit on a foreign bill with duration shorter than 4 months.");
	}

	@Test
	public void testRule9() {
		this.bill.setType(BillType.EUR);
		this.bill.setBase(700);
		this.runAndAssert("You can't deposit on a foreign bill with base lower than 750 units.");
	}

	@Test
	public void testRule10() {
		this.bill.setType(BillType.EUR);

		for (int i = 0; i < 3; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(1));

		this.runAndAssert("You can't deposit on a foreign bill on which you made a deposit more than 2 times.");
	}

	@Test
	public void testRule11Pt1() {
		this.amount = 90000;
		this.bill.setBalance(200000);
		this.bill.setBase(200001);
		this.runAndAssert("You can't deposit more than 40% of current bill balance.");
	}

	@Test
	public void testRule11Pt2() {
		this.bill.setType(BillType.EUR);
		this.amount = 900;
		this.bill.setBalance(2000);
		this.bill.setBase(20001);
		this.runAndAssert("You can't deposit more than 40% of current bill balance.");
	}

	@Test
	public void testRule12Pt1() {
		this.amount = 90000;
		this.bill.setBalance(200000);
		this.bill.setBase(199999);

		for (int i = 0; i < 4; i++)
			this.account.getBills().add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.RSD));

		this.runAndAssert("You can't deposit more than 40% of current bill balance.");
	}

	@Test
	public void testRule12Pt2() {
		this.amount = 90000;
		this.bill.setBalance(200000);
		this.bill.setBase(199999);

		for (int i = 0; i < 4; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(30000));

		this.runAndAssert("You can't deposit more than 40% of current bill balance.");
	}

	@Test
	public void testRule13Pt1() {
		this.amount = 900;
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(2000);
		this.bill.setBase(1999);

		for (int i = 0; i < 3; i++)
			this.account.getBills().add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.EUR));

		this.runAndAssert("You can't deposit more than 40% of current bill balance.");
	}

	@Test
	public void testRule13Pt2() {
		this.amount = 900;
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(2000);
		this.bill.setBase(1999);

		for (int i = 0; i < 2; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(280));

		this.runAndAssert("You can't deposit more than 40% of current bill balance.");
	}

	@Test
	public void testRule14Pt1() {
		this.amount = 60000;
		this.bill.setBalance(150000);
		this.bill.setBase(150001);
		this.runAndAssert("You can't deposit more than 35% of current bill balance.");
	}

	@Test
	public void testRule14Pt2() {
		this.bill.setType(BillType.EUR);
		this.amount = 600;
		this.bill.setBalance(1500);
		this.bill.setBase(1501);
		this.runAndAssert("You can't deposit more than 35% of current bill balance.");
	}

	@Test
	public void testRule15Pt1() {
		this.amount = 60000;
		this.bill.setBalance(150000);
		this.bill.setBase(149999);

		for (int i = 0; i < 5; i++)
			this.account.getBills().add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.RSD));

		this.runAndAssert("You can't deposit more than 35% of current bill balance.");
	}

	@Test
	public void testRule15Pt2() {
		this.amount = 60000;
		this.bill.setBalance(150000);
		this.bill.setBase(149999);

		for (int i = 0; i < 3; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(27000));

		this.runAndAssert("You can't deposit more than 35% of current bill balance.");
	}

	@Test
	public void testRule16Pt1() {
		this.amount = 600;
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(1500);
		this.bill.setBase(1499);

		for (int i = 0; i < 4; i++)
			this.account.getBills().add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.EUR));

		this.runAndAssert("You can't deposit more than 35% of current bill balance.");
	}

	@Test
	public void testRule16Pt2() {
		this.amount = 600;
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(1500);
		this.bill.setBase(1499);

		for (int i = 0; i < 2; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(260));

		this.runAndAssert("You can't deposit more than 35% of current bill balance.");
	}

	@Test
	public void testRule17Pt1() {
		this.amount = 35000;
		this.bill.setBalance(100000);
		this.bill.setBase(100001);
		this.runAndAssert("You can't deposit more than 30% of current bill balance.");
	}

	@Test
	public void testRule17Pt2() {
		this.bill.setType(BillType.EUR);
		this.amount = 350;
		this.bill.setBalance(1000);
		this.bill.setBase(1001);
		this.runAndAssert("You can't deposit more than 30% of current bill balance.");
	}

	@Test
	public void testRule18Pt1() {
		this.amount = 35000;
		this.bill.setBalance(100000);
		this.bill.setBase(99999);

		for (int i = 0; i < 6; i++)
			this.account.getBills().add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.RSD));

		this.runAndAssert("You can't deposit more than 30% of current bill balance.");
	}

	@Test
	public void testRule18Pt2() {
		this.amount = 35000;
		this.bill.setBalance(100000);
		this.bill.setBase(99999);

		for (int i = 0; i < 2; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(26000));

		this.runAndAssert("You can't deposit more than 30% of current bill balance.");
	}

	@Test
	public void testRule19Pt1() {
		this.amount = 350;
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(1000);
		this.bill.setBase(999);

		for (int i = 0; i < 5; i++)
			this.account.getBills().add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.EUR));

		this.runAndAssert("You can't deposit more than 30% of current bill balance.");
	}

	@Test
	public void testRule19Pt2() {
		this.amount = 350;
		this.bill.setType(BillType.EUR);
		this.bill.setBalance(1000);
		this.bill.setBase(999);

		for (int i = 0; i < 2; i++)
			this.bill.getTransactions().add(ObjectFactory.getTransaction(220));

		this.runAndAssert("You can't deposit more than 30% of current bill balance.");
	}
}
