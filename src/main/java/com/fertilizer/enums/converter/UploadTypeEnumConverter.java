package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.UploadTypeEnum;

@Converter(autoApply = true)
public class UploadTypeEnumConverter implements AttributeConverter<UploadTypeEnum, String> {

	@Override
	public String convertToDatabaseColumn(UploadTypeEnum couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public UploadTypeEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return UploadTypeEnum.fromShortName(dbData);
	}
}
