package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.AdColor;


@Converter(autoApply = true)
public class AdColorConverter implements AttributeConverter<AdColor, String> {
	@Override
	public String convertToDatabaseColumn(AdColor adColor) {
		if (adColor == null)
			return null;
		return adColor.getName();
	}

	@Override
	public AdColor convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return AdColor.fromShortName(dbData);
	}
}
