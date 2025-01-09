package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.AdCategoryType;


@Converter(autoApply = true)
public class AdCategoryTypeConverter implements AttributeConverter<AdCategoryType, String> {
	
	@Override
	public String convertToDatabaseColumn(AdCategoryType adCategoryType) {
		if (adCategoryType == null)
			return null;
		return adCategoryType.getName();
	}

	@Override
	public AdCategoryType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return AdCategoryType.fromShortName(dbData);
	}
}
