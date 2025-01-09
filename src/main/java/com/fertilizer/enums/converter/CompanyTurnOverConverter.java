package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.CompanyTurnOver;

@Converter(autoApply = true)
public class CompanyTurnOverConverter implements AttributeConverter<CompanyTurnOver, String> {

	@Override
	public String convertToDatabaseColumn(CompanyTurnOver vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public CompanyTurnOver convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return CompanyTurnOver.fromShortName(dbData);
	}
}
