package com.fertilizer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Dhiraj & Imran
 *
 */
public final class DateUtil {
	private DateUtil() {
	}
	
	public static final String TIMEPATTERN = "HH:MM";
	public static final String NEWSTRINGFORMAT = "yyyy-MM-dd";
	public static final String OLDSTRINGFORMAT = "dd/MM/yyyy";

	public static final String STRINGDATE = "dd-MMMM-yyyy";

	public static String getResponseStringDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STRINGDATE);
		return formatter.format(date);
	}

	public static String getStringFromLocalDate(LocalDate localDate) {

		return localDate.toString();
	}

	public static Date getDateFromString(String stringDate) throws ParseException {
		DateFormat newFormat = new SimpleDateFormat(NEWSTRINGFORMAT);
		DateFormat oldFormat = new SimpleDateFormat(OLDSTRINGFORMAT);
		LocalDate date = LocalDate.parse(newFormat.format(oldFormat.parse(stringDate)));
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getDateFromOldDateFormat(Date date) throws ParseException {
		DateFormat newSdf = new SimpleDateFormat(NEWSTRINGFORMAT);
		return newSdf.parse(newSdf.format(date));
	}

	public static LocalDate getLocalDateFromString(String stringDate) {
		return LocalDate.parse(stringDate, DateTimeFormatter.ofPattern(OLDSTRINGFORMAT));
	}

	public static LocalDate getLocalDateFromString(String stringDate, String customFormat) throws ParseException {
		DateFormat df = new SimpleDateFormat(customFormat);
		return LocalDate.parse(df.format(df.parse(stringDate)));
	}

	public static Date getParsedDateFromStrinDate(String date) throws ParseException {
		DateFormat oldFormat = new SimpleDateFormat(OLDSTRINGFORMAT);
		return oldFormat.parse(date);
	}

	public static String getParsedStringDateFromStringDate(String date) throws ParseException {
		DateFormat newFormat = new SimpleDateFormat(NEWSTRINGFORMAT);
		DateFormat oldFormat = new SimpleDateFormat(OLDSTRINGFORMAT);
		return newFormat.format(oldFormat.parse(date));
	}

	public static LocalTime getLocalTimeByDate(Date date) {
		return LocalTime.ofNanoOfDay(date.getTime() * 1000000);
	}

	public static Date getDateFromLocalDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getParsedDate(Date date) throws ParseException {
		DateFormat newFormat = new SimpleDateFormat(NEWSTRINGFORMAT);
		return newFormat.parse(newFormat.format(date));
	}

	public static Date getParsedDateFromCustomFormat(String date, String newFormat) throws ParseException {
		DateFormat newDateFormat = new SimpleDateFormat(newFormat);
		return newDateFormat.parse(date);
	}

	public static Date getDateFromTime(LocalTime localTime, LocalDate date) {
		LocalDateTime atDate = localTime.atDate(date);
		Instant instant = atDate.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	public static Map<Long, LocalDate> getSkippedStore(String skipstoreFordate) {
		Map<Long, LocalDate> mapOfString = new HashMap<>();
		if (null != skipstoreFordate && !skipstoreFordate.isEmpty()) {
			String[] split = skipstoreFordate.split("\\:");
			LocalDate localDateFromString = LocalDate.parse(split[0]);
			Arrays.asList(split[1].split("\\,")).stream().map(Long::valueOf)
					.forEach(storeId -> mapOfString.put(storeId, localDateFromString));
		}
		return mapOfString;
	}

	public static int getHours(Date date) {
		if (date != null) {
			Instant current = date.toInstant();
			LocalDateTime ldt = LocalDateTime.ofInstant(current, ZoneId.systemDefault());
			return ldt.getHour();
		}
		return 0;
	}

	public static String getTimeFromSec(int time) {
		if (time == 0) {
			return "00:00:00";
		} else {
			int sec = time % 60;
			int min = time / 60;
			int hr = min / 60;
			min = min % 60;
			StringBuilder result = new StringBuilder();
			if (hr >= 10) {
				result.append(hr + ":");
			} else {
				result.append("0" + hr + ":");
			}
			if (min >= 10) {
				result.append(min + ":");
			} else {
				result.append("0" + min + ":");
			}
			if (sec >= 10) {
				result.append(sec);
			} else {
				result.append("0" + sec);
			}
			return result.toString();
		}
	}

	public static LocalDate getLocalDateByDate(Date scheduleDateFrom) {
		return scheduleDateFrom.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
	}

	public static LocalTime getLocalTimeFromString(String prefferedTime) {
		return LocalTime.parse(prefferedTime,
	            DateTimeFormatter.ofPattern(TIMEPATTERN));
	}
	
	public static Map<String,LocalDate> getCurrentFinancialYear(String begFirstFy,String endFirstFy) {
		
		Map<String,LocalDate> result = new HashMap<>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		
		LocalDate begFirstFyDate = LocalDate.parse(begFirstFy, formatter);
		LocalDate endFirstFyDate = LocalDate.parse(endFirstFy, formatter);
		
		LocalDate curDate = LocalDate.now();
		
		Integer curMonth = curDate.getMonthValue();
		Integer begMonth = begFirstFyDate.getMonthValue();
		
		
		Integer yearsToAdd = 0;
		if(curMonth >=begMonth && curMonth<=12) {
			yearsToAdd = curDate.getYear() - begFirstFyDate.getYear();
		}else {
			yearsToAdd = curDate.getYear() - endFirstFyDate.getYear();
		}
		begFirstFyDate.plusYears(yearsToAdd);
		endFirstFyDate.plusYears(yearsToAdd);
		
		
		result.put("startingOfFinancialYear",begFirstFyDate);
		result.put("endingOfFinancialYear",endFirstFyDate);
		
		return result;
		
	}
}
