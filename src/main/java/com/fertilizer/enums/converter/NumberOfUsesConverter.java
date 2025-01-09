package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.NumberOfUse;
@Converter(autoApply = true)
public class NumberOfUsesConverter implements AttributeConverter<NumberOfUse, String>{
	@Override
	public String convertToDatabaseColumn(NumberOfUse attribute) {
		// TODO Auto-generated method stub
		if (attribute == null)
			return null;

		return attribute.getName();
	}

	@Override
	public NumberOfUse convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		if (dbData == null)
			return null;
		return NumberOfUse.fromShortName(dbData);
	}
}
