package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.PackageChoiceEnum;

@Converter(autoApply = true)
public class PackageChoiceConverter implements AttributeConverter<PackageChoiceEnum, String> {

	@Override
	public String convertToDatabaseColumn(PackageChoiceEnum couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public PackageChoiceEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return PackageChoiceEnum.fromShortName(dbData);
	}
}
