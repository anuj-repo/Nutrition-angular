package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.PackageTakenEnum;

@Converter(autoApply = true)
public class PackageTakenConverter implements AttributeConverter<PackageTakenEnum, String> {

	@Override
	public String convertToDatabaseColumn(PackageTakenEnum couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public PackageTakenEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return PackageTakenEnum.fromShortName(dbData);
	}
}
