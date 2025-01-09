package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.EditStatus;

@Converter(autoApply = true)
public class EditStatusConverter implements AttributeConverter<EditStatus, String> {

	@Override
	public String convertToDatabaseColumn(EditStatus status) {
		if (status == null)
			return null;

		return status.getName();
	}

	@Override
	public EditStatus convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return EditStatus.fromShortName(dbData);
	}

}
