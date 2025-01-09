package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.Salutation;

@Converter(autoApply = true)
public class SalutationConverter implements AttributeConverter<Salutation, String> {

	@Override
	public String convertToDatabaseColumn(Salutation vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public Salutation convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return Salutation.fromShortName(dbData);
	}
}