package com.fertilizer.util;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateValidator {
	
	public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = (Date) sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
}
