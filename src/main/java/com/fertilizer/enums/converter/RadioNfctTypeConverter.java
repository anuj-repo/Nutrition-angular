package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.RadioNfctType;

@Converter(autoApply = true)
public class RadioNfctTypeConverter implements AttributeConverter<RadioNfctType, String> {
	
	@Override
	public String convertToDatabaseColumn(RadioNfctType couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public RadioNfctType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return RadioNfctType.fromShortName(dbData);
	}
}

