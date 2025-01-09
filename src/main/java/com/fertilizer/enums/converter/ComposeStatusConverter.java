package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.ComposeStatus;

@Converter(autoApply = true)
public class ComposeStatusConverter implements AttributeConverter<ComposeStatus, String> {

	@Override
	public String convertToDatabaseColumn(ComposeStatus vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public ComposeStatus convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return ComposeStatus.fromShortName(dbData);
	}
}
