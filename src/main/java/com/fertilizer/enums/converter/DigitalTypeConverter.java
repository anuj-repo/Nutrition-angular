package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.DigitalType;

@Converter(autoApply = true)
public class DigitalTypeConverter implements AttributeConverter<DigitalType, String> {
	
	@Override
	public String convertToDatabaseColumn(DigitalType couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public DigitalType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return DigitalType.fromShortName(dbData);
	}
}
