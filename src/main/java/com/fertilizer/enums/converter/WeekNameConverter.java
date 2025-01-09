package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.WeekNameEnum;

@Converter(autoApply = true)
public class WeekNameConverter  implements AttributeConverter<WeekNameEnum, String> {
	@Override
	public String convertToDatabaseColumn(WeekNameEnum attribute) {
		if (attribute == null)
			return null;
		return attribute.getName();
	}

	@Override
	public WeekNameEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return WeekNameEnum.fromShortName(dbData);
	}
}
