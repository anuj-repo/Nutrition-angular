package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.GenderEnum;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<GenderEnum, String> {

	@Override
	public String convertToDatabaseColumn(GenderEnum status) {
		if (status == null)
			return null;

		return status.getName();
	}

	@Override
	public GenderEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return GenderEnum.fromShortName(dbData);
	}

}
