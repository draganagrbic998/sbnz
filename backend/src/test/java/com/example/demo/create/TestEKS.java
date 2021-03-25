package com.example.demo.create;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.example.demo.ObjectFactory;
import com.example.demo.rules.BillRequest;
import com.example.demo.rules.BillResponse;
import com.example.demo.model.Account;
import com.example.demo.model.Bill;
import com.example.demo.model.BillStatus;
import com.example.demo.model.BillType;
import com.example.demo.model.Transaction;
import com.example.demo.utils.Constants;

public class TestEKS {

	private static KieContainer kieContainer;
	private static KieSession kieSession;

	private Account account;
	private BillRequest request;
	private BillResponse response;
	
	@BeforeClass
	public static void beforeClass() {
		KieServices kieService = KieServices.Factory.get();
		kieContainer = kieService.newKieContainer(kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, "0.0.1-SNAPSHOT"));
		kieSession = kieContainer.newKieSession(Constants.CREATE_RULES);
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
		this.request = new BillRequest();
		this.response = new BillResponse();
		
		this.request.setType(BillType.RSD);
		this.request.setBase(10001);
		this.request.setMonths(4);
		this.account.setBalance(10001 * 140 + 1001);
		this.account.setBirthDate(LocalDate.now().plusYears(18).plusDays(1));
	}

	public void runAndAssert(double eks, int reward) {
		kieSession.insert(this.account);
		kieSession.insert(this.request);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.CREATE_RULES).setFocus();
		kieSession.fireAllRules();
		
		assertTrue(this.response.isValid());
		assertNull(this.response.getMessage());
		assertNotNull(this.response.getNks());
		assertNotNull(this.response.getPoints());
		assertEquals(this.response.getEks(), Double.valueOf(eks));
		assertEquals(this.response.getReward(), Integer.valueOf(reward));
	}
	
	@Test
	public void testRule1pt1() {
		this.account.setDate(LocalDate.now().plusYears(5).plusDays(1));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 100001, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2)));
		this.runAndAssert(1.0, 5000);
	}
	
	@Test
	public void testRule1pt2() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		this.runAndAssert(1.0, 5000);		
	}

	@Test
	public void testRule1pt3() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		this.request.setType(BillType.EUR);
		this.runAndAssert(1.0, 5000);
	}
	
	@Test
	public void testRule2pt1() {
		this.account.setDate(LocalDate.now().plusYears(4).plusDays(1));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 300001, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2)));
		this.runAndAssert(0.98, 2000);		
	}
	
	@Test
	public void testRule2pt2() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		this.account.getBills().add(ObjectFactory.getBill(BillType.GBP, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2)));
		this.request.setType(BillType.EUR);
		this.runAndAssert(0.98, 2000);
	}
	
	@Test
	public void testRule2pt3() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		this.account.getBills().add(ObjectFactory.getBill(BillType.GBP, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2)));
		this.request.setType(BillType.USD);
		this.runAndAssert(0.98, 2000);
	}
	
	@Test
	public void testRule3pt1() {
		this.account.setDate(LocalDate.now().plusYears(3).plusDays(1));
		Set<Transaction> transactions = new HashSet<>();
		transactions.add(ObjectFactory.getTransaction(0.3 * 100 + 1));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 100, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2), transactions));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 100, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2), transactions));
		this.runAndAssert(0.95, 1000);		
	}
	
	@Test
	public void testRule3pt2() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		this.account.getBills().add(ObjectFactory.getBill(BillType.EUR, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2)));
		this.request.setType(BillType.CHF);
		this.runAndAssert(0.95, 1000);		
	}
	
	@Test
	public void testRule3pt3() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		this.account.getBills().add(ObjectFactory.getBill(BillType.EUR, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2)));
		this.request.setType(BillType.GBP);
		this.runAndAssert(0.95, 1000);
	}
	
	@Test
	public void testRule4pt1() {
		this.account.setDate(LocalDate.now().plusYears(2).plusDays(1));
		Set<Transaction> transactions = new HashSet<>();
		transactions.add(ObjectFactory.getTransaction(50001));
		transactions.add(ObjectFactory.getTransaction(50001));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2), transactions));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2), transactions));
		this.runAndAssert(0.9, 500);		
	}
	
	@Test
	public void testRule4pt2() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		Bill bill = ObjectFactory.getBill(BillType.RSD, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2), null);
		bill.setStatus(BillStatus.CLOSED);
		this.account.getBills().add(bill);
		this.runAndAssert(0.9, 500);
	}
	
	@Test
	public void testRule5pt1() {
		this.account.setDate(LocalDate.now().plusYears(1).plusDays(1));
		Set<Transaction> transactions = new HashSet<>();
		transactions.add(ObjectFactory.getTransaction(10001));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(3).plusDays(2), transactions));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(3).plusDays(2), transactions));
		this.runAndAssert(0.87, 200);
	}
	
	@Test
	public void testRule5pt2() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		Bill bill = ObjectFactory.getBill(BillType.EUR, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2), null);
		bill.setStatus(BillStatus.CLOSED);
		this.account.getBills().add(bill);
		this.request.setType(BillType.EUR);
		this.runAndAssert(0.87, 200);
	}
	
	@Test
	public void testRule6() {
		this.account.setDate(LocalDate.now().plusYears(0).plusDays(1));
		this.account.getBills().add(ObjectFactory.getBill(BillType.RSD, 0, LocalDate.now().minusMonths(9).plusDays(1), LocalDate.now().minusMonths(9).plusDays(2), null));
		this.runAndAssert(0.85, 0);
	}

}
