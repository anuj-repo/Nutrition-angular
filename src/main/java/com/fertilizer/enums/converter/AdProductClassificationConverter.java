package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.AdProductClassification;

@Converter(autoApply = true)
public class AdProductClassificationConverter  implements AttributeConverter<AdProductClassification, String> {
	@Override
	public String convertToDatabaseColumn(AdProductClassification adProductClass) {
		if (adProductClass == null)
			return null;
		return adProductClass.getName();
	}

	@Override
	public AdProductClassification convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return AdProductClassification.fromShortName(dbData);
	}
}
