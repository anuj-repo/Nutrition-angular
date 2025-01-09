package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.AddressType;

@Converter(autoApply = true)
public class AddressTypeConverter implements AttributeConverter<AddressType, String> {

	@Override
	public String convertToDatabaseColumn(AddressType attribute) {
		if (attribute == null)
			return null;

		return attribute.getName();
	}

	@Override
	public AddressType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return AddressType.fromShortName(dbData);
	}
}
