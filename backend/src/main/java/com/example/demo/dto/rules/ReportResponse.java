package com.example.demo.dto.rules;

public class ReportResponse {

	private ReportUnit rsd;
	private ReportUnit eur;
	private ReportUnit usd;
	private ReportUnit chf;
	private ReportUnit gbp;
	
	public ReportResponse() {
		super();
	}
	
	public ReportUnit getRsd() {
		return rsd;
	}

	public void setRsd(ReportUnit rsd) {
		this.rsd = rsd;
	}

	public ReportUnit getEur() {
		return eur;
	}

	public void setEur(ReportUnit eur) {
		this.eur = eur;
	}

	public ReportUnit getUsd() {
		return usd;
	}

	public void setUsd(ReportUnit usd) {
		this.usd = usd;
	}

	public ReportUnit getChf() {
		return chf;
	}

	public void setChf(ReportUnit chf) {
		this.chf = chf;
	}

	public ReportUnit getGbp() {
		return gbp;
	}

	public void setGbp(ReportUnit gbp) {
		this.gbp = gbp;
	}
	
}
