package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.CouponType;

@Converter(autoApply = true)
public class CouponTypeConverter  implements AttributeConverter<CouponType, String> {
	
	@Override
	public String convertToDatabaseColumn(CouponType couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public CouponType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return CouponType.fromShortName(dbData);
	}
}

