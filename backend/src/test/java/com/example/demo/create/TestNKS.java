package com.example.demo.create;

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

import com.example.demo.rules.BillRequest;
import com.example.demo.rules.BillResponse;
import com.example.demo.model.Account;
import com.example.demo.model.BillType;
import com.example.demo.utils.Constants;

public class TestNKS {

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
		this.account.setBirthDate(LocalDate.now().plusYears(18).plusDays(1));
	}

	public void runAndAssert(double nks) {
		kieSession.insert(this.account);
		kieSession.insert(this.request);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.CREATE_RULES).setFocus();
		kieSession.fireAllRules();
		
		assertTrue(this.response.isValid());
		assertNull(this.response.getMessage());
		assertEquals(this.response.getNks(), Double.valueOf(nks));
		assertNotNull(this.response.getPoints());
		assertNotNull(this.response.getEks());
		assertNotNull(this.response.getReward());
	}
	
	@Test
	public void testRule1RSDpt1() {
		this.request.setType(BillType.RSD);
		this.request.setBase(10001);
		this.request.setMonths(22);
		this.account.setBalance(10001 + 201);
		this.runAndAssert(1.5);
	}

	@Test
	public void testRule1RSDpt2() {
		this.request.setType(BillType.RSD);
		this.request.setBase(2100001);
		this.request.setMonths(21);
		this.account.setBalance(2100001 + 201);
		this.runAndAssert(1.5);
	}

	@Test
	public void testRule1EURpt1() {
		this.request.setType(BillType.EUR);
		this.request.setBase(101);
		this.request.setMonths(31);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.5);
	}

	@Test
	public void testRule1EURpt2() {
		this.request.setType(BillType.EUR);
		this.request.setBase(90001);
		this.request.setMonths(30);
		this.account.setBalance(90001 * 140 + 1001);
		this.runAndAssert(1.5);
	}
	
	@Test
	public void testRule1USDpt1() {
		this.request.setType(BillType.USD);
		this.request.setBase(101);
		this.request.setMonths(28);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.5);		
	}
	
	@Test
	public void testRule1USDpt2() {
		this.request.setType(BillType.USD);
		this.request.setBase(78301);
		this.request.setMonths(27);
		this.account.setBalance(78301 * 140 + 1001);
		this.runAndAssert(1.5);
	}

	@Test
	public void testRule1CHFpt1() {
		this.request.setType(BillType.CHF);
		this.request.setBase(101);
		this.request.setMonths(25);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.5);		
	}
	
	@Test
	public void testRule1CHFpt2() {
		this.request.setType(BillType.CHF);
		this.request.setBase(67201);
		this.request.setMonths(24);
		this.account.setBalance(67201 * 140 + 1001);
		this.runAndAssert(1.5);
	}

	@Test
	public void testRule1GBPpt1() {
		this.request.setType(BillType.GBP);
		this.request.setBase(101);
		this.request.setMonths(34);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.5);		
	}
	
	@Test
	public void testRule1GBPpt2() {
		this.request.setType(BillType.GBP);
		this.request.setBase(99331);
		this.request.setMonths(33);
		this.account.setBalance(99331 * 140 + 1001);
		this.runAndAssert(1.5);
	}

	@Test
	public void testRule2RSDpt1() {
		this.request.setType(BillType.RSD);
		this.request.setBase(10001);
		this.request.setMonths(16);
		this.account.setBalance(10001 + 201);
		this.runAndAssert(1.2);		
	}
	
	@Test
	public void testRule2RSDpt2() {
		this.request.setType(BillType.RSD);
		this.request.setBase(750001);
		this.request.setMonths(15);
		this.account.setBalance(750001 + 201);
		this.runAndAssert(1.2);
	}
	
	@Test
	public void testRule2EURpt1() {
		this.request.setType(BillType.EUR);
		this.request.setBase(101);
		this.request.setMonths(25);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.2);		
	}
	
	@Test
	public void testRule2EURpt2() {
		this.request.setType(BillType.EUR);
		this.request.setBase(48001);
		this.request.setMonths(24);
		this.account.setBalance(48001 * 140 + 1001);
		this.runAndAssert(1.2);
	}

	@Test
	public void testRule2USDpt1() {
		this.request.setType(BillType.USD);
		this.request.setBase(101);
		this.request.setMonths(22);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.2);		
	}
	
	@Test
	public void testRule2USDpt2() {
		this.request.setType(BillType.USD);
		this.request.setBase(39901);
		this.request.setMonths(21);
		this.account.setBalance(39901 * 140 + 1001);
		this.runAndAssert(1.2);
	}

	@Test
	public void testRule2CHFpt1() {
		this.request.setType(BillType.CHF);
		this.request.setBase(101);
		this.request.setMonths(19);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.2);		
	}
	
	@Test
	public void testRule2CHFpt2() {
		this.request.setType(BillType.CHF);
		this.request.setBase(32401);
		this.request.setMonths(18);
		this.account.setBalance(32401 * 140 + 1001);
		this.runAndAssert(1.2);
	}

	@Test
	public void testRule2GBPpt1() {
		this.request.setType(BillType.GBP);
		this.request.setBase(101);
		this.request.setMonths(28);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.2);		
	}
	
	@Test
	public void testRule2GBPpt2() {
		this.request.setType(BillType.GBP);
		this.request.setBase(56701);
		this.request.setMonths(27);
		this.account.setBalance(56701 * 140 + 1001);
		this.runAndAssert(1.2);
	}
	
	@Test
	public void testRule3RSDpt1() {
		this.request.setType(BillType.RSD);
		this.request.setBase(10001);
		this.request.setMonths(10);
		this.account.setBalance(10001 + 201);
		this.runAndAssert(1.0);		
	}
	
	@Test
	public void testRule3RSDpt2() {
		this.request.setType(BillType.RSD);
		this.request.setBase(270001);
		this.request.setMonths(9);
		this.account.setBalance(270001 + 201);
		this.runAndAssert(1.0);
	}

	@Test
	public void testRule3EURpt1() {
		this.request.setType(BillType.EUR);
		this.request.setBase(101);
		this.request.setMonths(19);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.0);		
	}
	
	@Test
	public void testRule3EURpt2() {
		this.request.setType(BillType.EUR);
		this.request.setBase(18001);
		this.request.setMonths(18);
		this.account.setBalance(18001 * 140 + 1001);
		this.runAndAssert(1.0);
	}

	@Test
	public void testRule3USDpt1() {
		this.request.setType(BillType.USD);
		this.request.setBase(101);
		this.request.setMonths(16);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.0);		
	}
	
	@Test
	public void testRule3USDpt2() {
		this.request.setType(BillType.USD);
		this.request.setBase(13501);
		this.request.setMonths(15);
		this.account.setBalance(13501 * 140 + 1001);
		this.runAndAssert(1.0);
	}
	
	@Test
	public void testRule3CHFpt1() {
		this.request.setType(BillType.CHF);
		this.request.setBase(101);
		this.request.setMonths(13);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.0);		
	}
	
	@Test
	public void testRule3CHFpt2() {
		this.request.setType(BillType.CHF);
		this.request.setBase(9601);
		this.request.setMonths(12);
		this.account.setBalance(9601 * 140 + 1001);
		this.runAndAssert(1.0);
	}

	@Test
	public void testRule3GBPpt1() {
		this.request.setType(BillType.GBP);
		this.request.setBase(101);
		this.request.setMonths(22);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(1.0);		
	}
	
	@Test
	public void testRule3GBPpt2() {
		this.request.setType(BillType.GBP);
		this.request.setBase(23101);
		this.request.setMonths(21);
		this.account.setBalance(23101 * 140 + 1001);
		this.runAndAssert(1.0);
	}

	@Test
	public void testRule4RSDpt1() {
		this.request.setType(BillType.RSD);
		this.request.setBase(10001);
		this.request.setMonths(7);
		this.account.setBalance(10001 + 201);
		this.runAndAssert(0.8);		
	}
	
	@Test
	public void testRule4RSDpt2() {
		this.request.setType(BillType.RSD);
		this.request.setBase(60001);
		this.request.setMonths(6);
		this.account.setBalance(60001 + 201);
		this.runAndAssert(0.8);
	}

	@Test
	public void testRule4EURpt1() {
		this.request.setType(BillType.EUR);
		this.request.setBase(101);
		this.request.setMonths(13);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(0.8);		
	}
	
	@Test
	public void testRule4EURpt2() {
		this.request.setType(BillType.EUR);
		this.request.setBase(6001);
		this.request.setMonths(12);
		this.account.setBalance(6001 * 140 + 1001);
		this.runAndAssert(0.8);
	}
	
	@Test
	public void testRule4USDpt1() {
		this.request.setType(BillType.USD);
		this.request.setBase(101);
		this.request.setMonths(10);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(0.8);		
	}
	
	@Test
	public void testRule4USDpt2() {
		this.request.setType(BillType.USD);
		this.request.setBase(4321);
		this.request.setMonths(9);
		this.account.setBalance(4321 * 140 + 1001);
		this.runAndAssert(0.8);
	}

	@Test
	public void testRule4CHFpt1() {
		this.request.setType(BillType.CHF);
		this.request.setBase(101);
		this.request.setMonths(7);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(0.8);		
	}
	
	@Test
	public void testRule4CHFpt2() {
		this.request.setType(BillType.CHF);
		this.request.setBase(2821);
		this.request.setMonths(6);
		this.account.setBalance(2821 * 140 + 1001);
		this.runAndAssert(0.8);
	}

	@Test
	public void testRule4GBPpt1() {
		this.request.setType(BillType.GBP);
		this.request.setBase(101);
		this.request.setMonths(16);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(0.8);		
	}
	
	@Test
	public void testRule4GBPpt2() {
		this.request.setType(BillType.GBP);
		this.request.setBase(7801);
		this.request.setMonths(15);
		this.account.setBalance(7801 * 140 + 1001);
		this.runAndAssert(0.8);
	}

	@Test
	public void testRule5RSDpt1() {
		this.request.setType(BillType.RSD);
		this.request.setBase(10001);
		this.request.setMonths(6);
		this.account.setBalance(10001 + 201);
		this.runAndAssert(0.5);		
	}
	
	@Test
	public void testRule5RSDpt2() {
		this.request.setType(BillType.RSD);
		this.request.setBase(60000);
		this.request.setMonths(6);
		this.account.setBalance(60000 + 201);
		this.runAndAssert(0.5);
	}

	@Test
	public void testRule5EURpt1() {
		this.request.setType(BillType.EUR);
		this.request.setBase(101);
		this.request.setMonths(12);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(0.5);		
	}
	
	@Test
	public void testRule5EURpt2() {
		this.request.setType(BillType.EUR);
		this.request.setBase(6000);
		this.request.setMonths(12);
		this.account.setBalance(6000 * 140 + 1001);
		this.runAndAssert(0.5);
	}

	@Test
	public void testRule5USDpt1() {
		this.request.setType(BillType.USD);
		this.request.setBase(101);
		this.request.setMonths(9);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(0.5);		
	}
	
	@Test
	public void testRule5USDpt2() {
		this.request.setType(BillType.USD);
		this.request.setBase(4320);
		this.request.setMonths(9);
		this.account.setBalance(4320 * 140 + 1001);
		this.runAndAssert(0.5);
	}
	
	@Test
	public void testRule5CHFpt1() {
		this.request.setType(BillType.CHF);
		this.request.setBase(101);
		this.request.setMonths(6);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(0.5);		
	}
	
	@Test
	public void testRule5CHFpt2() {
		this.request.setType(BillType.CHF);
		this.request.setBase(2820);
		this.request.setMonths(6);
		this.account.setBalance(2820 * 140 + 1001);
		this.runAndAssert(0.5);
	}

	@Test
	public void testRule5GBPpt1() {
		this.request.setType(BillType.GBP);
		this.request.setBase(101);
		this.request.setMonths(15);
		this.account.setBalance(101 * 140 + 1001);
		this.runAndAssert(0.5);		
	}
	
	@Test
	public void testRule5GBPpt2() {
		this.request.setType(BillType.GBP);
		this.request.setBase(7800);
		this.request.setMonths(15);
		this.account.setBalance(7800 * 140 + 1001);
		this.runAndAssert(0.5);
	}

}
