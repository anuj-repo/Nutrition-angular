package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.RadioProductType;

@Converter(autoApply = true)
public class RadioProductTypeConverter implements AttributeConverter<RadioProductType, String> {
	
	@Override
	public String convertToDatabaseColumn(RadioProductType couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public RadioProductType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return RadioProductType.fromShortName(dbData);
	}

}
