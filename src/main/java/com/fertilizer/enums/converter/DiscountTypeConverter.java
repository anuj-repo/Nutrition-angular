package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.DiscountType;

@Converter(autoApply = true)
public class DiscountTypeConverter implements AttributeConverter<DiscountType, String> {
	
	@Override
	public String convertToDatabaseColumn(DiscountType couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public DiscountType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return DiscountType.fromShortName(dbData);
	}
}
