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
	
	public static Date convertDate(String textDate, Date defaultValue) {
	    try {
	        // Verificar se não está vazio
	        if (textDate == null || textDate.trim().isEmpty()) {
	            return defaultValue;
	        }
	        
	        return ModernDateValidator.parseDate(textDate);
	        
	    } catch (IllegalArgumentException e) {
	        // Se falhar no parse, retorna valor padrão
	        System.err.println("Erro ao converter data: " + textDate + " - " + e.getMessage());
	        return defaultValue;
	    }
	}
}
