package com.example.demo.rules;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportResponse {

	private ReportUnit rsd;
	private ReportUnit eur;
	private ReportUnit usd;
	private ReportUnit chf;
	private ReportUnit gbp;

}
