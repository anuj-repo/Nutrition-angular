package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.AdSizeEnum;


@Converter(autoApply = true)
public class AdSizeConverter implements AttributeConverter<AdSizeEnum, String> {
	@Override
	public String convertToDatabaseColumn(AdSizeEnum adSize) {
		if (adSize == null)
			return null;
		return adSize.getName();
	}

	@Override
	public AdSizeEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return AdSizeEnum.fromShortName(dbData);
	}
}
