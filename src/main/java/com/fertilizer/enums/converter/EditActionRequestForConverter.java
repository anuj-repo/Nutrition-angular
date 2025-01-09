package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.EditActionRequestFor;

@Converter(autoApply = true)
public class EditActionRequestForConverter implements AttributeConverter<EditActionRequestFor, String> {

	@Override
	public String convertToDatabaseColumn(EditActionRequestFor couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public EditActionRequestFor convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return EditActionRequestFor.fromShortName(dbData);
	}
}
