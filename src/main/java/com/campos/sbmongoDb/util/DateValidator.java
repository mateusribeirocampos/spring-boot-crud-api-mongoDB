package com.campos.sbmongoDb.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Util to convert strings to dates and validate date formats. supporting
 * multiple date formats.
 * 
 * @autor Mateus Campos
 * @version 1.0
 * @since 2023-10-01
 * 
 */

public class DateValidator {

	/**
	 * 
	 * @param dateStr string contening the date to be converted
	 * @return Date object if the string is a valid date, null otherwise
	 * @throws IllegalArgumentException if the dateStr is null or empty
	 */

	public static Date parseDate(String dateStr) {

		if (dateStr == null || dateStr.trim().isEmpty()) {
			throw new IllegalArgumentException("Date cannot be null or empty");
		}

		// Remove space
		dateStr = dateStr.trim();

		// get different date format (priority order)
		String[] patterns = { "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy", "dd-MM-yyyy HH:mm:ss", "dd-MM-yyyy",
				"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };

		for (String pattern : patterns) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
				LocalDateTime localDateTime;

				if (pattern.contains("HH:mm:ss")) {
					// Format include hours
					localDateTime = LocalDateTime.parse(dateStr, formatter);
				} else {
					// add pattern hour to the format just with date
					localDateTime = LocalDateTime.parse(dateStr + " 00:00:00",
							DateTimeFormatter.ofPattern(pattern + " HH:mm:ss"));
				}

				return Date.from(localDateTime.atZone(ZoneId.of("GMT")).toInstant());
			} catch (DateTimeParseException e) {
				// error log to debug in development
				if (isDebugMode()) {
					System.out.println(
							" try parse with pattern" + pattern + "'fail to '" + dateStr + "': '" + e.getMessage());
				}
				continue; // try other date format
			}
		}

		throw new IllegalArgumentException(String.format("Date '%s' is not in the knowleged format"
				+ "Format accept: dd/MM/yyyy, dd-MM-yyyy, yyyy-MM-dd" + "(all format date can have or not HH:mm:ss",
				dateStr));

	}

	/**
	 * Verify if the string represent a valid date
	 * 
	 * @param dateStr string to be test
	 * @return true if the date is valid, false otherwise
	 */

	public static boolean isValidDate(String dateStr) {
		try {
			parseDate(dateStr);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Convert Date format to string in the Brazilian format
	 * 
	 * @param date Date to be convert
	 * @return String in the format dd/MM/yyyy HH:mm:ss
	 * 
	 */

	public static String formatToBrazilianDate(Date date) {
		if (date == null) {
			return null;
		}

		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.of("GMT")).toLocalDateTime();

		return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	/**
	 * Verify if the debugMode is based in the system propriety or environment
	 * variable
	 * 
	 */
	private static boolean isDebugMode() {
		return Boolean.parseBoolean(System.getProperty("date.validator.debug", "false"))
				|| Boolean.parseBoolean(System.getenv("DATE_VALIDATOR_DEBUG"));
	}
}
