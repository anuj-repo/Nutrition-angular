package com.fertilizer.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fertilizer.enums.RateDay;
import com.fertilizer.enums.WeekNameEnum;

@Component
public class WeekNameUtil {
	public RateDay getRateDay(WeekNameEnum weekNameEnum) {
		RateDay rateDay= null;
		if(weekNameEnum.equals(WeekNameEnum.Sunday) || weekNameEnum.equals(WeekNameEnum.Saturday)) {
			rateDay=RateDay.WEEKEND;
		}
		else {
			rateDay=RateDay.WEEKDAY;
		}
		return rateDay;
	}
	
	public WeekNameEnum getDayOfDate(String dateStr) throws Exception {
		 DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		    Date date = formatter.parse(dateStr);
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    // Get values from calendar.
		    int dayofWeek=(calendar.get(Calendar.DAY_OF_WEEK));
		    WeekNameEnum weekNameEnum = null;
				switch (dayofWeek) {
				case 1:
					weekNameEnum=WeekNameEnum.Sunday;
					break;
				case 2:
					weekNameEnum=WeekNameEnum.Monday;
					break;
				case 3:
					weekNameEnum=WeekNameEnum.Tuesday;
					break;
				case 4:
					weekNameEnum=WeekNameEnum.Wednesday;
					break;
				case 5:
					weekNameEnum=WeekNameEnum.Thursday;
					break;
				case 6:
					weekNameEnum=WeekNameEnum.Friday;
					break;
				case 7:
					weekNameEnum=WeekNameEnum.Saturday;
					break;
				}
		    return weekNameEnum;
	}
}
