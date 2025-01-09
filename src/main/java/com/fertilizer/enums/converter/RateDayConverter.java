package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.RateDay;

@Converter(autoApply = true)
public class RateDayConverter implements AttributeConverter<RateDay, String> {
	@Override
	public String convertToDatabaseColumn(RateDay rateDay) {
		if (rateDay == null)
			return null;
		return rateDay.getName();
	}

	@Override
	public RateDay convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return RateDay.fromShortName(dbData);
	}
}
