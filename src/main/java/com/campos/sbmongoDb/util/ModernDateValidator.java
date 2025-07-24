package com.campos.sbmongoDb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModernDateValidator {

	/**
	 * Version 3: three or more date to DateValidator
	 * 
	 * @author Mateus Campos
	 * @param dateStr
	 * @return DateStr not empty and valid
	 */

	public static Date parseSimpleThreeDate(String dateStr) {
		// verify is null or empty
		if (dateStr == null || dateStr.trim().isEmpty()) {
			// exception IllegalArgumentException that is a RuntimeExeception
			throw new IllegalArgumentException("Date cannot be empty or null!");
		}

		String[] formatDate = {
				"dd/MM/yyyy",
				"dd-MM-yyyy",
				"yyyy-MM-dd"
				
		};
		
		for (String formatDates : formatDate) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(formatDates);
				return sdf.parse(dateStr);
			} catch (ParseException e) {
				continue;
			}
		}
		throw new IllegalArgumentException("Date '" 
		+ dateStr 
		+ "' must be in the format dd/MM/yyyy, dd-MM-yyyy or yyyy-MM-dd");		
	}

	/**
	 * Method to test the format
	 * 
	 * @param Date
	 * @return test
	 */
	public static void testFormats() {
		System.out.println("\n ============ TESTING SIMPLE DATEVALIDATOR ==================== ");

		String[] dates = { "21/03/2018", "21-03-2018", "2018-03-21", "invalid-date" };

		for (String ListDates : dates) {
			try {
				Date result = parseSimpleThreeDate(ListDates);
				System.out.println("OK '" + ListDates + " ' -> " + result);
			} catch (IllegalArgumentException e) {
				System.out.println("X '" + ListDates + "' -> ERROR: " + e.getMessage());
			}
		}

		System.out.println("=".repeat(50));
	}
}
