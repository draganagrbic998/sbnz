package com.example.demo.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.example.demo.ObjectFactory;
import com.example.demo.model.Bill;
import com.example.demo.model.BillStatus;
import com.example.demo.model.BillType;
import com.example.demo.model.Renewal;
import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionType;
import com.example.demo.rules.ReportResponse;
import com.example.demo.utils.Constants;

public class TestBaseReport {

	private static KieContainer kieContainer;
	private static KieSession kieSession;

	private List<Bill> bills = new ArrayList<>();
	private ReportResponse response;

	private double trueBaseSum;
	private int trueMonthsSum;

	@BeforeClass
	public static void beforeClass() {
		KieServices kieService = KieServices.Factory.get();
		kieContainer = kieService.newKieContainer(
				kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, "0.0.1-SNAPSHOT"));
		kieSession = kieContainer.newKieSession(Constants.REPORT_RULES);
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
		this.bills.clear();

		this.response = new ReportResponse();

		this.trueBaseSum = 0;
		this.trueMonthsSum = 0;
	}

	@Test
	public void testReportRule1() {
		for (int i = 0; i < 5; i++) {
			HashSet<Transaction> transactions = new HashSet<>();
			HashSet<Renewal> renewals = new HashSet<>();

			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 10000));
			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 11000));
			renewals.add(ObjectFactory.getRenewal(6));
			renewals.add(ObjectFactory.getRenewal(7));
			this.bills.add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.RSD, 100000 + i * 1000, 18 - i,
					transactions, renewals));

			this.trueBaseSum += this.bills.get(this.bills.size() - 1).getBase();
			this.trueMonthsSum += this.bills.get(this.bills.size() - 1).months();
		}

		this.bills.get(2).setStatus(BillStatus.CLOSED);
		this.bills.get(3).setStatus(BillStatus.CLOSED);
		this.bills.get(4).setStatus(BillStatus.ABORTED);

		kieSession.insert(this.bills);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.BASE_REPORT).setFocus();
		kieSession.fireAllRules();

		assertNotNull(this.response.getRsd());
		assertEquals(2, this.response.getRsd().getActiveCnt());
		assertEquals(3, this.response.getRsd().getClosedCnt());
		assertEquals(Double.valueOf(1 / 3), Double.valueOf(this.response.getRsd().getAbortedShare()));
		assertEquals(Double.valueOf(this.trueBaseSum / 5), Double.valueOf(this.response.getRsd().getBaseAvg()));
		assertEquals(Double.valueOf(this.trueMonthsSum / 5), Double.valueOf(this.response.getRsd().getMonthsAvg()));
		assertEquals(Double.valueOf(10500), Double.valueOf(this.response.getRsd().getIncreaseAvg()));
		assertEquals(Double.valueOf(6.5), Double.valueOf(this.response.getRsd().getRenewAvg()));
	}

	@Test
	public void testReportRule2() {
		for (int i = 0; i < 5; i++) {
			HashSet<Transaction> transactions = new HashSet<>();
			HashSet<Renewal> renewals = new HashSet<>();

			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 100));
			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 110));
			renewals.add(ObjectFactory.getRenewal(7));
			renewals.add(ObjectFactory.getRenewal(8));
			this.bills.add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.EUR, 1000 + i * 10, 17 - i, transactions,
					renewals));

			this.trueBaseSum += this.bills.get(this.bills.size() - 1).getBase();
			this.trueMonthsSum += this.bills.get(this.bills.size() - 1).months();
		}

		this.bills.get(2).setStatus(BillStatus.CLOSED);
		this.bills.get(3).setStatus(BillStatus.ABORTED);
		this.bills.get(4).setStatus(BillStatus.ABORTED);

		kieSession.insert(this.bills);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.BASE_REPORT).setFocus();
		kieSession.fireAllRules();

		assertNotNull(this.response.getEur());
		assertEquals(2, this.response.getEur().getActiveCnt());
		assertEquals(3, this.response.getEur().getClosedCnt());
		assertEquals(Double.valueOf(2 / 3), Double.valueOf(this.response.getEur().getAbortedShare()));
		assertEquals(Double.valueOf(this.trueBaseSum / 5), Double.valueOf(this.response.getEur().getBaseAvg()));
		assertEquals(Double.valueOf(this.trueMonthsSum / 5), Double.valueOf(this.response.getEur().getMonthsAvg()));
		assertEquals(Double.valueOf(105), Double.valueOf(this.response.getEur().getIncreaseAvg()));
		assertEquals(Double.valueOf(7.5), Double.valueOf(this.response.getEur().getRenewAvg()));
	}

	@Test
	public void testReportRule3() {
		for (int i = 0; i < 5; i++) {
			HashSet<Transaction> transactions = new HashSet<>();
			HashSet<Renewal> renewals = new HashSet<>();

			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 110));
			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 120));
			renewals.add(ObjectFactory.getRenewal(8));
			renewals.add(ObjectFactory.getRenewal(9));
			this.bills.add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.USD, 1010 + i * 10, 19 - i, transactions,
					renewals));

			this.trueBaseSum += this.bills.get(this.bills.size() - 1).getBase();
			this.trueMonthsSum += this.bills.get(this.bills.size() - 1).months();
		}

		this.bills.get(2).setStatus(BillStatus.ABORTED);
		this.bills.get(3).setStatus(BillStatus.ABORTED);
		this.bills.get(4).setStatus(BillStatus.ABORTED);

		kieSession.insert(this.bills);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.BASE_REPORT).setFocus();
		kieSession.fireAllRules();

		assertNotNull(this.response.getUsd());
		assertEquals(2, this.response.getUsd().getActiveCnt());
		assertEquals(3, this.response.getUsd().getClosedCnt());
		assertEquals(Double.valueOf(1), Double.valueOf(this.response.getUsd().getAbortedShare()));
		assertEquals(Double.valueOf(this.trueBaseSum / 5), Double.valueOf(this.response.getUsd().getBaseAvg()));
		assertEquals(Double.valueOf(this.trueMonthsSum / 5), Double.valueOf(this.response.getUsd().getMonthsAvg()));
		assertEquals(Double.valueOf(115), Double.valueOf(this.response.getUsd().getIncreaseAvg()));
		assertEquals(Double.valueOf(8.5), Double.valueOf(this.response.getUsd().getRenewAvg()));
	}

	@Test
	public void testReportRule4() {
		for (int i = 0; i < 5; i++) {
			HashSet<Transaction> transactions = new HashSet<>();
			HashSet<Renewal> renewals = new HashSet<>();

			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 130));
			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 120));
			renewals.add(ObjectFactory.getRenewal(10));
			renewals.add(ObjectFactory.getRenewal(9));
			this.bills.add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.CHF, 1010 + i * 20, 17 - i, transactions,
					renewals));

			this.trueBaseSum += this.bills.get(this.bills.size() - 1).getBase();
			this.trueMonthsSum += this.bills.get(this.bills.size() - 1).months();
		}

		this.bills.get(2).setStatus(BillStatus.CLOSED);
		this.bills.get(3).setStatus(BillStatus.CLOSED);
		this.bills.get(4).setStatus(BillStatus.CLOSED);

		kieSession.insert(this.bills);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.BASE_REPORT).setFocus();
		kieSession.fireAllRules();

		assertNotNull(this.response.getChf());
		assertEquals(2, this.response.getChf().getActiveCnt());
		assertEquals(3, this.response.getChf().getClosedCnt());
		assertEquals(Double.valueOf(0), Double.valueOf(this.response.getChf().getAbortedShare()));
		assertEquals(Double.valueOf(this.trueBaseSum / 5), Double.valueOf(this.response.getChf().getBaseAvg()));
		assertEquals(Double.valueOf(this.trueMonthsSum / 5), Double.valueOf(this.response.getChf().getMonthsAvg()));
		assertEquals(Double.valueOf(125), Double.valueOf(this.response.getChf().getIncreaseAvg()));
		assertEquals(Double.valueOf(9.5), Double.valueOf(this.response.getChf().getRenewAvg()));
	}

	@Test
	public void testReportRule5() {
		for (int i = 0; i < 5; i++) {
			HashSet<Transaction> transactions = new HashSet<>();
			HashSet<Renewal> renewals = new HashSet<>();

			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 140));
			transactions.add(ObjectFactory.getTransaction(TransactionType.INCREASE, 130));
			renewals.add(ObjectFactory.getRenewal(11));
			renewals.add(ObjectFactory.getRenewal(10));
			this.bills.add(ObjectFactory.getBill(BillStatus.ACTIVE, BillType.GBP, 1020 + i * 20, 19 - i, transactions,
					renewals));

			this.trueBaseSum += this.bills.get(this.bills.size() - 1).getBase();
			this.trueMonthsSum += this.bills.get(this.bills.size() - 1).months();
		}

		this.bills.get(1).setStatus(BillStatus.ABORTED);
		this.bills.get(2).setStatus(BillStatus.CLOSED);
		this.bills.get(3).setStatus(BillStatus.CLOSED);
		this.bills.get(4).setStatus(BillStatus.CLOSED);

		kieSession.insert(this.bills);
		kieSession.insert(this.response);
		kieSession.getAgenda().getAgendaGroup(Constants.BASE_REPORT).setFocus();
		kieSession.fireAllRules();

		assertNotNull(this.response.getGbp());
		assertEquals(1, this.response.getGbp().getActiveCnt());
		assertEquals(4, this.response.getGbp().getClosedCnt());
		assertEquals(Double.valueOf(1 / 4), Double.valueOf(this.response.getGbp().getAbortedShare()));
		assertEquals(Double.valueOf(this.trueBaseSum / 5), Double.valueOf(this.response.getGbp().getBaseAvg()));
		assertEquals(Double.valueOf(this.trueMonthsSum / 5), Double.valueOf(this.response.getGbp().getMonthsAvg()));
		assertEquals(Double.valueOf(135), Double.valueOf(this.response.getGbp().getIncreaseAvg()));
		assertEquals(Double.valueOf(10.5), Double.valueOf(this.response.getGbp().getRenewAvg()));
	}
}
