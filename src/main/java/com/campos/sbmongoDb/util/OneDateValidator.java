package com.campos.sbmongoDb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OneDateValidator {
	
	/**
	 * Simple version to DateValidator
	 * @author Mateus Campos
	 * @param dateStr
	 * @return DateStr not empty and valid
	 */
	
	public static Date parseSimpleDate(String dateStr) {
		// verify is null or empty
		if (dateStr == null || dateStr.trim().isEmpty()) {
			// exception IllegalArgumentException that is a RuntimeExeception
			throw new IllegalArgumentException("Date cannot be empty or null!");
		}
		
		// Create the formatter
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Date '" + dateStr + "' must be in the format dd/MM/yyyy");
		}
	}
	
	/**
	 *  Method to test the format
	 *  @param Date
	 *  @return test 
	 */
	public static void testFormats() {
		System.out.println("\n ============ TESTING SIMPLE DATEVALIDATOR ==================== ");
		
		String[] dates = {
				"21/03/2018",
				"21-03-2018",
				"2018-03-21",
				"invalid-date"
		};
		
		for (String ListDates : dates) {
			try {
				Date result = parseSimpleDate(ListDates);
				System.out.println("OK '" + ListDates + " ' -> " + result);
			} catch (IllegalArgumentException e) {
				System.out.println("X '" + ListDates + "' -> ERROR: " + e.getMessage());
			}
		}
		
		System.out.println("=".repeat(50));
	}
}
