package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.OtpFor;

@Converter(autoApply = true)
public class OtpForConverter implements AttributeConverter<OtpFor, String> {

	@Override
	public String convertToDatabaseColumn(OtpFor attribute) {
		if (attribute == null)
			return null;
		return attribute.getName();
	}

	@Override
	public OtpFor convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return OtpFor.fromShortName(dbData);
	}

}
