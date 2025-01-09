package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;

import com.fertilizer.enums.BooleanStatus;

public class BooleanStatusConverter implements AttributeConverter<BooleanStatus, String>{

	@Override
	public String convertToDatabaseColumn(BooleanStatus attribute) {
		if (attribute == null)
			return null;

		return attribute.getName();
	}

	@Override
	public BooleanStatus convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return BooleanStatus.fromShortName(dbData);
	}

}
