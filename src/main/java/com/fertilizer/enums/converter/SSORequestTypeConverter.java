package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.SSORequestType;

@Converter(autoApply = true)
public class SSORequestTypeConverter implements AttributeConverter<SSORequestType, String> {

	@Override
	public String convertToDatabaseColumn(SSORequestType attribute) {
		if (attribute == null)
			return null;

		return attribute.getName();
	}

	@Override
	public SSORequestType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return SSORequestType.fromShortName(dbData);
	}
}