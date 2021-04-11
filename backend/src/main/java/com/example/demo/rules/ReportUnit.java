package com.example.demo.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportUnit {

	private long activeCnt;
	private long closedCnt;	
	private double abortedShare;
	private double baseAvg;	
	private double monthsAvg;	
	private double increaseAvg;	
	private double renewAvg;

}
