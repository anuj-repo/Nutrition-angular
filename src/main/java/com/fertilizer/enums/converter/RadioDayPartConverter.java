package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.RadioDayPart;

@Converter(autoApply = true)
public class RadioDayPartConverter implements AttributeConverter<RadioDayPart, String> {
	
	@Override
	public String convertToDatabaseColumn(RadioDayPart couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public RadioDayPart convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return RadioDayPart.fromShortName(dbData);
	}
}
