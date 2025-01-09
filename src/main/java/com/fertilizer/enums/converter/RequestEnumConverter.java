package com.fertilizer.enums.converter;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.RequestEnum;

@Converter(autoApply = true)
public class RequestEnumConverter implements AttributeConverter<RequestEnum, String> {
	@Override
	public String convertToDatabaseColumn(RequestEnum requestFor) {
		if (requestFor == null)
			return null;
		return requestFor.getName();
	}

	@Override
	public RequestEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return RequestEnum.fromShortName(dbData);
	}
}
