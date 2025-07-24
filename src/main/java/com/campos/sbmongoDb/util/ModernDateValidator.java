package com.campos.sbmongoDb.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class ModernDateValidator {

	/**
	 * Version 3: three or more date to DateValidator
	 * 
	 * @author Mateus Campos
	 * @param dateStr
	 * @return DateStr not empty and valid
	 */

	public static Date parseDate(String dateStr) {
		// verify is null or empty
		if (dateStr == null || dateStr.trim().isEmpty()) {
			// exception IllegalArgumentException that is a RuntimeExeception
			throw new IllegalArgumentException("Date cannot be empty or null!");
		}

		// formatDate is an array of string with the different date formats
		String[] formatDate = { 
				"dd/MM/yyyy",
				"dd/MM/yyyy HH:mm:ss",
				"dd-MM-yyyy",
				"dd-MM-yyyy HH:mm:ss",
				"yyyy-MM-dd"

		};

		// for each format in formatDate, try to parse the dateStr
		for (String checkFormatDates : formatDate) {
			// try to parse the dateStr with the current format
			try {
				// Create the formatter
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(checkFormatDates);
				// LocalDateTime ldt will hold the parsed date
				LocalDateTime ldt;

				// If the format includes time, parse directly
				if (checkFormatDates.contains("HH:mm:ss")) {
					// Format include hours
					ldt = LocalDateTime.parse(dateStr, dtf);	
				} else {
					// If the format does not include time, append "00:00:00" to the dateStr
					ldt = LocalDateTime.parse(dateStr + "00:00:00",
							// add pattern hour to the format just with date
							DateTimeFormatter.ofPattern(checkFormatDates + "HH:mm:ss"));
				}
				// Convert LocalDateTime to Date and return
				return Date.from(ldt.atZone(ZoneId.of("GMT")).toInstant());
			} catch (DateTimeParseException e) { 
				// If parsing fails, continue to the next format
				continue;
			}
		}
		// If no format matched, throw an exception with a detailed message
		throw new IllegalArgumentException(String.format("Date '%s' is not in the knowleged format"
				+ "Format accept: dd/MM/yyyy, dd-MM-yyyy, yyyy-MM-dd" 
				+ "(all format date can have or not HH:mm:ss)",
				dateStr));
	}

	/**
	 * Method to test the format
	 * 
	 * @param Date
	 * @return test
	 */
	public static void testFormats() {
		System.out.println("\n ============ TESTING SIMPLE DATEVALIDATOR ==================== ");

		String[] dates = { "21/03/2018", "21-03-2018 11:53:12", "2018-03-21", "invalid-date" };

		for (String ListDates : dates) {
			try {
				Date result = parseDate(ListDates);
				System.out.println("OK '" + ListDates + " ' -> " + result);
			} catch (IllegalArgumentException e) {
				System.out.println("X '" + ListDates + "' -> ERROR: " + e.getMessage());
			}
		}

		System.out.println("=".repeat(50));
	}
}
