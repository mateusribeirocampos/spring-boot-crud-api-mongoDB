package com.campos.sbmongoDb.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import com.campos.sbmongoDb.util.ModernDateValidator;

public class URL {

	public static String decodeParam(String text) {
		try {
			return URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static Date convertDate(String textDate, Date DefaultValue) {
		Date convertTextDate = ModernDateValidator.parseDate(textDate);
		return convertTextDate;
	}
}
