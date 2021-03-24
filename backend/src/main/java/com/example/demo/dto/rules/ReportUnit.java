package com.example.demo.dto.rules;

public class ReportUnit {

	private long activeCnt;
	private long closedCnt;	
	private double abortedShare;
	private double baseAvg;	
	private double monthsAvg;	
	private double increaseAvg;	
	private double renewAvg;
	
	public ReportUnit() {
		super();
	}

	public ReportUnit(long activeCnt, long closedCnt, long abortedCnt,
			double baseAvg, double monthsAvg, 
			double increaseAvg, double renewAvg) {
		super();
		this.activeCnt = activeCnt;
		this.closedCnt = closedCnt;
		this.abortedShare = this.closedCnt != 0 ? abortedCnt / this.closedCnt : 0;
		this.baseAvg = baseAvg;
		this.monthsAvg = monthsAvg;
		this.increaseAvg = increaseAvg;
		this.renewAvg = renewAvg;
	}

	public long getActiveCnt() {
		return activeCnt;
	}

	public void setActiveCnt(long activeCnt) {
		this.activeCnt = activeCnt;
	}

	public long getClosedCnt() {
		return closedCnt;
	}

	public void setClosedCnt(long closedCnt) {
		this.closedCnt = closedCnt;
	}

	public double getAbortedShare() {
		return abortedShare;
	}

	public void setAbortedShare(double abortedShare) {
		this.abortedShare = abortedShare;
	}

	public double getBaseAvg() {
		return baseAvg;
	}

	public void setBaseAvg(double baseAvg) {
		this.baseAvg = baseAvg;
	}

	public double getMonthsAvg() {
		return monthsAvg;
	}

	public void setMonthsAvg(double monthsAvg) {
		this.monthsAvg = monthsAvg;
	}

	public double getIncreaseAvg() {
		return increaseAvg;
	}

	public void setIncreaseAvg(double increaseAvg) {
		this.increaseAvg = increaseAvg;
	}

	public double getRenewAvg() {
		return renewAvg;
	}

	public void setRenewAvg(double renewAvg) {
		this.renewAvg = renewAvg;
	}
	
}
