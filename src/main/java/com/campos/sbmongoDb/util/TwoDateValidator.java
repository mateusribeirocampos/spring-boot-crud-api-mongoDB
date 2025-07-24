package com.campos.sbmongoDb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TwoDateValidator {

	/**
	 * Version 2: two valid date to DateValidator
	 * 
	 * @author Mateus Campos
	 * @param dateStr
	 * @return DateStr not empty and valid
	 */

	public static Date parseSimpleTwoDate(String dateStr) {
		// verify is null or empty
		if (dateStr == null || dateStr.trim().isEmpty()) {
			// exception IllegalArgumentException that is a RuntimeExeception
			throw new IllegalArgumentException("Date cannot be empty or null!");
		}

		// Convert string to Date
		try {
			// Create the formatter
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			return sdf1.parse(dateStr);
		} catch (ParseException e1) {

			try {
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
				return sdf2.parse(dateStr);
			} catch (ParseException e2) {
				throw new IllegalArgumentException("Date '" + dateStr + "' must be in the format dd/MM/yyyy or dd-MM-yyyy");
			}
		}
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
				Date result = parseSimpleTwoDate(ListDates);
				System.out.println("OK '" + ListDates + " ' -> " + result);
			} catch (IllegalArgumentException e) {
				System.out.println("X '" + ListDates + "' -> ERROR: " + e.getMessage());
			}
		}

		System.out.println("=".repeat(50));
	}
}
