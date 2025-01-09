package com.fertilizer.util;

import java.util.Date;

import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class FinancialYearUtil {

	
	public  HashMap<String, Date> getFinancialYear(String startMonth,Integer endMonth) throws Exception
	{
		HashMap<String, Date> financialYear=new HashMap<String, Date>();
		DateFormat  date1=new SimpleDateFormat("dd/MM/yyyy");  
		int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
		int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
		String financiyalYearFrom="";
		String financiyalYearTo="";
		Calendar calendar = Calendar.getInstance();
		if (CurrentMonth<8) {
				financiyalYearFrom="01/"+startMonth+"/"+(CurrentYear-1);
				calendar = new GregorianCalendar(CurrentYear,endMonth,0);
				Date lastDayOfMonth = calendar.getTime();
				financiyalYearTo=date1.format(lastDayOfMonth);
		} else {
			financiyalYearFrom="01/"+startMonth+"/"+(CurrentYear);
			calendar = new GregorianCalendar(CurrentYear+1,endMonth,0);
			Date lastDayOfMonth = calendar.getTime();
			financiyalYearTo=date1.format(lastDayOfMonth);
		}
		financialYear.put("financiyalYearFrom",date1.parse(financiyalYearFrom));
		financialYear.put("financiyalYearTo", date1.parse(financiyalYearTo));
		return financialYear;
	}

}
