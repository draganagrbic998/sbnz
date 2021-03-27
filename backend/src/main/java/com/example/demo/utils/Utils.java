package com.example.demo.utils;

import com.example.demo.model.BillType;

public class Utils {

	public static double convertToRSD(BillType type, double value) {
		if (type.equals(BillType.EUR)) {
			return 117.48 * value;
		}
		if (type.equals(BillType.USD)) {
			return 98.77 * value;
		}
		if (type.equals(BillType.CHF)) {
			return 106.14 * value;
		}
		if (type.equals(BillType.GBP)) {
			return 137.26 * value;
		}
		return value;
	}

	public static double convertToCurrency(BillType type, double value) {
		if (type.equals(BillType.EUR)) {
			return 0.0085 * value;
		}
		if (type.equals(BillType.USD)) {
			return 0.01 * value;
		}
		if (type.equals(BillType.CHF)) {
			return 0.0094 * value;
		}
		if (type.equals(BillType.GBP)) {
			return 0.0073 * value;
		}
		return value;
	}

	public static int isAtLeast(double value, double limit) {
		if (value >= limit) {
			return 1;
		}
		return 0;
	}

}
